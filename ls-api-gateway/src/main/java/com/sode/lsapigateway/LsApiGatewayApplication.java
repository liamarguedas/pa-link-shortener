package com.sode.lsapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LsApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsApiGatewayApplication.class, args);
	}

}
