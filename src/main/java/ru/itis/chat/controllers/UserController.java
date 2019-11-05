package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.chat.dto.LoginDto;
import ru.itis.chat.dto.TokenDto;
import ru.itis.chat.security.authentication.TokenAuthentication;
import ru.itis.chat.services.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

  @Autowired
  @Qualifier("customUserDetailsService")
  private UserService service;

  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  public ModelAndView login(){
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginData) {
    TokenDto tokenDto = service.login(loginData);
    return ResponseEntity.ok(tokenDto);
  }

}
