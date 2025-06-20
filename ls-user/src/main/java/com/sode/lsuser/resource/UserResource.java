package com.sode.lsuser.resource;

import com.sode.lsuser.dto.UserDTO;
import com.sode.lsuser.entity.Link;
import com.sode.lsuser.entity.User;
import com.sode.lsuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		User u = userService.findById(id);
		return ResponseEntity.ok().body(u);
	}

	@PostMapping("/append")
	public ResponseEntity<Void> appendLink (@RequestBody Long id, @RequestBody Link link) {
		userService.appendLink(id, link);
		return ResponseEntity.noContent().build();
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
