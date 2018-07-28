
package com.sahaJwellers.app.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

//import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.service.UserService;

@Configuration
@EnableWebSecurity
public class MortgageAppSecurity  extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
		//auth.inMemoryAuthentication().withUser("arka").password("password").roles("USER");
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
       return new MortgageUserDetailsService();
	}
	
	
	
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable()
		     .authorizeRequests()
		 	    .antMatchers("/resources/**","/css/*","/js/**","/fonts/**","/images/**","/fontawesome/**","/mortgage-app/api/**").permitAll()
		        .antMatchers("/login*").anonymous()
				//.antMatchers("/mortgage-app/web/**").hasRole("USER")
				.anyRequest().authenticated()
		 .and()
		 	.formLogin().loginPage("/login").successHandler(successHandler()).failureUrl("/login?error=true").permitAll()
		 .and().logout().logoutSuccessUrl("/login?logout=true");
		 
			http.sessionManagement().maximumSessions(10);
        	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        	http.sessionManagement().sessionFixation().migrateSession();
        	http.sessionManagement().invalidSessionUrl("/login?invalidSession=true").enableSessionUrlRewriting(true);
		 
		super.configure(http);
	}
	
	/*@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);    
	    return firewall;
	}*/
	
/*	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth)
	            throws Exception {
	        auth.authenticationProvider(authProvider());
	    }
	 	*/
/*	  @Bean 
	  public AuthenticationProvider authProvider() {
	        // The custom authentication provider defined for this app
	        CustomUserDetailsAuthenticationProvider provider = new CustomUserDetailsAuthenticationProvider(
	                passwordEncoder(), userDetailsService);
	        return provider;
	   }*/
	 
	  @Bean(name = "passwordEncoder")
	  public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	  }
	
	@Bean
	public HttpFirewall defaultHttpFirewall() {
	    return new DefaultHttpFirewall();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    //@formatter:off
	    super.configure(web);
	    web.httpFirewall(defaultHttpFirewall());
	
	}
}
