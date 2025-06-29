package com.sode.lsapigateway.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
public class GatewayResource {

    @Autowired
    private  WebClient.Builder webClienBuilder;

    @GetMapping("/{accessKey}")
    public Mono<ResponseEntity<Void>> redirect(@PathVariable("accessKey") String accessKey) {
        return webClienBuilder.build()
                .get()
                .uri("http://ls-link/urls/redirect/{accessKey}", accessKey)
                .retrieve()
                .bodyToMono(String.class)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(url))
                .build());
    }

}
