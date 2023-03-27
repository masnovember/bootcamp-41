package com.nttdata.bank.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankGatewayServiceApplication.class, args);
	}

}
