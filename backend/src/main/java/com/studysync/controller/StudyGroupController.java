package com.studysync.controller;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.dto.StudyGroupDTO;
import com.studysync.service.StudyGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API Controller for StudyGroup operations.
 * Provides endpoints for group creation, member management, and queries.
 */
@RestController
@RequestMapping("/api/groups")
@Tag(name = "Study Groups", description = "Study group management endpoints")
public class StudyGroupController {
    private final StudyGroupService groupService;

    public StudyGroupController(StudyGroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Create a new study group.
     *
     * @param groupDTO Study group creation data
     * @return Created group with 201 status
     */
    @PostMapping
    @Operation(summary = "Create a study group", description = "Create a new study group")
    @ApiResponse(responseCode = "201", description = "Group created successfully",
        content = @Content(schema = @Schema(implementation = StudyGroupDTO.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<StudyGroupDTO> createGroup(@RequestBody StudyGroupDTO groupDTO) {
        try {
            Privacy privacy = Privacy.valueOf(groupDTO.getPrivacy().toUpperCase());
            StudyGroup group = groupService.createGroup(groupDTO.getName(),
                groupDTO.getCourseId(), groupDTO.getMaxCapacity(),
                privacy, groupDTO.getCreatorId());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(group));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get all study groups.
     *
     * @return List of all groups
     */
    @GetMapping
    @Operation(summary = "Get all study groups", description = "Retrieve all study groups")
    @ApiResponse(responseCode = "200", description = "Groups retrieved successfully")
    public ResponseEntity<List<StudyGroupDTO>> getAllGroups() {
        List<StudyGroup> groups = groupService.getAllGroups();
        List<StudyGroupDTO> dtos = groups.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get all public study groups.
     *
     * @return List of public groups
     */
    @GetMapping("/public")
    @Operation(summary = "Get public groups", description = "Retrieve all public study groups")
    @ApiResponse(responseCode = "200", description = "Public groups retrieved successfully")
    public ResponseEntity<List<StudyGroupDTO>> getPublicGroups() {
        List<StudyGroup> groups = groupService.getPublicGroups();
        List<StudyGroupDTO> dtos = groups.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get a study group by ID.
     *
     * @param groupId Group ID
     * @return Group details
     */
    @GetMapping("/{groupId}")
    @Operation(summary = "Get group by ID", description = "Retrieve a study group by ID")
    @ApiResponse(responseCode = "200", description = "Group found")
    @ApiResponse(responseCode = "404", description = "Group not found")
    public ResponseEntity<StudyGroupDTO> getGroupById(@PathVariable Long groupId) {
        try {
            StudyGroup group = groupService.getGroupById(groupId);
            return ResponseEntity.ok(convertToDTO(group));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get groups by course ID.
     *
     * @param courseId Course ID
     * @return List of groups for course
     */
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get groups by course", description = "Retrieve study groups for a specific course")
    @ApiResponse(responseCode = "200", description = "Groups retrieved successfully")
    public ResponseEntity<List<StudyGroupDTO>> getGroupsByCourse(@PathVariable Long courseId) {
        List<StudyGroup> groups = groupService.getGroupsByCourse(courseId);
        List<StudyGroupDTO> dtos = groups.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Get groups created by a user.
     *
     * @param creatorId Creator's user ID
     * @return List of groups created by user
     */
    @GetMapping("/creator/{creatorId}")
    @Operation(summary = "Get groups by creator", description = "Retrieve study groups created by a user")
    @ApiResponse(responseCode = "200", description = "Groups retrieved successfully")
    public ResponseEntity<List<StudyGroupDTO>> getGroupsByCreator(@PathVariable Long creatorId) {
        List<StudyGroup> groups = groupService.getGroupsByCreator(creatorId);
        List<StudyGroupDTO> dtos = groups.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Search groups by name.
     *
     * @param query Search query
     * @return List of matching groups
     */
    @GetMapping("/search")
    @Operation(summary = "Search groups", description = "Search study groups by name")
    @ApiResponse(responseCode = "200", description = "Search results retrieved")
    public ResponseEntity<List<StudyGroupDTO>> searchGroups(@RequestParam String query) {
        List<StudyGroup> groups = groupService.searchGroups(query);
        List<StudyGroupDTO> dtos = groups.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Check if a group has available slots.
     *
     * @param groupId Group ID
     * @return true if slots available
     */
    @GetMapping("/{groupId}/available")
    @Operation(summary = "Check available slots", description = "Check if a study group has available slots")
    @ApiResponse(responseCode = "200", description = "Availability status retrieved")
    public ResponseEntity<Boolean> hasAvailableSlots(@PathVariable Long groupId) {
        try {
            boolean hasSlots = groupService.hasAvailableSlots(groupId);
            return ResponseEntity.ok(hasSlots);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update group details.
     *
     * @param groupId  Group ID
     * @param groupDTO Updated group data
     * @return Updated group
     */
    @PutMapping("/{groupId}")
    @Operation(summary = "Update group", description = "Update study group details")
    @ApiResponse(responseCode = "200", description = "Group updated successfully")
    @ApiResponse(responseCode = "404", description = "Group not found")
    public ResponseEntity<StudyGroupDTO> updateGroup(@PathVariable Long groupId, @RequestBody StudyGroupDTO groupDTO) {
        try {
            StudyGroup group = groupService.updateGroup(groupId, groupDTO.getName(), groupDTO.getDescription());
            return ResponseEntity.ok(convertToDTO(group));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a study group.
     *
     * @param groupId Group ID
     * @param userId  User requesting deletion
     * @return 204 No Content
     */
    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete group", description = "Delete a study group (creator only)")
    @ApiResponse(responseCode = "204", description = "Group deleted successfully")
    @ApiResponse(responseCode = "404", description = "Group not found")
    @ApiResponse(responseCode = "403", description = "User not authorized to delete")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long groupId, @RequestParam Long userId) {
        try {
            groupService.deleteGroup(groupId, userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * Get group count.
     *
     * @return Total number of groups
     */
    @GetMapping("/count/total")
    @Operation(summary = "Get group count", description = "Get total number of study groups")
    @ApiResponse(responseCode = "200", description = "Count retrieved")
    public ResponseEntity<Long> getGroupCount() {
        long count = groupService.getGroupCount();
        return ResponseEntity.ok(count);
    }

    private StudyGroupDTO convertToDTO(StudyGroup group) {
        StudyGroupDTO dto = new StudyGroupDTO(group.getName(), group.getDescription(),
            group.getMaxCapacity(), group.getPrivacy().toString().toLowerCase(),
            group.getCourseId(), group.getCreatorId());
        dto.setGroupId(group.getGroupId());
        return dto;
    }
}
