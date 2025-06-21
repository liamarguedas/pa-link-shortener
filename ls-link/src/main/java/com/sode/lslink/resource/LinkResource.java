package com.sode.lslink.resource;

import com.sode.lslink.dto.LinkDTO;
import com.sode.lslink.entity.Link;
import com.sode.lslink.service.LinkService;
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
	
	@GetMapping("/all")
	public ResponseEntity<List<Link>> getAllLinks(){
		List<Link> links = service.getAllLinks();
		return ResponseEntity.ok().body(links);
	}
	
	@GetMapping("/redirect/{accessKey}")
	public RedirectView redirect(@PathVariable String accessKey){
		String redirectUrl = service.getRedirect(accessKey);
		return new RedirectView(redirectUrl);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createNewLink(
			@AuthenticationPrincipal Jwt jwt,
			@RequestBody LinkDTO redirect
	){

		Long id = (jwt != null) ? Long.parseLong(jwt.getSubject()) : null;
		String accessKey = service.createNewLink(redirect.getLink(), id);

		return ResponseEntity.ok().body(accessKey);
	}

	@PreAuthorize("hasAuthority('SCOPE_link:revoke')")
	@DeleteMapping("/revoke/{id}")
	public ResponseEntity<Void> revokeLink(
			@PathVariable Long id){
		service.deleteLink(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
}
