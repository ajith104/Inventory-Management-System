package com.inventorymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorymanagement.model.User;
import com.inventorymanagement.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")

public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/add")
	public User addUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@GetMapping
	public List<User> getAllUesrs() {
		return userService.getAllUser();
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}
	
	@PutMapping("/{id}")
	public void updateUserRole(
	        @PathVariable Long id,
	        @RequestBody User user,
	        Authentication authentication) {

	    String username = authentication.getName();

	    String role = authentication.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .filter(authority -> authority.startsWith("ROLE_"))
	            .map(authority -> authority.substring(5))
	            .findFirst()
	            .orElse("");

	    userService.updateUserRole(
	            id, user.getRole(), username, role);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id, Authentication authentication) {

	    String username = authentication.getName();

	    String role = authentication.getAuthorities()
	            .stream()
	            .map(GrantedAuthority::getAuthority)
	            .filter(authority -> authority.startsWith("ROLE_"))
	            .map(authority -> authority.substring(5))
	            .findFirst()
	            .orElse("");

	    userService.deleteUser(id, username, role);
	}

}
