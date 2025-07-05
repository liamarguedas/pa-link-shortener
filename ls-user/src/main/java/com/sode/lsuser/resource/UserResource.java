package com.sode.lsuser.resource;

import com.sode.lsuser.client.FeignLink;
import com.sode.lsuser.dto.UserDTO;
import com.sode.lsuser.entity.Link;
import com.sode.lsuser.entity.User;
import com.sode.lsuser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private FeignLink linkService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

	@GetMapping("/{id}/links")
	public ResponseEntity<List<Link>> getLinksByUserId(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable Long id) {

		String username = jwt.getSubject();
		User u = userService.findById(id);

		if ( username.equals(u.getUsername()) ) {

			List<Link> links = linkService.getAllLinksByUsername(u.getUsername()).getBody();
			return ResponseEntity.ok().body(links);
		}

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

	}

	@PostMapping("/register")
	public ResponseEntity<Void> createUser(@RequestBody UserDTO udto) {

		User u = new User(null, udto.getName(), udto.getUsername(), udto.getEmail(), passwordEncoder.encode(udto.getPassword()));

		try {
			userService.createUser(u);
			logger.info("User created: " + udto.getUsername());

		} catch (Exception e) {
			// TODO -------------------------------------
			// HANDLE USERNAME/EMAILS ALREADY REGISTERED!
			logger.error(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
