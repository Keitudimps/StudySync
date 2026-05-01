package com.studysync.domain;

import java.time.LocalDateTime;

public class UserCourse {
    private Long userCourseId;
    private Long userId;
    private Long courseId;
    private LocalDateTime enrolledAt;
    
    public UserCourse(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.enrolledAt = LocalDateTime.now();
    }
    
    public Long getUserCourseId() { return userCourseId; }
    public Long getUserId() { return userId; }
    public Long getCourseId() { return courseId; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
}
