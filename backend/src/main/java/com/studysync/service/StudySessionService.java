package com.studysync.service;

import com.studysync.domain.StudySession;
import com.studysync.repository.StudySessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for StudySession-related business operations.
 * Handles session scheduling, cancellation, and validation.
 */
@Service
public class StudySessionService {
    private final StudySessionRepository sessionRepository;
    private final StudyGroupService groupService;

    public StudySessionService(StudySessionRepository sessionRepository, StudyGroupService groupService) {
        this.sessionRepository = sessionRepository;
        this.groupService = groupService;
    }

    /**
     * Schedule a new study session with validation.
     * Business rule: Session must be at least 30 minutes in future, duration >= 1 hour.
     *
     * @param title      Session title
     * @param scheduledAt Scheduled date/time
     * @param durationHours Duration in hours
     * @param location   Session location (virtual/physical)
     * @param groupId    Associated study group ID
     * @param createdBy  Creator's user ID
     * @return Created StudySession
     * @throws IllegalArgumentException if validation fails
     */
    public StudySession scheduleSession(String title, LocalDateTime scheduledAt,
                                       Integer durationHours, String location,
                                       Long groupId, Long createdBy) {
        // Validate time first (before hitting the repository)
        if (scheduledAt == null || scheduledAt.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("Session must be at least 30 minutes in the future");
        }

        // Validate group exists
        groupService.getGroupById(groupId);

        // Delegate remaining validation to domain model
        StudySession session = StudySession.schedule(title, scheduledAt, durationHours,
            location, groupId, createdBy);

        sessionRepository.save(session);
        return session;
    }

    /**
     * Retrieve a study session by ID.
     *
     * @param sessionId Session ID
     * @return StudySession
     * @throws IllegalArgumentException if not found
     */
    public StudySession getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
            .orElseThrow(() -> new IllegalArgumentException("Study session not found with ID: " + sessionId));
    }

    /**
     * Get all study sessions.
     *
     * @return List of all sessions
     */
    public List<StudySession> getAllSessions() {
        return sessionRepository.findAll();
    }

    /**
     * Get all upcoming study sessions (scheduled in future).
     * Business rule: Helps users plan their study sessions.
     *
     * @return List of upcoming sessions
     */
    public List<StudySession> getUpcomingSessions() {
        return sessionRepository.findUpcomingSessions();
    }

    /**
     * Get study sessions for a specific group.
     *
     * @param groupId Study group ID
     * @return List of sessions for group
     */
    public List<StudySession> getSessionsByGroup(Long groupId) {
        // Validate group exists
        groupService.getGroupById(groupId);
        return sessionRepository.findByGroupId(groupId);
    }

    /**
     * Get upcoming sessions for a specific group.
     *
     * @param groupId Study group ID
     * @return List of upcoming sessions for group
     */
    public List<StudySession> getUpcomingSessionsByGroup(Long groupId) {
        return getSessionsByGroup(groupId).stream()
            .filter(s -> s.getScheduledAt().isAfter(LocalDateTime.now()))
            .toList();
    }

    /**
     * Get all sessions created by a user.
     *
     * @param userId User ID
     * @return List of sessions created by user
     */
    public List<StudySession> getSessionsByCreator(Long userId) {
        return getAllSessions().stream()
            .filter(s -> s.getCreatedBy().equals(userId))
            .toList();
    }

    /**
     * Cancel a study session.
     * Business rule: Can't cancel sessions that already started.
     *
     * @param sessionId Session ID
     * @param userId    User requesting cancellation
     * @throws IllegalStateException if session cannot be cancelled
     */
    public void cancelSession(Long sessionId, Long userId) {
        StudySession session = getSessionById(sessionId);

        if (!session.getCreatedBy().equals(userId)) {
            throw new IllegalStateException("Only session creator can cancel the session");
        }

        session.cancel();
        sessionRepository.deleteById(sessionId);
    }

    /**
     * Reschedule a study session to a new time.
     * Business rule: Can't reschedule to past times or less than 30 minutes away.
     *
     * @param sessionId Session ID
     * @param newTime   New scheduled time
     * @param userId    User requesting reschedule
     * @return Updated StudySession
     */
    public StudySession rescheduleSession(Long sessionId, LocalDateTime newTime, Long userId) {
        StudySession session = getSessionById(sessionId);

        if (!session.getCreatedBy().equals(userId)) {
            throw new IllegalStateException("Only session creator can reschedule the session");
        }

        session.reschedule(newTime);
        sessionRepository.save(session);
        return session;
    }

    /**
     * Update session details.
     *
     * @param sessionId   Session ID
     * @param title       Updated title
     * @param location    Updated location
     * @param notes       Updated notes
     * @return Updated StudySession
     */
    public StudySession updateSession(Long sessionId, String title, String location, String notes) {
        StudySession session = getSessionById(sessionId);
        session.updateDetails(title, location, notes);
        sessionRepository.save(session);
        return session;
    }

    /**
     * Get count of upcoming sessions for a group.
     * Business rule: Helps track group activity.
     *
     * @param groupId Study group ID
     * @return Number of upcoming sessions
     */
    public long getUpcomingSessionCountByGroup(Long groupId) {
        return getUpcomingSessionsByGroup(groupId).size();
    }

    /**
     * Get count of all study sessions.
     *
     * @return Number of sessions
     */
    public long getSessionCount() {
        return sessionRepository.count();
    }
}
