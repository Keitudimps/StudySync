package com.studysync.controller;

import com.studysync.domain.StudySession;
import com.studysync.dto.StudySessionDTO;
import com.studysync.service.StudySessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API Controller for StudySession operations.
 * Provides endpoints for session scheduling, cancellation, and management.
 */
@RestController
@RequestMapping("/api/sessions")
@Tag(name = "Study Sessions", description = "Study session management endpoints")
public class StudySessionController {
    private final StudySessionService sessionService;

    public StudySessionController(StudySessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * Schedule a new study session.
     *
     * @param sessionDTO Session data
     * @return Created session with 201 status
     */
    @PostMapping
    @Operation(summary = "Schedule a session", description = "Create a new study session")
    @ApiResponse(responseCode = "201", description = "Session scheduled successfully",
        content = @Content(schema = @Schema(implementation = StudySessionDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<StudySessionDTO> scheduleSession(@RequestBody StudySessionDTO sessionDTO) {
        try {
            StudySession session = sessionService.scheduleSession(
                sessionDTO.getTitle(),
                sessionDTO.getScheduledAt(),
                sessionDTO.getDurationHours(),
                sessionDTO.getLocation(),
                sessionDTO.getGroupId(),
                sessionDTO.getCreatedBy()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(session));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all study sessions.
     *
     * @return List of all sessions
     */
    @GetMapping
    @Operation(summary = "Get all sessions", description = "Retrieve all study sessions")
    @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully")
    public ResponseEntity<List<StudySessionDTO>> getAllSessions() {
        List<StudySession> sessions = sessionService.getAllSessions();
        List<StudySessionDTO> dtos = sessions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get upcoming study sessions.
     *
     * @return List of upcoming sessions
     */
    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming sessions", description = "Retrieve upcoming study sessions")
    @ApiResponse(responseCode = "200", description = "Upcoming sessions retrieved successfully")
    public ResponseEntity<List<StudySessionDTO>> getUpcomingSessions() {
        List<StudySession> sessions = sessionService.getUpcomingSessions();
        List<StudySessionDTO> dtos = sessions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get a study session by ID.
     *
     * @param sessionId Session ID
     * @return Session details
     */
    @GetMapping("/{sessionId}")
    @Operation(summary = "Get session by ID", description = "Retrieve a study session by ID")
    @ApiResponse(responseCode = "200", description = "Session found")
    @ApiResponse(responseCode = "404", description = "Session not found")
    public ResponseEntity<StudySessionDTO> getSessionById(@PathVariable Long sessionId) {
        try {
            StudySession session = sessionService.getSessionById(sessionId);
            return ResponseEntity.ok(convertToDTO(session));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get sessions for a study group.
     *
     * @param groupId Study group ID
     * @return List of sessions for group
     */
    @GetMapping("/group/{groupId}")
    @Operation(summary = "Get sessions by group", description = "Retrieve sessions for a specific study group")
    @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully")
    public ResponseEntity<List<StudySessionDTO>> getSessionsByGroup(@PathVariable Long groupId) {
        try {
            List<StudySession> sessions = sessionService.getSessionsByGroup(groupId);
            List<StudySessionDTO> dtos = sessions.stream().map(this::convertToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get upcoming sessions for a group.
     *
     * @param groupId Study group ID
     * @return List of upcoming sessions for group
     */
    @GetMapping("/group/{groupId}/upcoming")
    @Operation(summary = "Get upcoming sessions by group", description = "Retrieve upcoming sessions for a study group")
    @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully")
    public ResponseEntity<List<StudySessionDTO>> getUpcomingSessionsByGroup(@PathVariable Long groupId) {
        try {
            List<StudySession> sessions = sessionService.getUpcomingSessionsByGroup(groupId);
            List<StudySessionDTO> dtos = sessions.stream().map(this::convertToDTO).collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get sessions created by a user.
     *
     * @param userId User ID
     * @return List of sessions created by user
     */
    @GetMapping("/creator/{userId}")
    @Operation(summary = "Get sessions by creator", description = "Retrieve sessions created by a user")
    @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully")
    public ResponseEntity<List<StudySessionDTO>> getSessionsByCreator(@PathVariable Long userId) {
        List<StudySession> sessions = sessionService.getSessionsByCreator(userId);
        List<StudySessionDTO> dtos = sessions.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Reschedule a study session.
     *
     * @param sessionId Session ID
     * @param newTime   New scheduled time
     * @param userId    User requesting reschedule
     * @return Updated session
     */
    @PutMapping("/{sessionId}/reschedule")
    @Operation(summary = "Reschedule session", description = "Reschedule a study session to a new time")
    @ApiResponse(responseCode = "200", description = "Session rescheduled successfully")
    @ApiResponse(responseCode = "404", description = "Session not found")
    public ResponseEntity<StudySessionDTO> rescheduleSession(
        @PathVariable Long sessionId,
        @RequestParam LocalDateTime newTime,
        @RequestParam Long userId) {
        try {
            StudySession session = sessionService.rescheduleSession(sessionId, newTime, userId);
            return ResponseEntity.ok(convertToDTO(session));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Cancel a study session.
     *
     * @param sessionId Session ID
     * @param userId    User requesting cancellation
     * @return 204 No Content
     */
    @DeleteMapping("/{sessionId}")
    @Operation(summary = "Cancel session", description = "Cancel a study session (creator only)")
    @ApiResponse(responseCode = "204", description = "Session cancelled successfully")
    @ApiResponse(responseCode = "404", description = "Session not found")
    @ApiResponse(responseCode = "403", description = "User not authorized to cancel")
    public ResponseEntity<Void> cancelSession(@PathVariable Long sessionId, @RequestParam Long userId) {
        try {
            sessionService.cancelSession(sessionId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Get session count.
     *
     * @return Total number of sessions
     */
    @GetMapping("/count/total")
    @Operation(summary = "Get session count", description = "Get total number of study sessions")
    @ApiResponse(responseCode = "200", description = "Count retrieved")
    public ResponseEntity<Long> getSessionCount() {
        long count = sessionService.getSessionCount();
        return ResponseEntity.ok(count);
    }

    private StudySessionDTO convertToDTO(StudySession session) {
        StudySessionDTO dto = new StudySessionDTO(
            session.getTitle(),
            session.getScheduledAt(),
            session.getDurationHours(),
            session.getLocation(),
            session.getGroupId(),
            session.getCreatedBy()
        );
        dto.setSessionId(session.getSessionId());
        dto.setNotes(session.getNotes());
        return dto;
    }
}
