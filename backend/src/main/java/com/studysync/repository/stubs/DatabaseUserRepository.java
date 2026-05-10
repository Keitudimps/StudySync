package com.studysync.repository.stubs;

import com.studysync.domain.Role;
import com.studysync.domain.User;
import com.studysync.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * STUB — Future SQL database implementation of UserRepository.
 *
 * When implemented, this class will use Spring Data JPA or JDBC to
 * persist User entities to a PostgreSQL database.
 *
 * Connection will be configured via DATABASE_URL environment variable.
 * All methods below throw UnsupportedOperationException until implemented.
 */
public class DatabaseUserRepository implements UserRepository {

    // Future: inject JPA EntityManager or JdbcTemplate here
    // @Autowired private EntityManager em;

    @Override public void save(User user) {
        throw new UnsupportedOperationException(
            "DatabaseUserRepository.save() not yet implemented. " +
            "Use RepositoryFactory.getUserRepository(\"MEMORY\") for now.");
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
