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

  @PostMapping(value = "/login")
  @PreAuthorize("isAnonymous()")
  public ResponseEntity<TokenDto> login(/*@RequestParam(name = "login") String login,
          @RequestParam(name = "password") String password,*/
          @RequestBody LoginDto loginData
                            /*@RequestParam(value = "jwt", required = false) Boolean jwtEnabled*/) {
    /*LoginDto loginData = LoginDto.builder()
            .login(login)
            .password(password)
            .build();*/
//    loginData.setLogin(login);
//    loginData.setPassword(password);
    System.out.println(loginData);
    TokenDto tokenDto = service.login(loginData);
    System.out.println(tokenDto);
    /*if (jwtEnabled != null && jwtEnabled) {
      modelAndView.addObject("JWT", service.loginAndCreateJwt(loginData.getLogin(), loginData.getPassword()));
    }*/
//    modelAndView.addObject("token", tokenDto.getValue());

//    modelAndView.setViewName("login2");
    return ResponseEntity.ok(tokenDto);
  }

  @GetMapping("/login")
  @PreAuthorize("isAnonymous()")
  public ModelAndView login(){
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");
    return modelAndView;
  }

}
