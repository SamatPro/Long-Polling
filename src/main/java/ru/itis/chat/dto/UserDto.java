package ru.itis.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.chat.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
  private String name;

  public static UserDto from(User user) {
    return UserDto.builder()
            .name(user.getFirstName() + " " + user.getLastName())
            .build();
  }

}
