package com.sode.lslink.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/redirect/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/urls/create").permitAll()
                        .requestMatchers(HttpMethod.GET, "/urls/all").hasRole("SCOPE_link:revoke")
                        .requestMatchers(HttpMethod.DELETE, "/urls/revoke/*").hasRole("SCOPE_link:revoke")
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
}
