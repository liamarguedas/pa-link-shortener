package com.sode.lslink.service;

import com.sode.lslink.client.FeignUser;
import com.sode.lslink.entity.Link;
import com.sode.lslink.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LinkService {

	@Autowired
	private LinkRepository repository;

	@Autowired
	private FeignUser userRepository;

	private Boolean isRegisteredUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth != null &&
				auth.isAuthenticated() &&
				!"anonymousUser".equals(auth.getPrincipal());
	}

	private Long getRegisteredUserId(){

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth instanceof JwtAuthenticationToken jwtAuth) {

			String id = jwtAuth.getToken().getId();

			return Long.parseLong(id);

		} else {

			return null;

		}
	}

	public List<Link> getAllLinks(){
		
		return repository.findAll();
		
	}

	public String createNewLink(String url) {

		Instant now = Instant.now();
		Instant expiration = isRegisteredUser() ? now.plus(5, ChronoUnit.YEARS) : now.plus(1, ChronoUnit.DAYS);

		Link l = new Link(null, url, expiration);

		if( isRegisteredUser() ) {
			userRepository.appendLink(getRegisteredUserId(), l);
		}

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
