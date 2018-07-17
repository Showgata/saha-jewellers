package com.sahaJwellers.app.restController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.service.UserService;

@RestController
@RequestMapping("/modgage-app/api/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path="/",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public User login(@RequestBody User user) {
		
		Optional<User> optionalUser = userService.fetchUserByUsernameAndPassword(user.getUsername(), user.getPassword());

		if(optionalUser.isPresent()){
			return optionalUser.get();
		} else {
			return user;
		}
	}
	
	@RequestMapping(path="/",method=RequestMethod.GET)
	public User getSampleUser() {
		User user1 = new User();
		user1.setUserId(1L);
		user1.setPassword("password");
		user1.setUsername("user");
		return user1;
	}
}
