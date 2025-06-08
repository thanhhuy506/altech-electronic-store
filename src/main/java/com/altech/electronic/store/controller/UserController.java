package com.altech.electronic.store.controller;

import static com.altech.electronic.store.constant.PathConstant.USER;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(ProductDiscountController.class);

	private final UserService userService;

	@Operation(summary = "Register User", description = "Creates a new user in the system", tags = "user", responses = {
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request") })
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

	@Operation(summary = "Login User", description = "Logs in an existing user", tags = "user", responses = {
			@ApiResponse(responseCode = "200", description = "User logged in successfully"),
			@ApiResponse(responseCode = "401", description = "Invalid credentials") })
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

	@Operation(summary = "Get User Profile", description = "Retrieves the profile of the currently logged in user", tags = "user", responses = {
			@ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "User not found") })
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

	@Operation(summary = "Get User by ID", description = "Retrieves user information by ID", tags = "user", responses = {
			@ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
			@ApiResponse(responseCode = "404", description = "User not found") })
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

	@Operation(summary = "Update User Profile", description = "Updates the profile of the currently logged in user", tags = "user", responses = {
			@ApiResponse(responseCode = "200", description = "User profile updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request") })
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

	@Operation(summary = "Delete User Account", description = "Deletes the account of the currently logged in user", tags = "user", responses = {
			@ApiResponse(responseCode = "200", description = "User account deleted successfully"),
			@ApiResponse(responseCode = "404", description = "User not found") })
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