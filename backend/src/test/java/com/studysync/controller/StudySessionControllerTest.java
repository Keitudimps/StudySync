package com.studysync.controller;

import com.studysync.domain.StudySession;
import com.studysync.dto.StudySessionDTO;
import com.studysync.service.StudySessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("StudySessionController Integration Tests")
public class StudySessionControllerTest {
    private StudySessionController studySessionController;

    @Mock
    private StudySessionService studySessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        studySessionController = new StudySessionController(studySessionService);
    }

    @Test
    @DisplayName("POST /api/sessions - Should return 201 Created")
    void testScheduleSession_Created() {
        LocalDateTime futureTime = LocalDateTime.now().plusDays(1);
        StudySessionDTO input = new StudySessionDTO("Normalisation", futureTime, 2, "Library", 1L, 1L);
        StudySession session = new StudySession("Normalisation", futureTime, 2, "Library", null, 1L, 1L);
        session.setSessionId(1L);

        when(studySessionService.scheduleSession("Normalisation", futureTime, 2, "Library", 1L, 1L)).thenReturn(session);

        ResponseEntity<StudySessionDTO> response = studySessionController.scheduleSession(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertNotNull(response.getBody());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("Normalisation", response.getBody().getTitle());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(studySessionService).scheduleSession("Normalisation", futureTime, 2, "Library", 1L, 1L);
    }

    @Test
    @DisplayName("GET /api/sessions - Should return 200 OK")
    void testGetAllSessions_Success() {
        LocalDateTime futureTime = LocalDateTime.now().plusDays(2);
        StudySession session = new StudySession("SQL Practice", futureTime, 1, "Online", null, 1L, 2L);
        when(studySessionService.getAllSessions()).thenReturn(List.of(session));

        ResponseEntity<List<StudySessionDTO>> response = studySessionController.getAllSessions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(1, response.getBody().size());
        System.out.println("Assertion passed: expected result matches actual result.");
        verify(studySessionService).getAllSessions();
    }

    @Test
    @DisplayName("GET /api/sessions/{sessionId} - Should return 404 Not Found")
    void testGetSessionById_NotFound() {
        when(studySessionService.getSessionById(99L)).thenThrow(new IllegalArgumentException("Session not found"));

        ResponseEntity<StudySessionDTO> response = studySessionController.getSessionById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
    }

    @Test
    @DisplayName("DELETE /api/sessions/{sessionId} - Should return 403 for non-creator")
    void testCancelSession_Forbidden() {
        doThrow(new IllegalStateException("Only creator can cancel")).when(studySessionService).cancelSession(1L, 9L);

        ResponseEntity<Void> response = studySessionController.cancelSession(1L, 9L);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        System.out.println("Assertion passed: expected result matches actual result.");
    }
}