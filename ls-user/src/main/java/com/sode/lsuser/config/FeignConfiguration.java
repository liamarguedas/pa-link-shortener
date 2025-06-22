package com.sode.lsuser.config;

import com.sode.lsuser.security.TokenService;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {

    @Bean
    RequestInterceptor OAuth2FeignRequestInterceptor(TokenService tokenService) {

        return requestTemplate -> {
            String token = tokenService.getAccessToken().block();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
