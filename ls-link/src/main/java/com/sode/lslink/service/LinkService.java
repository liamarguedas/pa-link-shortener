package com.sode.lslink.service;

import com.sode.lslink.entity.Link;
import com.sode.lslink.repository.LinkRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LinkService {

	@Autowired
	private LinkRepository repository;

	public List<Link> getAllLinks(){
		return repository.findAll();
	}

	public List<Link> findByUsername(String username){
		List<Link> links = repository.findByOwner(username);
		return links;
	}

	public String createNewLink(
			@Nonnull String url,
			@Nullable String username) {


		Boolean isRegisteredUser = username != null;

		Instant now = Instant.now();
		Instant expiration = isRegisteredUser ? now.plus(5, ChronoUnit.YEARS) : now.plus(1, ChronoUnit.DAYS);

		Link l = new Link(null, url, expiration, username);

		repository.save(l);
		return l.getAccesssKey();
	}

	public String getRedirect(
			@Nonnull String accessKey) {

		Link l = repository.findByAccessKey(accessKey);
		return l.getRedirect();
	}
	
	
	public void deleteLink(
			@Nonnull Long id) {
		repository.deleteById(id);
	}

}
