package com.sode.lsapigateway;

import com.sode.lsrevoker.config.FeignConfiguration;
import com.sode.lsrevoker.entity.Link;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface FeignLink {
	
	@GetMapping("/all")
	public ResponseEntity<List<Link>> getAllLinks();

	@DeleteMapping("/revoke/{id}")
	public ResponseEntity<Void> revokeLink(@PathVariable("id") Long id);

}


