package com.sahaJwellwes.app;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sahaJwellers.app.MortgageAppApplication;
import com.sahaJwellers.app.model.User;
import com.sahaJwellers.app.repository.UserRepository;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=MortgageAppApplication.class)
public class MortgageAppApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void contextLoads() {
	}
	
//@Test
//	@Transactional
//	public void createUser() {
//	User user = new User();
//		user.setPassword(new BCryptPasswordEncoder().encode("password"));
//		user.setRole("USER");
//		user.setUsername("arka");
//		userRepository.save(user);
//	}

}

