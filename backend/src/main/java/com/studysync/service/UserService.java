package com.studysync.service;

import com.studysync.domain.User;
import com.studysync.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User-related business operations.
 * Encapsulates business logic and delegates persistence to UserRepository.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user with validation.
     *
     * @param name           User's full name
     * @param email          User's email address
     * @param passwordHash   Hashed password
     * @param yearOfStudy    Year of study (1-5)
     * @return Created User
     * @throws IllegalArgumentException if validation fails
     */
    public User registerUser(String name, String email, String passwordHash, Integer yearOfStudy) {
        // Delegate validation to domain model
        User user = User.register(name, email, passwordHash, yearOfStudy);

        // Check for duplicate email
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Persist user
        userRepository.save(user);
        return user;
    }

    /**
     * Retrieve a user by ID.
     *
     * @param userId User ID
     * @return User if found
     * @throws IllegalArgumentException if user not found
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    /**
     * Retrieve a user by email.
     *
     * @param email User email
     * @return User if found
     * @throws IllegalArgumentException if user not found
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    /**
     * Get all active users.
     *
     * @return List of active users
     */
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }

    /**
     * Get all users.
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Update user details.
     *
     * @param userId User ID
     * @param name   Updated name
     * @param email  Updated email
     * @return Updated User
     */
    public User updateUser(Long userId, String name, String email) {
        User user = getUserById(userId);

        if (email != null && !email.equalsIgnoreCase(user.getEmail())
            && userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        user.updateDetails(name, email);
        userRepository.save(user);
        return user;
    }

    /**
     * Deactivate a user account.
     *
     * @param userId User ID
     * @return Deactivated User
     */
    public User deactivateUser(Long userId) {
        User user = getUserById(userId);
        user.deactivate();
        userRepository.save(user);
        return user;
    }

    /**
     * Delete a user.
     *
     * @param userId User ID
     */
    public void deleteUser(Long userId) {
        User user = getUserById(userId); // Validate existence
        userRepository.deleteById(userId);
    }

    /**
     * Get count of active users.
     *
     * @return Number of active users
     */
    public long getActiveUserCount() {
        return userRepository.count();
    }
}
