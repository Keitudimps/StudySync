package com.studysync.controller;

import com.studysync.domain.User;
import com.studysync.dto.UserDTO;
import com.studysync.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API Controller for User operations.
 * Provides endpoints for user registration, retrieval, and management.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management endpoints")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Register a new user.
     *
     * @param userDTO User registration data
     * @return Created user with 201 status
     */
    @PostMapping
    @Operation(summary = "Register a new user", description = "Create a new user account with validation")
    @ApiResponse(responseCode = "201", description = "User created successfully",
        content = @Content(schema = @Schema(implementation = UserDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // UserDTO does not carry a password field (simplified for assignment).
            // A real system would include a password field; here we use a placeholder.
            String passwordHash = "default_password_hash";
            User user = userService.registerUser(userDTO.getName(), userDTO.getEmail(),
                passwordHash, userDTO.getYearOfStudy());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all users.
     *
     * @return List of users
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve all registered users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> dtos = users.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get all active users.
     *
     * @return List of active users
     */
    @GetMapping("/active")
    @Operation(summary = "Get all active users", description = "Retrieve all active users")
    @ApiResponse(responseCode = "200", description = "Active users retrieved successfully")
    public ResponseEntity<List<UserDTO>> getAllActiveUsers() {
        List<User> users = userService.getAllActiveUsers();
        List<UserDTO> dtos = users.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get a user by ID.
     *
     * @param userId User ID
     * @return User details
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(convertToDTO(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a user by email.
     *
     * @param email User email
     * @return User details
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieve a user by their email")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(convertToDTO(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update user details.
     *
     * @param userId  User ID
     * @param userDTO Updated user data
     * @return Updated user
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Update user details")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            User user = userService.updateUser(userId, userDTO.getName(), userDTO.getEmail());
            return ResponseEntity.ok(convertToDTO(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deactivate a user account.
     *
     * @param userId User ID
     * @return 204 No Content
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Deactivate user", description = "Deactivate a user account")
    @ApiResponse(responseCode = "204", description = "User deactivated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId) {
        try {
            userService.deactivateUser(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get user count.
     *
     * @return Count of users
     */
    @GetMapping("/count/total")
    @Operation(summary = "Get user count", description = "Get total number of active users")
    @ApiResponse(responseCode = "200", description = "Count retrieved")
    public ResponseEntity<Long> getUserCount() {
        long count = userService.getActiveUserCount();
        return ResponseEntity.ok(count);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO(user.getName(), user.getEmail(), user.getYearOfStudy());
        dto.setUserId(user.getUserId());
        dto.setIsActive(user.getIsActive());
        return dto;
    }
}
