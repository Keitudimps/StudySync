package com.studysync.repository;

import com.studysync.domain.Membership;
import com.studysync.domain.MembershipStatus;
import com.studysync.repository.inmemory.InMemoryMembershipRepository;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryMembershipRepository — CRUD and domain queries")
class InMemoryMembershipRepositoryTest {

    private InMemoryMembershipRepository repo;

    @BeforeEach
    void setUp() { repo = new InMemoryMembershipRepository(); }

    @Test
    @DisplayName("save() assigns ID and membership is retrievable")
    void testSaveAndFind() {
        System.out.println("\n--- TEST: save() + findById() ---");
        Membership m = new Membership(1L, 10L, MembershipStatus.ACTIVE);
        repo.save(m);

        assertNotNull(m.getMembershipId());
        assertTrue(repo.findById(m.getMembershipId()).isPresent());
        System.out.println("  Membership ID: " + m.getMembershipId());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findByUserId() returns all memberships for a user")
    void testFindByUserId() {
        System.out.println("\n--- TEST: findByUserId() ---");
        repo.save(new Membership(1L, 10L, MembershipStatus.ACTIVE));
        repo.save(new Membership(1L, 20L, MembershipStatus.PENDING));
        repo.save(new Membership(2L, 10L, MembershipStatus.ACTIVE));

        List<Membership> user1 = repo.findByUserId(1L);
        assertEquals(2, user1.size(), "User 1 has 2 memberships");
        System.out.println("  User 1 memberships: " + user1.size());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findByUserIdAndGroupId() finds the exact membership")
    void testFindByUserIdAndGroupId() {
        System.out.println("\n--- TEST: findByUserIdAndGroupId() ---");
        repo.save(new Membership(1L, 10L, MembershipStatus.ACTIVE));

        Optional<Membership> found = repo.findByUserIdAndGroupId(1L, 10L);
        assertTrue(found.isPresent(), "Membership (user=1, group=10) must be found");
        assertEquals(MembershipStatus.ACTIVE, found.get().getStatus());

        Optional<Membership> missing = repo.findByUserIdAndGroupId(1L, 99L);
        assertFalse(missing.isPresent(), "Non-existent combination must return empty");

        System.out.println("  Exact match found: " + found.isPresent());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("countActiveByUserId() enforces the 5-group business rule")
    void testCountActiveByUserId() {
        System.out.println("\n--- TEST: countActiveByUserId() ---");
        repo.save(new Membership(1L, 10L, MembershipStatus.ACTIVE));
        repo.save(new Membership(1L, 20L, MembershipStatus.ACTIVE));
        repo.save(new Membership(1L, 30L, MembershipStatus.PENDING));  // not active

        long activeCount = repo.countActiveByUserId(1L);
        assertEquals(2, activeCount,
            "Only ACTIVE memberships count — PENDING should not be included");
        System.out.println("  Active count for user 1: " + activeCount + " (expected 2)");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findByStatus() filters correctly by PENDING vs ACTIVE")
    void testFindByStatus() {
        System.out.println("\n--- TEST: findByStatus() ---");
        repo.save(new Membership(1L, 10L, MembershipStatus.ACTIVE));
        repo.save(new Membership(2L, 10L, MembershipStatus.PENDING));
        repo.save(new Membership(3L, 10L, MembershipStatus.ACTIVE));

        List<Membership> active  = repo.findByStatus(MembershipStatus.ACTIVE);
        List<Membership> pending = repo.findByStatus(MembershipStatus.PENDING);

        assertEquals(2, active.size(),  "2 ACTIVE memberships expected");
        assertEquals(1, pending.size(), "1 PENDING membership expected");
        System.out.println("  ACTIVE: " + active.size() + "  PENDING: " + pending.size());
        System.out.println("  PASS");
    }
}
