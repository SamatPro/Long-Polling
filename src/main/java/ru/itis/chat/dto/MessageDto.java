package ru.itis.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.chat.models.Message;
import ru.itis.chat.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
  private String text;
  private String token;
  private String userName;

  public static MessageDto from(Message message) {
    return MessageDto.builder()
            .text(message.getText())
            .build();
  }
}
