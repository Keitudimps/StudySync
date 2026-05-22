package com.studysync.repository;

import com.studysync.factory.RepositoryFactory;
import com.studysync.repository.inmemory.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RepositoryFactory — storage type routing and error handling")
class RepositoryFactoryTest {

    @Test
    @DisplayName("getUserRepository(MEMORY) returns an InMemoryUserRepository")
    void testGetUserRepositoryMemory() {
        System.out.println("\n--- TEST: Factory returns InMemoryUserRepository for MEMORY ---");

        UserRepository repo = RepositoryFactory.getUserRepository("MEMORY");

        assertNotNull(repo, "Factory must return a non-null repository");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryUserRepository.class, repo, "MEMORY type must produce an InMemoryUserRepository — " +
                "if this fails, the switch case was changed");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  Returned type: " + repo.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("getStudyGroupRepository(MEMORY) returns an InMemoryStudyGroupRepository")
    void testGetStudyGroupRepositoryMemory() {
        System.out.println("\n--- TEST: Factory returns InMemoryStudyGroupRepository for MEMORY ---");

        StudyGroupRepository repo = RepositoryFactory.getStudyGroupRepository("MEMORY");

        assertNotNull(repo);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryStudyGroupRepository.class, repo);
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Returned type: " + repo.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("getMembershipRepository(MEMORY) returns an InMemoryMembershipRepository")
    void testGetMembershipRepositoryMemory() {
        System.out.println("\n--- TEST: Factory returns InMemoryMembershipRepository for MEMORY ---");

        MembershipRepository repo = RepositoryFactory.getMembershipRepository("MEMORY");

        assertNotNull(repo);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryMembershipRepository.class, repo);
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Returned type: " + repo.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("getStudySessionRepository(MEMORY) returns an InMemoryStudySessionRepository")
    void testGetStudySessionRepositoryMemory() {
        System.out.println("\n--- TEST: Factory returns InMemoryStudySessionRepository for MEMORY ---");

        StudySessionRepository repo = RepositoryFactory.getStudySessionRepository("MEMORY");

        assertNotNull(repo);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryStudySessionRepository.class, repo);
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Returned type: " + repo.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("getCourseRepository(MEMORY) returns an InMemoryCourseRepository")
    void testGetCourseRepositoryMemory() {
        System.out.println("\n--- TEST: Factory returns InMemoryCourseRepository for MEMORY ---");

        CourseRepository repo = RepositoryFactory.getCourseRepository("MEMORY");

        assertNotNull(repo);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryCourseRepository.class, repo);
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Returned type: " + repo.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Factory is case-insensitive — 'memory' and 'MEMORY' both work")
    void testCaseInsensitiveStorageType() {
        System.out.println("\n--- TEST: Factory is case-insensitive ---");

        UserRepository lower = RepositoryFactory.getUserRepository("memory");
        UserRepository upper = RepositoryFactory.getUserRepository("MEMORY");
        UserRepository mixed = RepositoryFactory.getUserRepository("Memory");

        assertInstanceOf(InMemoryUserRepository.class, lower, "'memory' must work");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryUserRepository.class, upper, "'MEMORY' must work");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(InMemoryUserRepository.class, mixed, "'Memory' must work");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  'memory' → " + lower.getClass().getSimpleName());
        System.out.println("  'MEMORY' → " + upper.getClass().getSimpleName());
        System.out.println("  'Memory' → " + mixed.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Unknown storage type throws IllegalArgumentException with descriptive message")
    void testUnknownStorageTypeThrowsException() {
        System.out.println("\n--- TEST: Unknown storage type throws exception ---");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> RepositoryFactory.getUserRepository("ORACLE"),
            "Unknown storage type must throw IllegalArgumentException"
        );

        System.out.println("  Exception message: " + ex.getMessage());
        assertTrue(ex.getMessage().contains("ORACLE"),     "Exception message must include the bad storage type name");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Each MEMORY call returns a NEW independent repository instance")
    void testFactoryReturnsNewInstanceEachCall() {
        System.out.println("\n--- TEST: Each factory call returns a new instance ---");

        UserRepository repo1 = RepositoryFactory.getUserRepository("MEMORY");
        UserRepository repo2 = RepositoryFactory.getUserRepository("MEMORY");

        assertNotSame(repo1, repo2,"Factory must return a new instance on each call — " +
                "if this fails, the factory accidentally became a Singleton");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  repo1 hash: " + System.identityHashCode(repo1));
        System.out.println("  repo2 hash: " + System.identityHashCode(repo2));
        System.out.println("  Independent instances confirmed");
        System.out.println("  PASS");
    }
}