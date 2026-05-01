package com.studysync.creational.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudyGroupBuilder {
    private String name;
    private String description = "";
    private String courseCode;
    private Integer maxCapacity = 10;
    private String privacy = "PUBLIC";
    private List<String> tags = new ArrayList<>();
    private LocalDateTime meetingTime;
    private String location;
    
    private StudyGroupBuilder(String name, String courseCode) {
        this.name = name;
        this.courseCode = courseCode;
    }
    
    public static StudyGroupBuilder builder(String name, String courseCode) {
        return new StudyGroupBuilder(name, courseCode);
    }
    
    public StudyGroupBuilder description(String description) {
        this.description = description;
        return this;
    }
    
    public StudyGroupBuilder maxCapacity(Integer capacity) {
        if (capacity < 2 || capacity > 50) {
            throw new IllegalArgumentException("Capacity must be between 2 and 50");
        }
        this.maxCapacity = capacity;
        return this;
    }
    
    public StudyGroupBuilder privateGroup() {
        this.privacy = "PRIVATE";
        return this;
    }
    
    public StudyGroupBuilder publicGroup() {
        this.privacy = "PUBLIC";
        return this;
    }
    
    public StudyGroupBuilder addTag(String tag) {
        this.tags.add(tag);
        return this;
    }
    
    public StudyGroupBuilder meetingTime(LocalDateTime time) {
        this.meetingTime = time;
        return this;
    }
    
    public StudyGroupBuilder location(String location) {
        this.location = location;
        return this;
    }
    
    public StudyGroupDTO build() {
        StudyGroupDTO group = new StudyGroupDTO();
        group.setName(name);
        group.setDescription(description);
        group.setCourseCode(courseCode);
        group.setMaxCapacity(maxCapacity);
        group.setPrivacy(privacy);
        group.setTags(tags);
        group.setMeetingTime(meetingTime);
        group.setLocation(location);
        return group;
    }
}
