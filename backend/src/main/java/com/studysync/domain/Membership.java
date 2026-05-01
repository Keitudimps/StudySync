package com.studysync.domain;

import java.time.LocalDateTime;

public class Membership {
    private Long membershipId;
    private MembershipStatus status;
    private LocalDateTime joinedAt;
    private Long userId;
    private Long groupId;
    
    public Membership(Long userId, Long groupId, MembershipStatus status) {
        this.userId = userId;
        this.groupId = groupId;
        this.status = status;
        this.joinedAt = LocalDateTime.now();
    }
    
    public void approve() {
        if (this.status != MembershipStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be approved");
        }
        this.status = MembershipStatus.ACTIVE;
    }
    
    public void reject() {
        if (this.status != MembershipStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be rejected");
        }
    }
    
    public void leave() {}
    public void remove() {}
    
    // Getters
    public Long getMembershipId() { return membershipId; }
    public MembershipStatus getStatus() { return status; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public Long getUserId() { return userId; }
    public Long getGroupId() { return groupId; }
}
