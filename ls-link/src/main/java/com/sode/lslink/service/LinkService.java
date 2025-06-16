package com.sode.lslink.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sode.lslink.entity.Link;
import com.sode.lslink.repository.LinkRepository;

@Service
public class LinkService {

	@Autowired
	private LinkRepository repository;
	
	public List<Link> getAllLinks(){
		
		return repository.findAll();
		
	}

	public String createNewLink(String url) {

		// Adjust as needed (1 day expiration limit)
		Instant now = Instant.now();
		Instant tomorrow = now.plus(2, ChronoUnit.MINUTES);
		// ------------------------------------------
		Link l = new Link(null, url, tomorrow);
		repository.save(l);

		return l.getAccesssKey();
	}

	public String getRedirect(String accessKey) {

		Link l = repository.findByAccessKey(accessKey);

		return l.getRedirect();
	}
	
	
	public void deleteLink(Long id) {
		repository.deleteById(id);
	}

}
