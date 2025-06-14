package com.sode.lsuser.config;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.sode.lsuser.entity.User;
import com.sode.lsuser.repository.UserRepository;


@Configuration
public class Loader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		// UNCOMMEND FOR NON-SQL TESTING
		//User u1 = new User(null, "Liam Arguedas", "liano", "liano@email.com", "test123");
		//User u2 = new User(null, "Liam Arguedas 2", "liano2", "liano2@email.com", "test123");
	}
}
