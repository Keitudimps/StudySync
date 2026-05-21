package com.studysync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

/**
 * DTO for StudySession creation and updates.
 */
public class StudySessionDTO {
    @JsonProperty("session_id")
    private Long sessionId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("scheduled_at")
    private LocalDateTime scheduledAt;

    @JsonProperty("duration_hours")
    private Integer durationHours;

    @JsonProperty("location")
    private String location;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("created_by")
    private Long createdBy;

    public StudySessionDTO() {}

    public StudySessionDTO(String title, LocalDateTime scheduledAt, Integer durationHours,
                          String location, Long groupId, Long createdBy) {
        this.title = title;
        this.scheduledAt = scheduledAt;
        this.durationHours = durationHours;
        this.location = location;
        this.groupId = groupId;
        this.createdBy = createdBy;
    }

    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public Integer getDurationHours() { return durationHours; }
    public void setDurationHours(Integer durationHours) { this.durationHours = durationHours; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public Long getCreatedBy() { return createdBy; }
    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
}
