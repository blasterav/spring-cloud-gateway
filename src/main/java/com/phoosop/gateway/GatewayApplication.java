package com.phoosop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
//		BlockHound.install();
		SpringApplication.run(GatewayApplication.class, args);
	}

}
