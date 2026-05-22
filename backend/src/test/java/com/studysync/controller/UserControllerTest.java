package com.studysync.controller;

import com.studysync.domain.User;
import com.studysync.dto.UserDTO;
import com.studysync.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Integration tests for UserController REST endpoints.
 * Tests HTTP response codes, status, and data mapping.
 */
@DisplayName("UserController Integration Tests")
public class UserControllerTest {
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
    }

    @Test
    @DisplayName("POST /api/users - Should return 201 Created")
    void testRegisterUser_Created() {
        // Arrange
        UserDTO input = new UserDTO("John Doe", "john@example.com", 2);
        User mockUser = new User("John Doe", "john@example.com", "hash", 2);
        when(userService.registerUser(anyString(), anyString(), anyString(), anyInt()))
            .thenReturn(mockUser);

        // Act
        ResponseEntity<UserDTO> response = userController.registerUser(input);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertNotNull(response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("John Doe", response.getBody().getName());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).registerUser(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    @DisplayName("POST /api/users - Should return 400 Bad Request on invalid input")
    void testRegisterUser_BadRequest() {
        // Arrange
        UserDTO input = new UserDTO("Jane", "jane@example.com", 1);
        when(userService.registerUser(anyString(), anyString(), anyString(), anyInt()))
            .thenThrow(new IllegalArgumentException("Invalid input"));

        // Act
        ResponseEntity<UserDTO> response = userController.registerUser(input);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
    }

    @Test
    @DisplayName("GET /api/users - Should return 200 OK with user list")
    void testGetAllUsers_Success() {
        // Arrange
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("User1", "user1@example.com", "hash", 1));
        mockUsers.add(new User("User2", "user2@example.com", "hash", 2));
        when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertNotNull(response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(2, response.getBody().size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).getAllUsers();
    }

    @Test
    @DisplayName("GET /api/users/active - Should return 200 OK with active users only")
    void testGetAllActiveUsers_Success() {
        // Arrange
        List<User> activeUsers = new ArrayList<>();
        activeUsers.add(new User("Active", "active@example.com", "hash", 1));
        when(userService.getAllActiveUsers()).thenReturn(activeUsers);

        // Act
        ResponseEntity<List<UserDTO>> response = userController.getAllActiveUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(1, response.getBody().size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).getAllActiveUsers();
    }

    @Test
    @DisplayName("GET /api/users/{userId} - Should return 200 OK with user details")
    void testGetUserById_Success() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User("John", "john@example.com", "hash", 2);
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertNotNull(response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("John", response.getBody().getName());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).getUserById(userId);
    }

    @Test
    @DisplayName("GET /api/users/{userId} - Should return 404 Not Found")
    void testGetUserById_NotFound() {
        // Arrange
        Long userId = 999L;
        when(userService.getUserById(userId))
            .thenThrow(new IllegalArgumentException("User not found"));

        // Act
        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
    }

    @Test
    @DisplayName("GET /api/users/email/{email} - Should return 200 OK")
    void testGetUserByEmail_Success() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User("Test", email, "hash", 1);
        when(userService.getUserByEmail(email)).thenReturn(mockUser);

        // Act
        ResponseEntity<UserDTO> response = userController.getUserByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertNotNull(response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(email, response.getBody().getEmail());
        System.out.println("Assertion passed: expected result matches actual result.");
    }

    @Test
    @DisplayName("PUT /api/users/{userId} - Should return 200 OK with updated user")
    void testUpdateUser_Success() {
        // Arrange
        Long userId = 1L;
        UserDTO updateDTO = new UserDTO("Updated Name", "updated@example.com", 3);
        User mockUser = new User("Updated Name", "updated@example.com", "hash", 3);
        when(userService.updateUser(userId, updateDTO.getName(), updateDTO.getEmail()))
            .thenReturn(mockUser);

        // Act
        ResponseEntity<UserDTO> response = userController.updateUser(userId, updateDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertNotNull(response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("Updated Name", response.getBody().getName());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).updateUser(userId, updateDTO.getName(), updateDTO.getEmail());
    }

    @Test
    @DisplayName("DELETE /api/users/{userId} - Should return 204 No Content")
    void testDeactivateUser_Success() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User("John", "john@example.com", "hash", 2);
        when(userService.deactivateUser(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<Void> response = userController.deactivateUser(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).deactivateUser(userId);
    }

    @Test
    @DisplayName("GET /api/users/count/total - Should return total user count")
    void testGetUserCount_Success() {
        // Arrange
        when(userService.getActiveUserCount()).thenReturn(5L);

        // Act
        ResponseEntity<Long> response = userController.getUserCount();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(5L, response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userService).getActiveUserCount();
    }
}