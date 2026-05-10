package com.studysync.repository.stubs;

import com.studysync.domain.Role;
import com.studysync.domain.User;
import com.studysync.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * STUB — Future JSON filesystem implementation of UserRepository.
 *
 * When implemented, this class will serialize User objects to a JSON file
 * using a library like Gson or Jackson.
 *
 * Example file path: ./data/users.json
 * Format: { "1": { "userId": 1, "name": "...", "email": "..." }, ... }
 */
public class FileSystemUserRepository implements UserRepository {

    private final String filePath;

    public FileSystemUserRepository() {
        this("./data/users.json");
    }

    public FileSystemUserRepository(String filePath) {
        this.filePath = filePath;
        // Future: create file/directory if it doesn't exist
    }

    @Override public void save(User user) {
        throw new UnsupportedOperationException(
            "FileSystemUserRepository not yet implemented. " +
            "Planned: serialize user to " + filePath + " using Gson.");
    }
    @Override public Optional<User> findById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public List<User> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public void deleteById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public boolean existsById(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public Optional<User> findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public boolean existsByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public List<User> findByRole(Role role) {
        throw new UnsupportedOperationException("Not implemented");
    }
    @Override public List<User> findAllActive() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
