package com.studysync.creational.builder;

import java.time.LocalDateTime;
import java.util.List;

public class StudyGroupDTO {
    private String name;
    private String description;
    private String courseCode;
    private Integer maxCapacity;
    private String privacy;
    private List<String> tags;
    private LocalDateTime meetingTime;
    private String location;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }
    public String getPrivacy() { return privacy; }
    public void setPrivacy(String privacy) { this.privacy = privacy; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public LocalDateTime getMeetingTime() { return meetingTime; }
    public void setMeetingTime(LocalDateTime meetingTime) { this.meetingTime = meetingTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    @Override
    public String toString() {
        return "StudyGroupDTO{name='" + name + "', courseCode='" + courseCode + 
               "', maxCapacity=" + maxCapacity + ", privacy='" + privacy + "'}";
    }
}
