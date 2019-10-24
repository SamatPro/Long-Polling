package ru.itis.chat.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.dto.TokenDto;
import ru.itis.chat.dto.UserDto;
import ru.itis.chat.models.Message;
import ru.itis.chat.models.User;
import ru.itis.chat.repositories.MessagesRepository;
import ru.itis.chat.services.MessagesService;
import ru.itis.chat.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {
  private final Map<String, List<MessageDto>> messages = new HashMap<>();

  @Autowired
  private MessagesService messagesService;

  @Autowired
  @Qualifier("customUserDetailsService")
  private UserService userService;

  @PostMapping("/messages")
  public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto messageDto){
    System.out.println(messageDto.getToken());
    Message message = messagesService.messageDtoToPlainMessage(messageDto);
    messagesService.saveMessage(message);

    User user = userService.findUserByToken(messageDto.getToken());
    String userName = user.getFirstName() + " " + user.getLastName();
    messageDto.setUserName(userName);
    if (!messages.containsKey(messageDto.getToken())){
      messages.put(messageDto.getToken(), new ArrayList<>());

    }
    for (List<MessageDto> pageMessages : messages.values()){
      synchronized (pageMessages){
        pageMessages.add(messageDto);
        pageMessages.notifyAll();
      }
    }
    return ResponseEntity.ok().build();
  }

  @SneakyThrows
  @GetMapping("/messages")
  public ResponseEntity<List<MessageDto>> getMessagesForPage(@RequestParam("token") String auth){
    System.out.println(auth);
    synchronized (messages.get(auth)){
      if (messages.get(auth).isEmpty()){
        messages.get(auth).wait();
      }
      List<MessageDto> response = new ArrayList<>(messages.get(auth));
      messages.get(auth).clear();
      return ResponseEntity.ok(response);
    }
  }
}
