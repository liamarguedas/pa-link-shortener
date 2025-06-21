package com.sode.lsrevoker.config;

import com.sode.lsrevoker.security.TokenService;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FeignConfiguration.class);

    @Bean
    RequestInterceptor oauth2FeignRequestInterceptor(TokenService tokenService) {

        return requestTemplate -> {
            String token = tokenService.getAccessToken().block();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
