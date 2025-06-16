package com.sode.lsrevoker.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sode.lsrevoker.entity.Link;

@FeignClient(name="ls-link", path="/url")
public interface FeignLink {
	
	@GetMapping
	public ResponseEntity<List<Link>> getAllLinks();
	
	
	@DeleteMapping("/revoke/{id}")
	public ResponseEntity<Void> revokeLink(@PathVariable Long id);

}


