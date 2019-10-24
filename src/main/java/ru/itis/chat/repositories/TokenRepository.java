package ru.itis.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.chat.models.Token;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
  Optional<Token> findFirstByValue(String value);
  void deleteTokensByExpiredDateTimeBefore(LocalDateTime now);
  void deleteByValue(String value);
}