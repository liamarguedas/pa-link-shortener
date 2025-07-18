package com.sode.lsrevoker.config;

import com.sode.lsrevoker.security.TokenService;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    RequestInterceptor OAuth2FeignRequestInterceptor(TokenService tokenService) {

        return requestTemplate -> {
            String token = tokenService.getAccessToken().block();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
