package com.sode.lsrevoker.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class TokenService {

    @Value("${property.issuer}")
    private String authServer;

    @Value("${property.client}")
    private String revokerClient;

    @Value("${property.secret}")
    private String revokerClientSecret;

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private final WebClient webClient;

    public TokenService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(authServer).build();
    }
    public Mono<String> getAccessToken() {
        logger.info("Requesting token");

        return webClient.post()
                .uri(authServer + "/oauth2/token")
                .headers(headers -> headers.setBasicAuth(revokerClient, revokerClientSecret))
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("scope", "service:revoke"))
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
