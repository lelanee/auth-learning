package com.lantranle.auth.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
  @Bean
  public SecurityFilterChain buildSecFilter(HttpSecurity httpSecurity) {
    return httpSecurity
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/home").permitAll()
        .requestMatchers("/dashboard").authenticated()
        .anyRequest().authenticated()
      )
      .formLogin(Customizer.withDefaults())
      .build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
      .password("{noop}123456")
      .roles("USER")
      .build();

    UserDetails admin = User.withUsername("admin")
      .password("{noop}123456")
      .roles("ADMIN")
      .build();

    return new InMemoryUserDetailsManager(user, admin);
  }
}
