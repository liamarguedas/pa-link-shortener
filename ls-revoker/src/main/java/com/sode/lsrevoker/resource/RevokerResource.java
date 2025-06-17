package com.sode.lsrevoker.resource;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sode.lsrevoker.entity.Link;
import com.sode.lsrevoker.service.RevokerService;

@Service
public class RevokerResource {
	
	@Autowired
	private RevokerService service;
	
	private static final Logger logger = LoggerFactory.getLogger(RevokerResource.class);
	
	@Scheduled(fixedRate = 60000, initialDelay = 120000)
	public void revoke() {
		
		logger.info("----- Executing revoke -----");
		
		Instant now = Instant.now();

		List<Link> links = service.getAllLinks();
		
		logger.info("Current links in memory: " + links.size());
		
		
		List<Link> toDelete = links.stream()
						.filter( l -> now.isAfter(l.getExpiration()))
						.toList();
		service.revokeAllLinks(toDelete);
		
		for (Link link : links) {
			
			logger.info("Link ID: " + link.getId());
			logger.info("Current date: " + now);
			logger.info("Expiration date: " + link.getExpiration());
			logger.info("Needs to be deleted: " + now.isAfter(link.getExpiration()));
			
		}
		
		logger.info("Links to be revoke: " + toDelete.size());

		logger.info("Total revoked: " + toDelete.size());
		logger.info("----- Revoke completed -----");
			
		}
	}
