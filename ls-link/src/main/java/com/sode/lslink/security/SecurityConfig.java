package com.sode.lslink.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.disable())
                    .authorizeHttpRequests(auth -> auth

                            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                            // SECURED ENDPOINTS
                            .requestMatchers(HttpMethod.GET, "/urls/all").hasAuthority("SCOPE_service:revoke")
                            .requestMatchers(HttpMethod.DELETE, "/urls/revoke/*").hasAuthority("SCOPE_service:revoke")
                            .requestMatchers(HttpMethod.GET, "/urls/all/*").hasAuthority("SCOPE_service:user")

                            // PUBLIC ENDPOINTS
                            .requestMatchers(HttpMethod.POST, "/urls/create").permitAll()
                            .requestMatchers(HttpMethod.GET, "/urls/redirect/**").permitAll()

                            // Everything else
                            .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter()))
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .anonymous(Customizer.withDefaults());

            return http.build();
        }
    }

