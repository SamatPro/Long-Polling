package ru.itis.chat.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.chat.models.Role;
import ru.itis.chat.models.Token;
import ru.itis.chat.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

  private User user;
  private Token currentToken;

  public UserDetailsImpl(User user, Token token) {
    this.user = user;
    this.currentToken = token;
  }
  public UserDetailsImpl(Long id, String role, String login) {
    this.user = User.builder()
            .id(id)
            .role(Role.valueOf(role))
            .login(login)
            .build();
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return user.getPasswordHash();
  }

  @Override
  public String getUsername() {
    return user.getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public User getUser() {
    return user;
  }

  public Token getCurrentToken() {
    return currentToken;
  }
}
