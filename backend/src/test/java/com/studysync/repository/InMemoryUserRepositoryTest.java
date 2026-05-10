package com.studysync.repository;

import com.studysync.domain.Role;
import com.studysync.domain.User;
import com.studysync.repository.inmemory.InMemoryUserRepository;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryUserRepository — CRUD and domain queries")
class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryUserRepository();  // fresh empty repo for every test
    }

    // ── CRUD ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("save() assigns an auto-generated ID when entity has none")
    void testSaveAssignsId() {
        System.out.println("\n--- TEST: save() assigns ID ---");
        User user = new User("Alice", "alice@uni.ac.za", "password123", 2);
        assertNull(user.getUserId(), "ID must be null before save");

        repo.save(user);

        assertNotNull(user.getUserId(), "ID must be set after save");
        System.out.println("  Assigned ID: " + user.getUserId());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("save() then findById() returns the same user")
    void testSaveAndFindById() {
        System.out.println("\n--- TEST: save() + findById() ---");
        User user = new User("Bob", "bob@uni.ac.za", "password123", 1);
        repo.save(user);
        Long id = user.getUserId();

        Optional<User> found = repo.findById(id);

        assertTrue(found.isPresent(), "findById must return the saved user");
        assertEquals("Bob", found.get().getName());
        assertEquals("bob@uni.ac.za", found.get().getEmail());
        System.out.println("  Found user: " + found.get().getName() + " (ID=" + id + ")");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findById() returns empty for non-existent ID")
    void testFindByIdNotFound() {
        System.out.println("\n--- TEST: findById() returns empty for missing ID ---");
        Optional<User> result = repo.findById(999L);
        assertFalse(result.isPresent(), "findById must return empty for unknown ID");
        System.out.println("  findById(999) returned empty: confirmed");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findAll() returns all saved users")
    void testFindAll() {
        System.out.println("\n--- TEST: findAll() ---");
        repo.save(new User("Alice", "alice@uni.ac.za", "password123", 2));
        repo.save(new User("Bob",   "bob@uni.ac.za",   "password123", 1));
        repo.save(new User("Carol", "carol@uni.ac.za", "password123", 3));

        List<User> all = repo.findAll();
        assertEquals(3, all.size(), "findAll must return all 3 saved users");
        System.out.println("  findAll returned " + all.size() + " users");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("deleteById() removes the user so findById returns empty")
    void testDeleteById() {
        System.out.println("\n--- TEST: deleteById() ---");
        User user = new User("Dave", "dave@uni.ac.za", "password123", 2);
        repo.save(user);
        Long id = user.getUserId();
        assertTrue(repo.existsById(id), "User must exist before delete");

        repo.deleteById(id);

        assertFalse(repo.existsById(id), "User must not exist after delete");
        assertFalse(repo.findById(id).isPresent(), "findById must return empty after delete");
        System.out.println("  User deleted; existsById=" + repo.existsById(id));
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("count() reflects the number of saved users")
    void testCount() {
        System.out.println("\n--- TEST: count() ---");
        assertEquals(0, repo.count(), "Empty repo must have count 0");
        repo.save(new User("A", "a@uni.ac.za", "password123", 1));
        repo.save(new User("B", "b@uni.ac.za", "password123", 1));
        assertEquals(2, repo.count(), "After 2 saves, count must be 2");
        System.out.println("  count after 2 saves: " + repo.count());
        System.out.println("  PASS");
    }

    // ── Domain queries ────────────────────────────────────────────────────────

    @Test
    @DisplayName("findByEmail() returns the correct user")
    void testFindByEmail() {
        System.out.println("\n--- TEST: findByEmail() ---");
        User user = new User("Eve", "eve@uni.ac.za", "password123", 2);
        repo.save(user);

        Optional<User> found = repo.findByEmail("eve@uni.ac.za");
        assertTrue(found.isPresent(), "findByEmail must find the user");
        assertEquals("Eve", found.get().getName());

        Optional<User> notFound = repo.findByEmail("nobody@uni.ac.za");
        assertFalse(notFound.isPresent(), "findByEmail must return empty for unknown email");

        System.out.println("  Found: " + found.get().getName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("existsByEmail() returns true only for registered emails")
    void testExistsByEmail() {
        System.out.println("\n--- TEST: existsByEmail() ---");
        repo.save(new User("Frank", "frank@uni.ac.za", "password123", 3));

        assertTrue(repo.existsByEmail("frank@uni.ac.za"),
            "existsByEmail must return true for a registered email");
        assertFalse(repo.existsByEmail("ghost@uni.ac.za"),
            "existsByEmail must return false for an unregistered email");

        System.out.println("  frank@uni.ac.za exists: " + repo.existsByEmail("frank@uni.ac.za"));
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findAllActive() excludes deactivated users")
    void testFindAllActive() {
        System.out.println("\n--- TEST: findAllActive() ---");
        User active = new User("Grace", "grace@uni.ac.za", "password123", 1);
        User inactive = new User("Harry", "harry@uni.ac.za", "password123", 2);
        inactive.deactivate();
        repo.save(active);
        repo.save(inactive);

        List<User> activeUsers = repo.findAllActive();
        assertEquals(1, activeUsers.size(),
            "findAllActive must return only the 1 active user");
        assertEquals("Grace", activeUsers.get(0).getName());

        System.out.println("  Active users: " + activeUsers.size() + " (expected 1)");
        System.out.println("  PASS");
    }
}
