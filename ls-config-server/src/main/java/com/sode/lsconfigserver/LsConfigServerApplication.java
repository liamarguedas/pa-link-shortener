package com.sode.lsconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class LsConfigServerApplication{

    public static void main(String[] args) {
        SpringApplication.run(LsConfigServerApplication.class, args);
    }

}
