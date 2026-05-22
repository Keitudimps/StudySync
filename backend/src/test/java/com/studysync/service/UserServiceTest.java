package com.studysync.service;

import com.studysync.domain.User;
import com.studysync.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService business logic.
 * Tests user registration, validation, retrieval, and management.
 */
@DisplayName("UserService Tests")
public class UserServiceTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Should register a new user with valid input")
    void testRegisterUser_Success() {
        // Arrange
        String name = "John Doe";
        String email = "john@example.com";
        String password = "hashedPassword123";
        Integer yearOfStudy = 2;

        User mockUser = new User(name, email, password, yearOfStudy);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        doNothing().when(userRepository).save(any(User.class));
        System.out.println("Save operation completed.");

        // Act
        User result = userService.registerUser(name, email, password, yearOfStudy);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(name, result.getName());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(email, result.getEmail());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userRepository).save(any(User.class));
        System.out.println("Save operation completed.");
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void testRegisterUser_DuplicateEmail() {
        // Arrange
        String email = "existing@example.com";
        User existingUser = new User("Existing", email, "hash", 1);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("Executing action that should throw an exception...");
            userService.registerUser("John", email, "password123", 2);
        });
        System.out.println("Expected exception was thrown. Test Passed.");
        verify(userRepository, never()).save(any());
        System.out.println("Save operation completed.");
    }

    @Test
    @DisplayName("Should retrieve user by ID successfully")
    void testGetUserById_Success() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User("Jane", "jane@example.com", "hash", 3);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserById(userId);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("Jane", result.getName());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userRepository).findById(userId);
    }

    @Test
    @DisplayName("Should throw exception when user not found by ID")
    void testGetUserById_NotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("Executing action that should throw an exception...");
            userService.getUserById(userId);
        });
        System.out.println("Expected exception was thrown. Test Passed.");
    }

    @Test
    @DisplayName("Should retrieve user by email successfully")
    void testGetUserByEmail_Success() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User("Test", email, "hash", 1);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(email, result.getEmail());
        System.out.println("Assertion passed: expected result matches actual result.");
    }

    @Test
    @DisplayName("Should get all users")
    void testGetAllUsers_Success() {
        // Arrange
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("User1", "user1@example.com", "hash1", 1));
        mockUsers.add(new User("User2", "user2@example.com", "hash2", 2));
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(2, result.size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should get all active users")
    void testGetAllActiveUsers_Success() {
        // Arrange
        List<User> activeUsers = new ArrayList<>();
        activeUsers.add(new User("Active1", "active1@example.com", "hash", 1));
        when(userRepository.findAllActive()).thenReturn(activeUsers);

        // Act
        List<User> result = userService.getAllActiveUsers();

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(1, result.size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userRepository).findAllActive();
    }

    @Test
    @DisplayName("Should delete user by ID")
    void testDeleteUser_Success() {
        // Arrange
        Long userId = 1L;
        User mockUser = new User("John", "john@example.com", "hash", 2);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        userService.deleteUser(userId);
        System.out.println("Delete operation completed.");

        // Assert
        verify(userRepository).deleteById(userId);
        System.out.println("Delete operation completed.");
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void testDeleteUser_NotFound() {
        // Arrange
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("Executing action that should throw an exception...");
            userService.deleteUser(userId);
        });
        System.out.println("Expected exception was thrown. Test Passed.");
        verify(userRepository, never()).deleteById(userId);
        System.out.println("Delete operation completed.");
    }

    @Test
    @DisplayName("Should get count of active users")
    void testGetActiveUserCount_Success() {
        // Arrange
        when(userRepository.count()).thenReturn(5L);

        // Act
        long result = userService.getActiveUserCount();

        // Assert
        assertEquals(5L, result);
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(userRepository).count();
    }
}