package ru.itis.chat.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.itis.chat.dto.LoginDto;
import ru.itis.chat.security.filters.TokenAuthenticationFilter;
import ru.itis.chat.security.providers.JwtTokenAuthenticationProvider;
import ru.itis.chat.security.providers.TokenAuthenticationProvider;
import ru.itis.chat.services.UserService;
import ru.itis.chat.services.UserServiceImpl;

import javax.servlet.Filter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity // включаем безопасность
@ComponentScan("ru.itis.chat") // говорим, чтобы искал все компоненты в наших пакетах
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("tokenProvider")
  private TokenAuthenticationProvider provider;

//  @Autowired
//  @Qualifier("jwtTokenProvider")
//  private JwtTokenAuthenticationProvider jwtTokenAuthenticationProvider;

  @Autowired
  @Qualifier("customUserDetailsService")
  private UserService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(provider)/*.authenticationProvider(jwtTokenAuthenticationProvider)*/;

  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .authorizeRequests().anyRequest().permitAll().and();*/

    http.addFilterAt(new TokenAuthenticationFilter(), BasicAuthenticationFilter.class)
            .csrf().disable()
            .authenticationProvider(provider)
            .sessionManagement().disable()
            .authorizeRequests().anyRequest().permitAll().and();
            /*.antMatchers("/login").anonymous()
            .antMatchers("/chat").authenticated();*/
  }

  @Bean
  public UserService userDetailsService(){
    return new UserServiceImpl();
  }

  @Bean
  public AuthenticationProvider tokenAuthenticationProvider() {
    return new TokenAuthenticationProvider();
  }

  /*@Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setPasswordEncoder(passwordEncoder());
    authProvider.setUserDetailsService(userDetailsService());
    return authProvider;
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authProvider());
  }*/

}