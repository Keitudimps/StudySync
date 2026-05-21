package com.studysync.service;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.repository.StudyGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for StudyGroup-related business operations.
 * Handles group creation, member management, and validation.
 */
@Service
public class StudyGroupService {
    private final StudyGroupRepository groupRepository;

    public StudyGroupService(StudyGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Create a new study group with validation.
     * Business rule: Group name must be unique, max capacity 2-50 members.
     *
     * @param name         Group name
     * @param courseId     Course ID
     * @param maxCapacity  Maximum members (2-50)
     * @param privacy      Group privacy setting
     * @param creatorId    Creator's user ID
     * @return Created StudyGroup
     * @throws IllegalArgumentException if validation fails
     */
    public StudyGroup createGroup(String name, Long courseId, Integer maxCapacity,
                                  Privacy privacy, Long creatorId) {
        // Delegate validation to domain model
        StudyGroup group = StudyGroup.create(name, courseId, maxCapacity, privacy, creatorId);

        // Check for duplicate group name using searchByName
        List<StudyGroup> existing = groupRepository.searchByName(name);
        if (!existing.isEmpty()) {
            throw new IllegalArgumentException("Group name already exists: " + name);
        }

        // Persist group
        groupRepository.save(group);
        return group;
    }

    /**
     * Retrieve a study group by ID.
     *
     * @param groupId Group ID
     * @return StudyGroup
     * @throws IllegalArgumentException if not found
     */
    public StudyGroup getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException("Study group not found with ID: " + groupId));
    }

    /**
     * Retrieve a study group by name.
     *
     * @param name Group name
     * @return StudyGroup
     * @throws IllegalArgumentException if not found
     */
    public StudyGroup getGroupByName(String name) {
        List<StudyGroup> results = groupRepository.searchByName(name);
        if (results.isEmpty()) {
            throw new IllegalArgumentException("Study group not found: " + name);
        }
        return results.get(0); // Return first match
    }

    /**
     * Get all study groups.
     *
     * @return List of all groups
     */
    public List<StudyGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * Get all public study groups.
     *
     * @return List of public groups
     */
    public List<StudyGroup> getPublicGroups() {
        List<StudyGroup> allGroups = groupRepository.findAll();
        return allGroups.stream()
            .filter(g -> g.getPrivacy() == Privacy.PUBLIC)
            .toList();
    }

    /**
     * Get study groups for a specific course.
     *
     * @param courseId Course ID
     * @return List of groups for course
     */
    public List<StudyGroup> getGroupsByCourse(Long courseId) {
        return groupRepository.findByCourseId(courseId);
    }

    /**
     * Get study groups created by a specific user.
     *
     * @param creatorId Creator's user ID
     * @return List of groups created by user
     */
    public List<StudyGroup> getGroupsByCreator(Long creatorId) {
        return groupRepository.findByCreatorId(creatorId);
    }

    /**
     * Check if a study group has available slots.
     * Business rule: Users can't join a full group.
     *
     * @param groupId Group ID
     * @return true if group has available slots
     */
    public boolean hasAvailableSlots(Long groupId) {
        StudyGroup group = getGroupById(groupId);
        return !group.isFull();
    }

    /**
     * Search study groups by name.
     *
     * @param searchTerm Search term
     * @return List of matching groups
     */
    public List<StudyGroup> searchGroups(String searchTerm) {
        return groupRepository.searchByName(searchTerm);
    }

    /**
     * Update study group details.
     *
     * @param groupId     Group ID
     * @param name        Updated name
     * @param description Updated description
     * @return Updated StudyGroup
     */
    public StudyGroup updateGroup(Long groupId, String name, String description) {
        StudyGroup group = getGroupById(groupId);
<<<<<<< HEAD
        group.updateDetails(name, description);
=======
        // In a real system, use proper setters
>>>>>>> a45ea42cb29611d2dde3a32c101ba083797f449e
        groupRepository.save(group);
        return group;
    }

    /**
     * Delete a study group.
     * Business rule: Only group creator can delete.
     *
     * @param groupId   Group ID
     * @param userId    User requesting deletion
     */
    public void deleteGroup(Long groupId, Long userId) {
        StudyGroup group = getGroupById(groupId);

        if (!group.getCreatorId().equals(userId)) {
            throw new IllegalStateException("Only group creator can delete the group");
        }

        group.delete();
        groupRepository.deleteById(groupId);
    }

    /**
     * Get count of study groups.
     *
     * @return Number of groups
     */
    public long getGroupCount() {
        return groupRepository.count();
    }
}
