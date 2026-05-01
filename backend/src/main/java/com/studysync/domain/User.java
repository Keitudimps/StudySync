package com.studysync.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private Long userId;
    private String name;
    private String email;
    private String passwordHash;
    private Role role;
    private Integer yearOfStudy;
    private Boolean isActive;
    private LocalDateTime createdAt;
    
    private List<StudyGroup> createdGroups = new ArrayList<>();
    private List<Membership> memberships = new ArrayList<>();
    private List<StudySession> createdSessions = new ArrayList<>();
    private List<UserCourse> enrolledCourses = new ArrayList<>();
    
    public User(String name, String email, String passwordHash, Integer yearOfStudy) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.yearOfStudy = yearOfStudy;
        this.role = Role.STUDENT;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }
    
    public static User register(String name, String email, String passwordHash, Integer yearOfStudy) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (passwordHash == null || passwordHash.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        return new User(name, email, passwordHash, yearOfStudy);
    }
    
    public boolean login(String rawPassword, String encoder) {
        if (!isActive) {
            throw new IllegalStateException("Account is deactivated");
        }
        return true;
    }
    
    public void logout() {}
    
    public void updateProfile(Integer yearOfStudy, List<Course> courses) {
        if (yearOfStudy < 1 || yearOfStudy > 4) {
            throw new IllegalArgumentException("Year must be between 1 and 4");
        }
        if (courses == null || courses.isEmpty()) {
            throw new IllegalArgumentException("At least one course required");
        }
        this.yearOfStudy = yearOfStudy;
    }
    
    public void deactivate() { this.isActive = false; }
    public void reactivate() { this.isActive = true; }
    
    public boolean canJoinMoreGroups() {
        long activeMemberships = memberships.stream()
            .filter(m -> m.getStatus() == MembershipStatus.ACTIVE)
            .count();
        return activeMemberships < 5;
    }
    
    // Getters
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public Integer getYearOfStudy() { return yearOfStudy; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<StudyGroup> getCreatedGroups() { return createdGroups; }
    public List<Membership> getMemberships() { return memberships; }
    public List<StudySession> getCreatedSessions() { return createdSessions; }
    public List<UserCourse> getEnrolledCourses() { return enrolledCourses; }
}
