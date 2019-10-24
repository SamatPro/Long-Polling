package ru.itis.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.models.Message;
import ru.itis.chat.models.Token;
import ru.itis.chat.repositories.MessagesRepository;
import ru.itis.chat.repositories.UserRepository;

@Service
public class MessagesServiceImpl implements MessagesService {

  @Autowired
  private MessagesRepository messagesRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void saveMessage(Message message) {
    messagesRepository.save(message);
  }
  @Override
  public Message messageDtoToPlainMessage(MessageDto messageDto){
    Message message = Message.builder()
            .text(messageDto.getText())
            .user(userRepository.findOneByToken(messageDto.getToken()).get())
            .build();
    return message;
  }
}
