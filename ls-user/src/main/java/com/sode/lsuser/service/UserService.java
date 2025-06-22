package com.sode.lsuser.service;

import com.sode.lsuser.entity.Role;
import com.sode.lsuser.entity.User;
import com.sode.lsuser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
	
	private static Role userRole = new Role(2l, "ROLE_USER");
	private static Role adminRole = new Role(1l, "ROLE_ADMIN");

	@Autowired
	private UserRepository userRepository;
	
	public List<User> checkAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		User u = userRepository.findById(id).orElse(null);
		return u;
	}

	public User findByUsername(String username) {
		User u = userRepository.findByUsername(username);
		return  u;
	}

	public void createUser(User u) {
		u.getRoles().add(userRole);
		userRepository.save(u);
	}

}
