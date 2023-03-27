package com.nttdata.bank.transaction.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BankTransactionAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransactionAccountServiceApplication.class, args);
	}

}
