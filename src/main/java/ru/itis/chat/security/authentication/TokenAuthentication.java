package ru.itis.chat.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.chat.security.details.UserDetailsImpl;

import java.util.Collection;

public class TokenAuthentication implements Authentication {

  private UserDetailsImpl userDetails;
  private String token;

  private boolean isAuthenticated;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (userDetails != null) {
      return userDetails.getAuthorities();
    } else return null;
  }

  @Override
  public Object getCredentials() {
    return userDetails.getPassword();
  }

  @Override
  public Object getDetails() {
    return userDetails;
  }

  @Override
  public Object getPrincipal() {
    if (userDetails != null) {
      return userDetails;
    } else return null;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

  @Override
  public String getName() {
    return token;
  }

  public void setUserDetails(UserDetailsImpl userDetails) {
    this.userDetails = userDetails;
  }

  public void setToken(String token) {
    this.token = token;
  }
}