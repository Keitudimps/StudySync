package com.studysync.repository.stubs;

/**
 * STUB — Future SQL database implementation of StudySessionRepository.
 * Implement all interface methods using JPA/JDBC when integrating a real database.
 */
public class DatabaseStudySessionRepository implements com.studysync.repository.StudySessionRepository {
    @Override public void save(com.studysync.domain.StudySession entity) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.Optional<com.studysync.domain.StudySession> findById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudySession> findAll() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public boolean existsById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public long count() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudySession> findByGroupId(Long groupId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudySession> findUpcomingSessions() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudySession> findPastSessions() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudySession> findByCreatedBy(Long userId) { throw new UnsupportedOperationException("Not implemented"); }
}
