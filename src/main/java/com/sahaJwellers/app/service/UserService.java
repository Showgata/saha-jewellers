package com.sahaJwellers.app.service;

import java.util.Optional;

import com.sahaJwellers.app.model.User;

public interface UserService {

	Optional<User> fetchUserByUserId(Long id);

	User saveUser(User user);

	Optional<User> fetchUserByUsernameAndPassword(String username, String password);

	Optional<User> fetchUserByUsername(String username);

}
