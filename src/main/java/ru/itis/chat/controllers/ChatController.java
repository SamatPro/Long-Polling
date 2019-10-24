package ru.itis.chat.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.chat.models.User;
import ru.itis.chat.security.details.UserDetailsImpl;

@RestController
public class ChatController {

  @GetMapping("/chat")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView getChatPage(@RequestHeader(name = "AUTH", required=false) String auth,
          ModelAndView model){

    System.out.println("Chat:" + auth);
    model.setViewName("chat");
    return model;
  }
}
