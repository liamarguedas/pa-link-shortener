package com.sode.lsoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LsOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsOauthApplication.class, args);
	}

}
