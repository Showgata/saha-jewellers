package com.sahaJwellers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.sahaJwellers.app")
public class MortgageAppApplication extends SpringBootServletInitializer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(MortgageAppApplication.class);
	 }
	
	public static void main(String[] args) {
		SpringApplication.run(MortgageAppApplication.class, args);
	}		
}
