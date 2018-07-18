package com.sahaJwellers.app.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.service.UserService;

@Component
public class MortgageUserDetailsService implements UserDetailsService {
  

    @Autowired
    private UserService userService;
    
    public MortgageUserDetailsService() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Optional<User> usr = userService.fetchUserByUsername(username);
        if (!usr.isPresent()) {
            throw new UsernameNotFoundException("User not found by name: " + username);
        }
        User user = usr.get();
      
        return toUserDetails(user);
    }

    private UserDetails toUserDetails(User userObject) {
    
        return org.springframework.security.core.userdetails.User.withUsername(userObject.getUsername())
                   .password(userObject.getPassword())
                   .roles(userObject.getRole()).build();
    }

    private static class UserObject {
        private String name;
        private String password;
        private String role;

        public UserObject(String name, String password, String role) {
            this.name = name;
            this.password = password;
            this.role = role;
        }
    }
}