package ru.itis.chat.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itis.chat.dto.LoginDto;
import ru.itis.chat.dto.TokenDto;
import ru.itis.chat.models.Token;
import ru.itis.chat.models.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
  TokenDto login(LoginDto loginData);
  User findUserByToken(String token);
  void logout(String token);

  TokenDto loginAndCreateJwt(String login, String password);
}
