package com.sahaJwellers.app.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query("select u from User u where u.username = :username or u.password = :password")
	public Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
