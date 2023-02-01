package com.example.initiativeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InitiativeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitiativeServiceApplication.class, args);
	}

}
