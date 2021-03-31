package com.denisakulov.springboot.spring_boot_mvc.controller;

import com.denisakulov.springboot.spring_boot_mvc.model.AuthenticationProviderElse;
import com.denisakulov.springboot.spring_boot_mvc.model.Role;
import com.denisakulov.springboot.spring_boot_mvc.model.User;
import com.denisakulov.springboot.spring_boot_mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<User>> showUsers() {
		List<User> users = userService.listUsers();
		return users != null && !users.isEmpty()
				? new ResponseEntity<>(users, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/roles")
	public ResponseEntity<List<Role>> showRoles() {
		List<Role> roles = userService.listRoles();
		return roles != null && !roles.isEmpty()
				? new ResponseEntity<>(roles, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	@GetMapping(value = "/user/principal")
	public ResponseEntity<Object> getPrincipal(Principal principal) {
		User user = null;
		if (principal instanceof OAuth2AuthenticationToken) {
			user = userService.findByPrincipal(principal);

		} else {
			user = userService.findByUsername(principal.getName());
		}
		return user != null
				? new ResponseEntity<>(user, HttpStatus.OK)
				: new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/user/{id}")
	public ResponseEntity<Object> showUserById(@PathVariable("id") Long id) {
		User user = userService.getById(id);
		return user != null
				? new ResponseEntity<>(user, HttpStatus.OK)
				: new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/users")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);

	}

	@PutMapping(value = "/users")
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		userService.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "/users/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
		Long user_id = userService.delete(id);
		return user_id != null
				? new ResponseEntity<>("User was deleted", HttpStatus.OK)
				: new ResponseEntity<>("Not Found",HttpStatus.NOT_FOUND);
	}
}