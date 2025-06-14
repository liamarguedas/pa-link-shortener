package com.sode.lsuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sode.lsuser.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	
}
