package com.sode.lsuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sode.lsuser.entity.Role;
import com.sode.lsuser.entity.User;
import com.sode.lsuser.repository.UserRepository;

@Service
public class UserService {
	
	private static Role userRole = new Role(2l, "ROLE_USER");
	private static Role adminRole = new Role(1l, "ROLE_ADMIN");

	@Autowired
	private UserRepository userRepository;
	
	public List<User> checkAll() {
		return userRepository.findAll();
	}
	
	
	public void createUser(User u) {
		u.getRoles().add(userRole);
		userRepository.save(u);
	}

}
