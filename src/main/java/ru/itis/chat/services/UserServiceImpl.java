package ru.itis.chat.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.chat.dto.LoginDto;
import ru.itis.chat.dto.TokenDto;
import ru.itis.chat.models.Token;
import ru.itis.chat.models.User;
import ru.itis.chat.repositories.TokenRepository;
import ru.itis.chat.repositories.UserRepository;
import ru.itis.chat.security.details.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service(value = "customUserDetailsService")
public class UserServiceImpl implements UserService {

  @Autowired
  private TokenRepository tokenRepository;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private UserRepository usersRepository;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${token.expired}")
  private Integer expiredSecondsForToken;

  @Override
  public TokenDto login(LoginDto loginData) {
    Optional<User> userCandidate = usersRepository.findFirstByLoginIgnoreCase(loginData.getLogin());

    if (userCandidate.isPresent()) {
      User user = userCandidate.get();
      if (encoder.matches(loginData.getPassword(), user.getPasswordHash())) {
        String value = UUID.randomUUID().toString();
        Token token = Token.builder()
                .createdAt(LocalDateTime.now())
                .expiredDateTime(LocalDateTime.now().plusSeconds(expiredSecondsForToken))
                .value(value)
                .user(user)
                .build();
        tokenRepository.save(token);
        return TokenDto.from(value);
      }
    } throw new BadCredentialsException("Incorrect login or password");
  }

  @Override
  public UserDetailsImpl loadUserByUsername(String s) throws UsernameNotFoundException {
//    return usersRepository.findFirstByLoginIgnoreCase(s).get();
    Optional<Token> authenticationCandidate = tokenRepository.findFirstByValue(s);
    if (authenticationCandidate.isPresent()) {
      Token token = authenticationCandidate.get();
      return new UserDetailsImpl(token.getUser(), token);
    } return null;
  }

  @Override
  public User findUserByToken(String token) {
    return usersRepository.findOneByToken(token).get();
  }

  @Override
  public void logout(String token) {
    tokenRepository.deleteByValue(token);
  }

  @Override
  public TokenDto loginAndCreateJwt(String login, String password) {
    Optional<User> candidate = usersRepository.findOneByLogin(login);
    if (candidate.isPresent()) {
      User currentUser = candidate.get();
      if (encoder.matches(password, currentUser.getPasswordHash())) {
        String jwtToken = Jwts.builder()
                .claim("role", currentUser.getRole().toString())
                .claim("login", currentUser.getLogin())
                .setSubject(currentUser.getId().toString())
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return TokenDto.builder().value(jwtToken).build();
      } throw new BadCredentialsException("User not found");
    } throw new BadCredentialsException("User not found");

  }
}