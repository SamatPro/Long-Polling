package ru.itis.chat.services;

import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.models.Message;

public interface MessagesService {
  void saveMessage(Message message);
  Message messageDtoToPlainMessage(MessageDto messageDto);
}
