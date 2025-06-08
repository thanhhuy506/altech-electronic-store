package com.altech.electronic.store.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altech.electronic.store.exception.EmailAlreadyExistsException;
import com.altech.electronic.store.service.UserService;

import dto.UserDTO;
import dto.UserLoginDTO;
import dto.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(ProductDiscountController.class);

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegistrationDTO registrationDTO)
			throws EmailAlreadyExistsException {
		logger.info("Register new user {}", registrationDTO);
		try {
			return new ResponseEntity<>(userService.register(registrationDTO), HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Failed to register new user {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDTO loginDTO) {
		logger.info("Login {}", loginDTO);
		try {
			return ResponseEntity.ok(userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword()));
		} catch (Exception e) {
			logger.error("Failed to login {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser() {
		logger.info("Get current User infor ");
		try {
			return ResponseEntity.ok(userService.getCurrentUser());
		} catch (Exception e) {
			logger.error("Failed to get current user {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		logger.info("Get user infor by id {}", id);
		try {
			return ResponseEntity.ok(userService.getUserById(id));
		} catch (Exception e) {
			logger.error("Failed to user infor by id {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		logger.info("Update User id {}", id);
		try {
			if (!id.equals(userDTO.getId())) {
				throw new IllegalArgumentException("ID in path and body must match");
			}
			return ResponseEntity.ok(userService.updateUser(userDTO));
		} catch (Exception e) {
			logger.error("Failed to update user {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		logger.info("Delete User id {}", id);
		try {
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			logger.error("Failed to delete user {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An unexpected error occurred: " + e.getMessage());
		}

	}
}