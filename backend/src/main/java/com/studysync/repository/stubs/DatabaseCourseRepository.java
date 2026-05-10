package com.studysync.repository.stubs;

/**
 * STUB — Future SQL database implementation of CourseRepository.
 * Implement all interface methods using JPA/JDBC when integrating a real database.
 */
public class DatabaseCourseRepository implements com.studysync.repository.CourseRepository {
    @Override public void save(com.studysync.domain.Course entity) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.Optional<com.studysync.domain.Course> findById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.Course> findAll() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public boolean existsById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public long count() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.Optional<com.studysync.domain.Course> findByCourseCode(String courseCode) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public boolean existsByCourseCode(String courseCode) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.Course> searchByKeyword(String keyword) { throw new UnsupportedOperationException("Not implemented"); }
}
