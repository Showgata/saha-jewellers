package com.sahaJwellers.app.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.service.UserService;

@RestController
@RequestMapping("/mortgage-app/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(path="/",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public User saveUser(@RequestBody User user) {
		user = userService.saveUser(user);
		System.out.println("saved User =>"+user);
		return user;
	}
	
	@GetMapping("/")
	public List<User> fetchAllUser(){
		return userService.findAll();
	}
	
	@GetMapping("/{id}")
	public User findUserById(@PathVariable("id") Long id){
		return userService.fetchUserByUserId(id).get();
	}
	
	@PostMapping("/{id}")
	public void deleteUserById(@PathVariable("id") long id){
		userService.deleteUserById(id);
	}
}
