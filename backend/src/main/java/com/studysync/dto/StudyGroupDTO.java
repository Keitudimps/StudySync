package com.studysync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for StudyGroup creation and updates.
 */
public class StudyGroupDTO {
    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("max_capacity")
    private Integer maxCapacity;

    @JsonProperty("privacy")
    private String privacy;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("creator_id")
    private Long creatorId;

    public StudyGroupDTO() {}

    public StudyGroupDTO(String name, String description, Integer maxCapacity, 
                        String privacy, Long courseId, Long creatorId) {
        this.name = name;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.privacy = privacy;
        this.courseId = courseId;
        this.creatorId = creatorId;
    }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }
    public String getPrivacy() { return privacy; }
    public void setPrivacy(String privacy) { this.privacy = privacy; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getCreatorId() { return creatorId; }
    public void setCreatorId(Long creatorId) { this.creatorId = creatorId; }
}
