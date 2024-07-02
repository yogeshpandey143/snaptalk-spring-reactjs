package com.example.snaptalk.SnapTalk.configsecutiry;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config {

@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
  http.sessionManagement(
          management -> management.sessionCreationPolicy(
                  SessionCreationPolicy.STATELESS)).authorizeHttpRequests(
                          Authorize -> Authorize.requestMatchers("/api/**").authenticated()
                                  .anyRequest().permitAll())
          .addFilterBefore(new jwtValidator(), BasicAuthenticationFilter.class)
          .csrf(csrf-> csrf.disable());

  return http.build();

}


@Bean
PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
}
