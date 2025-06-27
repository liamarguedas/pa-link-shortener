package com.sode.lsrevoker.resource;

import com.sode.lsrevoker.config.PropertyConfig;
import com.sode.lsrevoker.entity.Link;
import com.sode.lsrevoker.service.RevokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.List;

@Service
public class RevokerResource {

	@Autowired
	private RevokerService service;

	private String issuer;

	private String client;

	private String secret;

	private final PropertyConfig config;

	private final WebClient webClient;

	public RevokerResource(WebClient.Builder webClientBuilder, @Qualifier("ls-com.sode.lsrevoker.config.PropertyConfig") PropertyConfig config) {
		this.config = config;
		this.webClient = webClientBuilder.build();

		this.issuer = config.getIssuer();
		this.client = config.getClient();
		this.secret = config.getSecret();
	}

	private static final Logger logger = LoggerFactory.getLogger(RevokerResource.class);


	@Scheduled(fixedRate = 60000, initialDelay = 30000)
//    @Scheduled(fixedRate = 60000, initialDelay = 120000)
	public void revoke() {

		logger.info("----- Executing revoke -----");
		List<Link> links = service.getAllLinks();
		logger.info("Current links in memory: " + links.size());

		Instant now = Instant.now();
		List<Link> toDelete = links.stream()
				.filter(l -> now.isAfter(l.getExpiration()))
				.toList();

		logger.info("Links to be revoke: " + toDelete.size());

		service.revokeAllLinks(toDelete);

		for (Link link : links) {

			logger.info("Link ID: " + link.getId());
			logger.info("Current date: " + now);
			logger.info("Expiration date: " + link.getExpiration());
			logger.info("Needs to be deleted: " + now.isAfter(link.getExpiration()));

		}

		logger.info("Total revoked: " + toDelete.size());
		logger.info("----- Revoke completed -----");
		logger.info("Links to be revoke: " + toDelete.size());
	}
}
