package com.sode.lsuser.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sode.lsuser.dto.UserDTO;
import com.sode.lsuser.entity.User;
import com.sode.lsuser.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@GetMapping("/all")
	public ResponseEntity<List<User>> checkAll() {

		List<User> all = userService.checkAll();

		return ResponseEntity.ok().body(all);
	}

	@PostMapping("/register")
	public ResponseEntity<Void> createUser(@RequestBody UserDTO udto) {

		User u = new User(null, udto.getName(), udto.getUsername(), udto.getEmail(), udto.getPassword());

		try {
			userService.createUser(u);
		} catch (Exception e) {

			// TODO -------------------------------------
			// HANDLE USERNAME/EMAILS ALREADY REGISTERED!
			e.printStackTrace();

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();

	}

}
