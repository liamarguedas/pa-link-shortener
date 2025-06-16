package com.sode.lsrevoker.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sode.lsrevoker.entity.Link;
import com.sode.lsrevoker.service.RevokerService;

@RestController
@RequestMapping(value = "/test")
public class TestResource {
	
	
	@Autowired
	private RevokerService service;
	
	@GetMapping
	public ResponseEntity<List<Link>> getAll(){
		return ResponseEntity.ok().body(service.getAllLinks());
	}

}
