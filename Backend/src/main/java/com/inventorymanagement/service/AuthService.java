package com.inventorymanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.User;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.security.JwtService;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtService jwtService) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
	}
	
	public User register(User user) {
		if(user.getUsername() == null || user.getUsername().isBlank() || user.getPassword() == null || user.getPassword().isBlank()) {
			throw new IllegalArgumentException("Username and Password are required");
		}
		
		if(userRepository.findByUsername(user.getUsername()) != null) {
			throw new IllegalArgumentException("Username already exists");
		}
		
		if(user.getRole() == null || user.getRole().isBlank()){
			throw new IllegalArgumentException("Role is required");
		}
		
		String role = user.getRole().trim().toUpperCase();
		
		if(!role.equals("OWNER") && !role.equals("MANAGER") && !role.equals("EMPLOYEE")) {
			throw new IllegalArgumentException("Invalid role");
		}
		
		user.setRole(role);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}
	
	public  String login(String username,String password) {
		User user=userRepository.findByUsername(username);
		if(user == null || password == null || !passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("Invalid username or password");
		}
		return jwtService.generateToken(user.getUsername());
	}
}
