package com.studysync.repository.inmemory;

import com.studysync.domain.Role;
import com.studysync.domain.User;
import com.studysync.repository.UserRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of UserRepository using a HashMap.
 * IDs are auto-generated using an AtomicLong counter.
 * Thread-safe for single-threaded test use.
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public void save(User user) {
        if (user.getUserId() == null) {
            user.setUserId(idCounter.getAndIncrement());
        }
        storage.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return storage.values().stream()
            .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public List<User> findByRole(Role role) {
        return storage.values().stream()
            .filter(u -> u.getRole() == role)
            .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllActive() {
        return storage.values().stream()
            .filter(u -> Boolean.TRUE.equals(u.getIsActive()))
            .collect(Collectors.toList());
    }
}
