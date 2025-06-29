package com.sode.lsapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.sode.lsapigateway.client")
@SpringBootApplication
public class LsApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsApiGatewayApplication.class, args);
	}

}
