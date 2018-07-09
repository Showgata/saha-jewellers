package com.sahaJwellers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sahaJwellers.app")
public class MortgageAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MortgageAppApplication.class, args);
	}
}
