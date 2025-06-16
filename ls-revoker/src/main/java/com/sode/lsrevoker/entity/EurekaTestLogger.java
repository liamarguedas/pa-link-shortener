package com.sode.lsrevoker.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class EurekaTestLogger {

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostConstruct
    public void logServices() {
        System.out.println("Eureka services: " + discoveryClient.getServices());
    }
}