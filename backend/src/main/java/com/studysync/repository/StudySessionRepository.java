package com.studysync.repository;

import com.studysync.domain.StudySession;
import java.util.List;

/**
 * Entity-specific repository for StudySession.
 */
public interface StudySessionRepository extends Repository<StudySession, Long> {

    /** Return all sessions scheduled within the given group. */
    List<StudySession> findByGroupId(Long groupId);

    /** Return all sessions whose scheduled time is in the future. */
    List<StudySession> findUpcomingSessions();

    /** Return all sessions whose scheduled time is in the past. */
    List<StudySession> findPastSessions();

    /** Return all sessions created by the given user. */
    List<StudySession> findByCreatedBy(Long userId);
}
