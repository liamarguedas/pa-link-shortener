package com.sode.lsuser.security;

import com.sode.lsuser.config.PropertyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private final WebClient webClient;

    private final PropertyConfig config;

    public TokenService(WebClient.Builder webClientBuilder, PropertyConfig propertyConfig) {
        this.config = propertyConfig;
        this.webClient = webClientBuilder.baseUrl(config.getIssuer()).build();
    }
    public Mono<String> getAccessToken() {
        logger.info("Requesting token");

        return webClient.post()
                .uri(config.getIssuer() + "/oauth2/token")
                .headers(headers -> headers.setBasicAuth(config.getClient(), config.getSecret()))
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("scope", "service:user"))
                .retrieve()
                .bodyToMono(Map.class)
                .map(body -> {
                    Object token = body.get("access_token");
                    if (token == null) {
                        logger.warn("Access token not found in response: " + body);
                        return null;
                    }
                    logger.info("Access token received");
                    return token.toString();
                });
    }
}
