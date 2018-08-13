package com.sahaJwellers.app.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.model.Voucher;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	
	@Query("select u from User u where u.username = :username or u.password = :password")
	public Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
	
	@Query("select u from User u where u.username = :username")
	public Optional<User> findByUsername(@Param("username") String username);
	
	@Query("select u from User u  ORDER BY u.updateTimestamp DESC")
	public List<User> listUser();
}
