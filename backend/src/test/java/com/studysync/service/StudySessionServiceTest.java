package com.studysync.service;

import com.studysync.domain.StudySession;
import com.studysync.repository.StudySessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudySessionService business logic.
 * Tests session scheduling, cancellation, and validation.
 */
@DisplayName("StudySessionService Tests")
public class StudySessionServiceTest {
    private StudySessionService sessionService;

    @Mock
    private StudySessionRepository sessionRepository;

    @Mock
    private StudyGroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionService = new StudySessionService(sessionRepository, groupService);
    }

    @Test
    @DisplayName("Should schedule a session with valid input")
    void testScheduleSession_Success() {
        // Arrange
        String title = "Math101 Study Session";
        LocalDateTime futureTime = LocalDateTime.now().plusDays(1);
        Integer duration = 2;
        String location = "Library Room A";
        Long groupId = 1L;
        Long createdBy = 1L;

        StudySession mockSession = StudySession.schedule(title, futureTime, duration, location, groupId, createdBy);
        when(groupService.getGroupById(groupId)).thenReturn(null); // Group exists (null is OK for this test)
        doNothing().when(sessionRepository).save(any(StudySession.class));
        System.out.println("Save operation completed.");

        // Act
        StudySession result = sessionService.scheduleSession(title, futureTime, duration, location, groupId, createdBy);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(title, result.getTitle());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).save(any(StudySession.class));
        System.out.println("Save operation completed.");
    }

    @Test
    @DisplayName("Should throw exception for session in past")
    void testScheduleSession_PastTime() {
        // Arrange
        LocalDateTime pastTime = LocalDateTime.now().minusDays(1);
        Long groupId = 1L;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("Executing action that should throw an exception...");
            sessionService.scheduleSession("Past Session", pastTime, 2, "Room", groupId, 1L);
        });
        System.out.println("Expected exception was thrown. Test Passed.");
        verify(sessionRepository, never()).save(any());
        System.out.println("Save operation completed.");
    }

    @Test
    @DisplayName("Should retrieve session by ID successfully")
    void testGetSessionById_Success() {
        // Arrange
        Long sessionId = 1L;
        StudySession mockSession = StudySession.schedule("Test Session",
            LocalDateTime.now().plusDays(1), 1, "Room", 1L, 1L);
        when(sessionRepository.findById(sessionId)).thenReturn(java.util.Optional.of(mockSession));

        // Act
        StudySession result = sessionService.getSessionById(sessionId);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("Test Session", result.getTitle());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).findById(sessionId);
    }

    @Test
    @DisplayName("Should throw exception when session not found by ID")
    void testGetSessionById_NotFound() {
        // Arrange
        Long sessionId = 999L;
        when(sessionRepository.findById(sessionId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("Executing action that should throw an exception...");
            sessionService.getSessionById(sessionId);
        });
        System.out.println("Expected exception was thrown. Test Passed.");
    }

    @Test
    @DisplayName("Should get all sessions")
    void testGetAllSessions_Success() {
        // Arrange
        List<StudySession> mockSessions = new ArrayList<>();
        mockSessions.add(StudySession.schedule("Session1", LocalDateTime.now().plusDays(1), 1, "Room1", 1L, 1L));
        mockSessions.add(StudySession.schedule("Session2", LocalDateTime.now().plusDays(2), 2, "Room2", 2L, 2L));
        when(sessionRepository.findAll()).thenReturn(mockSessions);

        // Act
        List<StudySession> result = sessionService.getAllSessions();

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(2, result.size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).findAll();
    }

    @Test
    @DisplayName("Should get upcoming sessions only")
    void testGetUpcomingSessions_Success() {
        // Arrange
        List<StudySession> upcomingSessions = new ArrayList<>();
        upcomingSessions.add(StudySession.schedule("Future Session", LocalDateTime.now().plusDays(1), 1, "Room", 1L, 1L));
        when(sessionRepository.findUpcomingSessions()).thenReturn(upcomingSessions);

        // Act
        List<StudySession> result = sessionService.getUpcomingSessions();

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(1, result.size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).findUpcomingSessions();
    }

    @Test
    @DisplayName("Should get sessions by group ID")
    void testGetSessionsByGroup_Success() {
        // Arrange
        Long groupId = 1L;
        List<StudySession> mockSessions = new ArrayList<>();
        mockSessions.add(StudySession.schedule("Group Session", LocalDateTime.now().plusDays(1), 1, "Room", groupId, 1L));
        when(sessionRepository.findByGroupId(groupId)).thenReturn(mockSessions);

        // Act
        List<StudySession> result = sessionService.getSessionsByGroup(groupId);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(1, result.size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).findByGroupId(groupId);
    }

    @Test
    @DisplayName("Should reschedule session by creator only")
    void testRescheduleSession_CreatorOnly() {
        // Arrange
        Long sessionId = 1L;
        Long creatorId = 1L;
        LocalDateTime newTime = LocalDateTime.now().plusDays(2);
        StudySession mockSession = StudySession.schedule("Session", LocalDateTime.now().plusDays(1), 1, "Room", 1L, creatorId);
        when(sessionRepository.findById(sessionId)).thenReturn(java.util.Optional.of(mockSession));
        doNothing().when(sessionRepository).save(any(StudySession.class));
        System.out.println("Save operation completed.");

        // Act
        StudySession result = sessionService.rescheduleSession(sessionId, newTime, creatorId);

        // Assert
        assertNotNull(result);
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).save(any(StudySession.class));
        System.out.println("Save operation completed.");
    }

    @Test
    @DisplayName("Should throw exception when non-creator tries to reschedule")
    void testRescheduleSession_NonCreator() {
        // Arrange
        Long sessionId = 1L;
        Long creatorId = 1L;
        Long otherUserId = 2L;
        LocalDateTime newTime = LocalDateTime.now().plusDays(2);
        StudySession mockSession = StudySession.schedule("Session", LocalDateTime.now().plusDays(1), 1, "Room", 1L, creatorId);
        when(sessionRepository.findById(sessionId)).thenReturn(java.util.Optional.of(mockSession));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            System.out.println("Executing action that should throw an exception...");
            sessionService.rescheduleSession(sessionId, newTime, otherUserId);
        });
        System.out.println("Expected exception was thrown. Test Passed.");
        verify(sessionRepository, never()).save(any());
        System.out.println("Save operation completed.");
    }

    @Test
    @DisplayName("Should cancel session by creator only")
    void testCancelSession_CreatorOnly() {
        // Arrange
        Long sessionId = 1L;
        Long creatorId = 1L;
        StudySession mockSession = StudySession.schedule("Session", LocalDateTime.now().plusDays(1), 1, "Room", 1L, creatorId);
        when(sessionRepository.findById(sessionId)).thenReturn(java.util.Optional.of(mockSession));
        doNothing().when(sessionRepository).deleteById(sessionId);
        System.out.println("Delete operation completed.");

        // Act
        sessionService.cancelSession(sessionId, creatorId);

        // Assert
        verify(sessionRepository).deleteById(sessionId);
        System.out.println("Delete operation completed.");
    }

    @Test
    @DisplayName("Should get session count")
    void testGetSessionCount_Success() {
        // Arrange
        when(sessionRepository.count()).thenReturn(5L);

        // Act
        long result = sessionService.getSessionCount();

        // Assert
        assertEquals(5L, result);
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(sessionRepository).count();
    }
}