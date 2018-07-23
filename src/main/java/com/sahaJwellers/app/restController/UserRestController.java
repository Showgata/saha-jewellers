package com.sahaJwellers.app.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.service.UserService;

@RestController
@RequestMapping("/modgage-app/api/user")
public class UserRestController {
	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public List<User> fetchUserList(){
		return userService.fetchAllUser();
	}
	
	@PostMapping("/")
	public User saveUser(@RequestBody User user) {
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		return userService.saveUser(user);
	}
	
	@GetMapping("/{id}")
	public User fetchUserById(@PathVariable("id") Long id) {
		if( userService.fetchUserByUserId(id) .isPresent())
		return userService.fetchUserByUserId(id).get();
		else 
			return null;
	}
	
	@PostMapping("/{id}")
	public void deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}
	
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	 }
}
