package com.sode.lsuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sode.lsuser.entity.User;
import com.sode.lsuser.repository.UserRepository;

@Service
public class UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> checkAll() {
		return userRepository.findAll();
	}
	
	
	public void createUser(User u) {
		userRepository.save(u);
	}

}
