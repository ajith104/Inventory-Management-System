package com.inventorymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.User;
import com.inventorymanagement.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
		
	public User saveUser(User user) {
		return userRepository.save(user);
	}
		
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
		
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
		
	public void deleteUser(Long id, String currentUsername, String currentRole) {

	    User currentUser = userRepository.findByUsername(currentUsername);
	    User targetUser = userRepository.findById(id).orElse(null);

	    if (currentUser == null || targetUser == null) {
	        throw new IllegalArgumentException("User not found");
	    }

	    if ("EMPLOYEE".equals(currentRole)) {
	        throw new AccessDeniedException("Employees cannot delete users");
	    }

	    if ("OWNER".equals(targetUser.getRole())) {
	        throw new AccessDeniedException("The Owner account cannot be deleted");
	    }

	    if ("MANAGER".equals(currentRole)
	            && !"EMPLOYEE".equals(targetUser.getRole())) {
	        throw new AccessDeniedException("Managers can delete Employees only");
	    }

	    if (!"OWNER".equals(currentRole) && !"MANAGER".equals(currentRole)) {
	        throw new AccessDeniedException("You are not allowed to delete users");
	    }

	    userRepository.delete(targetUser);
	}
	
	public void updateUserRole(
	        Long id,
	        String newRole,
	        String currentUsername,
	        String currentRole) {

	    User currentUser = userRepository.findByUsername(currentUsername);
	    User targetUser = userRepository.findById(id).orElse(null);

	    if (currentUser == null || targetUser == null) {
	        throw new IllegalArgumentException("User not found");
	    }

	    if (!"OWNER".equals(currentRole)
	            && !"MANAGER".equals(currentRole)) {
	        throw new AccessDeniedException(
	                "You are not allowed to edit users");
	    }

	    if ("OWNER".equals(targetUser.getRole())) {
	        throw new AccessDeniedException(
	                "The Owner account cannot be edited");
	    }

	    if ("MANAGER".equals(currentRole)
	            && !"EMPLOYEE".equals(targetUser.getRole())) {
	        throw new AccessDeniedException(
	                "Managers can edit Employees only");
	    }

	    if (!"EMPLOYEE".equals(newRole)
	            && !"MANAGER".equals(newRole)) {
	        throw new IllegalArgumentException("Invalid role");
	    }

	    if ("MANAGER".equals(currentRole)
	            && !"EMPLOYEE".equals(newRole)) {
	        throw new AccessDeniedException(
	                "Managers cannot assign the Manager role");
	    }

	    targetUser.setRole(newRole);
	    userRepository.save(targetUser);
	}

}
