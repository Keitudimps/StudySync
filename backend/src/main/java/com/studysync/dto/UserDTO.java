package com.studysync.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for User creation and updates.
 */
public class UserDTO {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("year_of_study")
    private Integer yearOfStudy;

    @JsonProperty("is_active")
    private Boolean isActive;

    public UserDTO() {}

    public UserDTO(String name, String email, Integer yearOfStudy) {
        this.name = name;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(Integer yearOfStudy) { this.yearOfStudy = yearOfStudy; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
