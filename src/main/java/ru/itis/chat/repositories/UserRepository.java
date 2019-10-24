package ru.itis.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.chat.models.Token;
import ru.itis.chat.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findFirstByLoginIgnoreCase(String login);
  @Query(value = "SELECT * FROM users " +
          "LEFT JOIN token ON token.user_id = users.id " +
          "WHERE token.value = ?1", nativeQuery = true)
  Optional<User> findOneByToken(String token);
  Optional<User> findOneByLogin(String login);
}