package com.studysync.repository;

import com.studysync.domain.Membership;
import com.studysync.domain.MembershipStatus;
import java.util.List;
import java.util.Optional;

/**
 * Entity-specific repository for Membership.
 */
public interface MembershipRepository extends Repository<Membership, Long> {

    /** Return all memberships for the given user. */
    List<Membership> findByUserId(Long userId);

    /** Return all memberships in the given group. */
    List<Membership> findByGroupId(Long groupId);

    /** Return memberships filtered by status (PENDING or ACTIVE). */
    List<Membership> findByStatus(MembershipStatus status);

    /** Return the membership for a specific user in a specific group, if it exists. */
    Optional<Membership> findByUserIdAndGroupId(Long userId, Long groupId);

    /** Count the active memberships for a given user (enforces the 5-group limit). */
    long countActiveByUserId(Long userId);
}
