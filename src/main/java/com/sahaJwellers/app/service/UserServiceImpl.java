package com.sahaJwellers.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	public UserRepository userRepository;
	
	@Override
	public Optional<User> fetchUserByUserId(Long id){
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser;
	}
	@Override
	public User saveUser(User user){
		return userRepository.save(user);
	}
	@Override
	public Optional<User> fetchUserByUsernameAndPassword(String username, String password){
		return userRepository.findByUsernameAndPassword(username, password);
	}
	
	@Override
	public Optional<User> fetchUserByUsername(String username){
		return userRepository.findByUsername(username);
	}
	
	@Override
	public List<User> fetchAllUser(){
		return userRepository.listAllUser();
	}
	
	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
