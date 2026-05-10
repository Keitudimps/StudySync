package com.studysync.repository;

import com.studysync.domain.Role;
import com.studysync.domain.User;
import java.util.List;
import java.util.Optional;

/**
 * Entity-specific repository for User.
 * Extends the generic Repository with user-domain queries.
 */
public interface UserRepository extends Repository<User, Long> {

    /** Find a user by their unique email address. */
    Optional<User> findByEmail(String email);

    /** Return true if any user is registered with this email. */
    boolean existsByEmail(String email);

    /** Return all users with the given role (STUDENT or ADMIN). */
    List<User> findByRole(Role role);

    /** Return all users whose isActive flag is true. */
    List<User> findAllActive();
}
