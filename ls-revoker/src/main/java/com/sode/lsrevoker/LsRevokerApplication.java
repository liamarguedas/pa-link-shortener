package com.sode.lsrevoker;

import com.sode.lsrevoker.config.PropertyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients(basePackages = "com.sode.lsrevoker.client")
@EnableDiscoveryClient
@EnableConfigurationProperties(PropertyConfig.class)
@SpringBootApplication
public class LsRevokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LsRevokerApplication.class, args);
	}

}
