package com.sahaJwellers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.sahaJwellers.app")
public class MortgageAppApplication extends SpringBootServletInitializer implements WebMvcConfigurer{

	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(MortgageAppApplication.class);
	 }
	
	public static void main(String[] args) {
		SpringApplication.run(MortgageAppApplication.class, args);
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	
}
