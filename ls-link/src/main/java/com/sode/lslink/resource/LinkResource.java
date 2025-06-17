package com.sode.lslink.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.sode.lslink.dto.LinkDTO;
import com.sode.lslink.entity.Link;
import com.sode.lslink.service.LinkService;

@RestController
@RequestMapping("/urls")
public class LinkResource {

	@Autowired
	private LinkService service;
	
	@GetMapping
	public ResponseEntity<List<Link>> getAllLinks(){
		List<Link> links = service.getAllLinks();
		return ResponseEntity.ok().body(links);
	}
	
	@GetMapping("/{accessKey}")
	public RedirectView redirect(@PathVariable String accessKey){
		String redirectUrl = service.getRedirect(accessKey);
		return new RedirectView(redirectUrl);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createNewLink(@RequestBody LinkDTO redirect){
		String accessKey = service.createNewLink(redirect.getLink());
		return ResponseEntity.ok().body(accessKey);
	}
	
	@DeleteMapping("/revoke/{id}")
	public ResponseEntity<Void> revokeLink(@PathVariable Long id){
		service.deleteLink(id);
		return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
	}
	
	
}
