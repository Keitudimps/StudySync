package com.studysync.repository;

import com.studysync.domain.StudySession;
import com.studysync.repository.inmemory.InMemoryStudySessionRepository;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryStudySessionRepository — CRUD and domain queries")
class InMemoryStudySessionRepositoryTest {

    private InMemoryStudySessionRepository repo;
    private final LocalDateTime FUTURE = LocalDateTime.now().plusDays(2);
    private final LocalDateTime PAST   = LocalDateTime.now().minusDays(1);

    @BeforeEach
    void setUp() { repo = new InMemoryStudySessionRepository(); }

    @Test
    @DisplayName("save() assigns ID and session is retrievable")
    void testSaveAndFind() {
        System.out.println("\n--- TEST: save() + findById() ---");
        StudySession session = new StudySession("Finals Prep", FUTURE, 2, "Library", null, 10L, 1L);
        repo.save(session);
        System.out.println("Save operation completed.");

        assertNotNull(session.getSessionId());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertTrue(repo.findById(session.getSessionId()).isPresent());
        System.out.println("Assertion passed: expected result matches actual result.");
        System.out.println("  Session ID: " + session.getSessionId());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findByGroupId() returns only sessions for that group")
    void testFindByGroupId() {
        System.out.println("\n--- TEST: findByGroupId() ---");
        repo.save(new StudySession("Session A", FUTURE, 1, "Room 1", null, 10L, 1L));
        System.out.println("Save operation completed.");
        repo.save(new StudySession("Session B", FUTURE, 2, "Room 2", null, 10L, 1L));
        System.out.println("Save operation completed.");
        repo.save(new StudySession("Session C", FUTURE, 1, "Room 3", null, 20L, 2L));
        System.out.println("Save operation completed.");

        List<StudySession> group10Sessions = repo.findByGroupId(10L);
        assertEquals(2, group10Sessions.size(), "Group 10 has 2 sessions");
        System.out.println("Assertion passed: expected result matches actual result.");

        List<StudySession> group20Sessions = repo.findByGroupId(20L);
        assertEquals(1, group20Sessions.size(), "Group 20 has 1 session");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Group 10 sessions: " + group10Sessions.size());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("findUpcomingSessions() returns only future sessions sorted ascending")
    void testFindUpcomingSessions() {
        System.out.println("\n--- TEST: findUpcomingSessions() ---");
        repo.save(new StudySession("Past",    PAST,   1, "Room 1", null, 10L, 1L));
        System.out.println("Save operation completed.");
        repo.save(new StudySession("Future1", FUTURE, 2, "Room 2", null, 10L, 1L));
        System.out.println("Save operation completed.");
        repo.save(new StudySession("Future2", FUTURE.plusDays(1), 1, "Room 3", null, 10L, 1L));
        System.out.println("Save operation completed.");

        List<StudySession> upcoming = repo.findUpcomingSessions();
        assertEquals(2, upcoming.size(), "Only 2 future sessions expected");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertTrue(upcoming.get(0).getScheduledAt().isBefore(upcoming.get(1).getScheduledAt()),
                "Results must be sorted by scheduledAt ascending");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  Upcoming sessions: " + upcoming.size() + " (expected 2)");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("deleteById() removes session from storage")
    void testDeleteById() {
        System.out.println("\n--- TEST: deleteById() ---");
        StudySession session = new StudySession("To Delete", FUTURE, 1, "Room", null, 10L, 1L);
        repo.save(session);
        System.out.println("Save operation completed.");
        Long id = session.getSessionId();

        repo.deleteById(id);
        System.out.println("Delete operation completed.");
        assertFalse(repo.existsById(id));
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Session deleted; exists=" + repo.existsById(id));
        System.out.println("  PASS");
    }
}