package com.sode.lslink.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sode.lslink.entity.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
	
	
	Link findByAccessKey(String accessKey);

}
