package com.sode.lsuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class LsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsUserApplication.class, args);
	}

}
