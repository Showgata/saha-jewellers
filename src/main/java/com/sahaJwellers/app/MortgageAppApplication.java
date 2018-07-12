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
	
/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("arka").password("password").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
				.antMatchers("/mortgage-app/").hasRole("USER")
				.anyRequest().authenticated()
				.antMatchers("/").permitAll();
		
		 http.formLogin().loginPage("/index.html").successForwardUrl("/mortgage-app/").failureForwardUrl("/");
		 
		 http.logout().permitAll();
			
		super.configure(http);
	}*/
	
}
