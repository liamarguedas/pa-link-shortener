package com.sode.lslink.repository;
import com.sode.lslink.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {
	
	Link findByAccessKey(String accessKey);
	List<Link> findByOwner(String username);

}
