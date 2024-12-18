package com.socslingo.website;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}