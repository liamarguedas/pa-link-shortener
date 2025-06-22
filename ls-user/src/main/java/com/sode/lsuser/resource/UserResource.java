package com.sode.lsuser.resource;

import com.sode.lsuser.client.FeignLink;
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

	@Autowired
	private FeignLink linkService;

	/* ADD PAGINATION */
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

	@GetMapping("/{id}/links")
	public ResponseEntity<List<Link>> getLinksByUserId(@PathVariable Long id) {

		User u = userService.findById(id);
		List<Link> links = linkService.getAllLinksByUsername(u.getUsername()).getBody();

		return ResponseEntity.ok().body(links);
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
