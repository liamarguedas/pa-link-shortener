package com.sode.lslink.resource;

import com.sode.lslink.dto.LinkDTO;
import com.sode.lslink.entity.Link;
import com.sode.lslink.service.LinkService;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/urls")
public class LinkResource {

	@Autowired
	private LinkService service;

	private static final Logger logger = LoggerFactory.getLogger(LinkResource.class);

	@PreAuthorize("hasAuthority('SCOPE_service:revoke')")
	@GetMapping("/all")
	public ResponseEntity<List<Link>> getAllLinks(){

		List<Link> links = service.getAllLinks();

		return ResponseEntity.ok().body(links);
	}

	@PreAuthorize("hasAuthority('SCOPE_service:revoke')")
	@DeleteMapping("/revoke/{id}")
	public ResponseEntity<Void> revokeLink(
			@PathVariable("id") Long id){

		logger.info("Revoke requesition from: ls-revoke, {}", id);
		service.deleteLink(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PreAuthorize("hasAuthority('SCOPE_service:user')")
	@GetMapping("/all/{username}")
	public ResponseEntity<List<Link>> getAllLinksByUsername(
			@PathVariable("username") @Nonnull String username
	){

		List<Link> links = service.findByUsername(username);

		return ResponseEntity.ok().body(links);
	}

	@GetMapping("/redirect/{accessKey}")
	public RedirectView redirect(@PathVariable("accessKey") String accessKey){
		String redirectUrl = service.getRedirect(accessKey);
		return new RedirectView(redirectUrl);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createNewLink(
			@AuthenticationPrincipal Jwt jwt,
			@RequestBody LinkDTO redirect
	){

		String username = (jwt != null) ? jwt.getSubject() : null;
		logger.info("Adding link: " + redirect.getLink() + " , to user: " + username);

		String accessKey = service.createNewLink(redirect.getLink(), username);

		return ResponseEntity.ok().body(accessKey);
	}



	
}
