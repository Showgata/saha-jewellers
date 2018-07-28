package com.sahaJwellers.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;	
	
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = findUserbyUserName(username);
	  Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); 
          
    // CustomUserDetails customUserDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), grantedAuthorities);
    
    UserBuilder builder = null;
    if (user != null) {
      builder = org.springframework.security.core.userdetails.User.withUsername(username);
      builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
      builder.roles(user.getRole());
    } else {
      throw new UsernameNotFoundException("User not found.");
    }

   return builder.build();
     //return customUserDetails;
  }
  
  /*public UserDetails loadUserByUsername(String userName , String password) throws UsernameNotFoundException{
	  CustomUserDetails customUserDetails = null;
	  if (StringUtils.isBlank(userName) && StringUtils.isBlank(password)) {
          throw new UsernameNotFoundException(
                  "Username and password must be provided");
      }
	  
	  Optional<User> user = userRepository.findByUsername(userName);
	  if(user.isPresent()) {
		  user = userRepository.findByUsernameAndPassword(userName, password);
		  if(user.isPresent()) {
			  User usr = user.get();
			  Set<GrantedAuthority> grantedAuthorities = new HashSet<>(); 
		        for (String role : usr.getRoles()) {
		            grantedAuthorities.add(new SimpleGrantedAuthority(role));
		        }		        
		       customUserDetails = new CustomUserDetails(usr.getUsername(), usr.getPassword(), grantedAuthorities);
		  	}  else {
		  		throw new UsernameNotFoundException("User "+userName+" not found with password : "+password);
		  	}
	  } else {
		  throw new UsernameNotFoundException("User "+userName+" not found ");
	  }
	return customUserDetails;
  }*/

  private User findUserbyUserName(String username) {
	  
	  Optional<User> user = userRepository.findByUsername(username);
	  if(user.isPresent()) {
		  return user.get();
	  }
    /*if(username.equalsIgnoreCase("admin")) {
      return new User(username, "admin123", "ADMIN");
    }*/
    return null;
  }
}