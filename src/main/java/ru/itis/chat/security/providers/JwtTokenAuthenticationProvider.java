package ru.itis.chat.security.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.itis.chat.security.authentication.JwtTokenAuthentication;
import ru.itis.chat.security.details.UserDetailsImpl;
//@Component("jwtTokenProvider")
public class JwtTokenAuthenticationProvider implements AuthenticationProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtTokenAuthentication tokenAuthentication = (JwtTokenAuthentication) authentication;
    // Claims - содержимое payload-а
    Claims body;
    try {
      body = Jwts.parser()
              .setSigningKey(jwtSecret)
              .parseClaimsJws(tokenAuthentication.getName())
              .getBody();
    } catch (MalformedJwtException | SignatureException e) {
      e.printStackTrace();
      throw new AuthenticationServiceException("Invalid token");
    }

    UserDetails userDetails = new UserDetailsImpl(
            Long.parseLong(body.get("sub").toString()),
            body.get("role").toString(),
            body.get("login").toString()
    );

    tokenAuthentication.setUserDetails(userDetails);
    tokenAuthentication.setAuthenticated(true);
    return tokenAuthentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return JwtTokenAuthentication.class.isAssignableFrom(authentication);
  }
}