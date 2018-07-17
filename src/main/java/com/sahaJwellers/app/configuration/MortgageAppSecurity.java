<<<<<<< HEAD
/*package com.sahaJwellers.app.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MortgageAppSecurity  extends WebSecurityConfigurerAdapter  {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("arka").password("password").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
				.antMatchers("/mortgage-app/").hasRole("USER")
				.anyRequest().authenticated()
				.antMatchers("/").permitAll()
		 .and().
		 		formLogin().loginPage("/index").permitAll()
		 .and().logout().permitAll();
			
		super.configure(http);
	}
}
=======
/*package com.sahaJwellers.app.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MortgageAppSecurity  extends WebSecurityConfigurerAdapter  {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("arka").password("password").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
				.antMatchers("/mortgage-app/").hasRole("USER")
				.anyRequest().authenticated()
				.antMatchers("/").permitAll()
		 .and().
		 		formLogin().loginPage("/index").permitAll()
		 .and().logout().permitAll();
			
		super.configure(http);
	}
}
>>>>>>> e5a3f8cc8bbf944746bb398b37e879e387199c5d
*/