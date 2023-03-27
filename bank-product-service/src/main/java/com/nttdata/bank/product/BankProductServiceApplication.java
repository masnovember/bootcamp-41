package com.nttdata.bank.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankProductServiceApplication.class, args);
	}

}
