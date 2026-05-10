package com.studysync.repository.stubs;

/**
 * STUB — Future SQL database implementation of StudyGroupRepository.
 * Implement all interface methods using JPA/JDBC when integrating a real database.
 */
public class DatabaseStudyGroupRepository implements com.studysync.repository.StudyGroupRepository {
    @Override public void save(com.studysync.domain.StudyGroup entity) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.Optional<com.studysync.domain.StudyGroup> findById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudyGroup> findAll() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public boolean existsById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public long count() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudyGroup> findByCourseId(Long courseId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudyGroup> findPublicGroups() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudyGroup> findByCreatorId(Long creatorId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudyGroup> searchByName(String keyword) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.StudyGroup> findGroupsWithAvailableSpace() { throw new UnsupportedOperationException("Not implemented"); }
}
