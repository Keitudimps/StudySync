package com.studysync.domain;

import java.time.LocalDateTime;

public class StudySession {
    private Long sessionId;
    private String title;
    private LocalDateTime scheduledAt;
    private Integer durationHours;
    private String location;
    private String notes;
    private Long groupId;
    private Long createdBy;
    
    public StudySession(String title, LocalDateTime scheduledAt, Integer durationHours,
                        String location, String notes, Long groupId, Long createdBy) {
        this.title = title;
        this.scheduledAt = scheduledAt;
        this.durationHours = durationHours;
        this.location = location;
        this.notes = notes;
        this.groupId = groupId;
        this.createdBy = createdBy;
    }
    
    public static StudySession schedule(String title, LocalDateTime scheduledAt, 
                                        Integer durationHours, String location,
                                        Long groupId, Long createdBy) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Session title is required");
        }
        if (scheduledAt.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("Session must be at least 30 minutes in the future");
        }
        if (durationHours == null || durationHours < 1) {
            throw new IllegalArgumentException("Duration must be at least 1 hour");
        }
        return new StudySession(title, scheduledAt, durationHours, location, null, groupId, createdBy);
    }
    
    public void cancel() {
        if (scheduledAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel a past session");
        }
    }
    
    public void reschedule(LocalDateTime newTime) {
        if (newTime.isBefore(LocalDateTime.now().plusMinutes(30))) {
            throw new IllegalArgumentException("New time must be at least 30 minutes in the future");
        }
        this.scheduledAt = newTime;
    }
    
    public boolean isUpcoming() { return scheduledAt.isAfter(LocalDateTime.now()); }
    public boolean isPast() { return scheduledAt.isBefore(LocalDateTime.now()); }
    
    // Getters
    public Long getSessionId() { return sessionId; }
    public String getTitle() { return title; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public Integer getDurationHours() { return durationHours; }
    public String getLocation() { return location; }
    public String getNotes() { return notes; }
    public Long getGroupId() { return groupId; }
    public Long getCreatedBy() { return createdBy; }
}
