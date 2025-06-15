package com.sode.lslink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
public class LsLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsLinkApplication.class, args);
	}

}
