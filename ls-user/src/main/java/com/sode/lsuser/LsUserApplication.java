package com.sode.lsuser;

import com.sode.lsuser.config.PropertyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(PropertyConfig.class)
public class LsUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsUserApplication.class, args);
	}

}
