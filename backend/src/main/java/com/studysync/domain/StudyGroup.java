package com.studysync.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudyGroup {
    private Long groupId;
    private String name;
    private String description;
    private Privacy privacy;
    private Integer maxCapacity;
    private LocalDateTime createdAt;
    private Long creatorId;
    private Long courseId;
    
    private List<Membership> members = new ArrayList<>();
    private List<StudySession> sessions = new ArrayList<>();
    
    public StudyGroup(String name, String description, Privacy privacy, 
                      Integer maxCapacity, Long creatorId, Long courseId) {
        this.name = name;
        this.description = description;
        this.privacy = privacy;
        this.maxCapacity = maxCapacity;
        this.creatorId = creatorId;
        this.courseId = courseId;
        this.createdAt = LocalDateTime.now();
    }
    
    public static StudyGroup create(String name, Long courseId, Integer maxCapacity, 
                                     Privacy privacy, Long creatorId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name is required");
        }
        if (courseId == null) {
            throw new IllegalArgumentException("Course must be selected");
        }
        if (maxCapacity < 2 || maxCapacity > 50) {
            throw new IllegalArgumentException("Capacity must be between 2 and 50");
        }
        return new StudyGroup(name, "", privacy, maxCapacity, creatorId, courseId);
    }
    
    public void delete() {}
    
    public boolean isFull() {
        long activeMembers = members.stream()
            .filter(m -> m.getStatus() == MembershipStatus.ACTIVE)
            .count();
        return activeMembers >= maxCapacity;
    }
    
    public List<Membership> getMembers() { return new ArrayList<>(members); }
    public List<StudySession> getSessions() { return new ArrayList<>(sessions); }
    
    public void updateDetails(String name, String description) {
        if (name != null && !name.trim().isEmpty()) { this.name = name; }
        if (description != null) { this.description = description; }
    }
    
    public void addMember(Membership membership) { this.members.add(membership); }
    public void addSession(StudySession session) { this.sessions.add(session); }
    
    // Getters
    public Long getGroupId() { return groupId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Privacy getPrivacy() { return privacy; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getCreatorId() { return creatorId; }
    public Long getCourseId() { return courseId; }
}
