package com.inventorymanagement.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorymanagement.model.User;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	
	private final AuthService authService;
	private final UserRepository userRepository;
	
	public AuthController(AuthService authService,UserRepository userRepository) {
		this.authService=authService;
		this.userRepository=userRepository;
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> login(@RequestBody Map<String,String> credentials){
		String username=credentials.get("username");
		String password=credentials.get("password");
		
		if(username == null || username.isBlank() || password == null || password.isBlank()) {
			return ResponseEntity.badRequest()
												.body(Map.of("error","Username and Password are required"));
		}
		try {
			String token = authService.login(username, password);
			return ResponseEntity.ok(Map.of("token",token));
		}catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
												.body(Map.of("error","Invalid username or password"));
		}
	}
	
	@PostMapping("/setup-owner")
	public ResponseEntity<Map<String,String>> setupOwner(@RequestBody User user){
		if(userRepository.existsByRole("OWNER")) {
			return ResponseEntity.status(403)
												.body(Map.of("error","An Owner account already exists"));
		}
		try {
			user.setRole("OWNER");
			authService.register(user);
			return ResponseEntity.ok(Map.of("message","Initial Owner created successfully"));
		} catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest()
												.body(Map.of("error",e.getMessage()));
		}
	}
	
	@PostMapping("/register")
	public  ResponseEntity<Map<String,String>> register(@RequestBody User user, Authentication authentication){
		if(authentication == null) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED)
																	.body(Map.of("error","Authorization required"));
		}
		
		String currentRole = authentication.getAuthorities()
															.stream()
															.map(authority -> authority.getAuthority())
															.filter(authority -> authority.equals("ROLE_OWNER") || authority.equals("ROLE_MANAGER"))
															.findFirst()
															.orElse("");
		String requestedRole = user.getRole() == null ? "" : user.getRole().trim().toUpperCase();
		
		if(currentRole.equals("ROLE_OWNER")) {
			if(!requestedRole.equals("MANAGER") && !requestedRole.equals("EMPLOYEE")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
																	.body(Map.of("error","Owner can create Manager and Employee only"));
			}
		}else if(currentRole.equals("ROLE_MANAGER")) {
			if(!requestedRole.equals("EMPLOYEE")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
																	.body(Map.of("error","Manager can create Employee only"));
			}
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(Map.of("error","You are not allowed to register users"));
		}
		try {
			authService.register(user);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(Map.of("message","User registered sucessfully")); 
		}catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest()
					.body(Map.of("error",e.getMessage()));
		}
	}
	
	@GetMapping("/me")
	public ResponseEntity<Map<String,String>> getCurrentUser(Authentication authentication){
		if(authentication == null || !authentication.isAuthenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("error","Authorization required"));
		}
		String username = authentication.getName();
		String role = authentication.getAuthorities()
				.stream()
				.map(authority -> authority.getAuthority())
				.filter(authority -> authority.startsWith("ROLE_"))
				.map(authority -> authority.substring(5))
				.findFirst()
				.orElse("UNKNOWN");
		
		return ResponseEntity.ok(Map.of("username",username,"role",role));
	}
}
