package com.studysync.repository.stubs;

/**
 * STUB — Future SQL database implementation of MembershipRepository.
 * Implement all interface methods using JPA/JDBC when integrating a real database.
 */
public class DatabaseMembershipRepository implements com.studysync.repository.MembershipRepository {
    @Override public void save(com.studysync.domain.Membership entity) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.Optional<com.studysync.domain.Membership> findById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.Membership> findAll() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public void deleteById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public boolean existsById(Long id) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public long count() { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.Membership> findByUserId(Long userId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.Membership> findByGroupId(Long groupId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.List<com.studysync.domain.Membership> findByStatus(com.studysync.domain.MembershipStatus status) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public java.util.Optional<com.studysync.domain.Membership> findByUserIdAndGroupId(Long userId, Long groupId) { throw new UnsupportedOperationException("Not implemented"); }
    @Override public long countActiveByUserId(Long userId) { throw new UnsupportedOperationException("Not implemented"); }
}
