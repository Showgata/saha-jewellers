
package com.sahaJwellers.app.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

@EnableWebSecurity
public class MortgageAppSecurity  extends WebSecurityConfigurerAdapter  {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService());
		//auth.inMemoryAuthentication().withUser("arka").password("password").roles("USER");
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		userDetails.add(User.withDefaultPasswordEncoder().username("admin").password("password").roles("USER","ADMIN").build());
		userDetails.add(User.withDefaultPasswordEncoder().username("arka").password("password").roles("USER").build());
		return new InMemoryUserDetailsManager(userDetails);
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
		 	    .antMatchers("/resources/**","/css/*","/js/**","/fonts/**","/images/**","/fontawesome/**").permitAll()
		        .antMatchers("/login*").anonymous()
				.antMatchers("/**").hasRole("USER")
				.anyRequest().authenticated()
		 .and()
		 	.formLogin().loginPage("/login").successHandler(successHandler()).failureUrl("/login?error=true").permitAll()
		 .and().logout().logoutSuccessUrl("/index?logout=true");
		super.configure(http);
	}
	
	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);    
	    return firewall;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    //@formatter:off
	    super.configure(web);
	    web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
	
	}
}
