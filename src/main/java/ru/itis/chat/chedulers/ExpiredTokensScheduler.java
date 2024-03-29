package ru.itis.chat.chedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.itis.chat.repositories.TokenRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ExpiredTokensScheduler {

  @Autowired
  private TokenRepository tokenRepository;

  @Scheduled(cron = "*/10 * * * * *")
  @Transactional
  public void removeExpiredTokens() {
    tokenRepository.deleteTokensByExpiredDateTimeBefore(LocalDateTime.now());
  }

}
