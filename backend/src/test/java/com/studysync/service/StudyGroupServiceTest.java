package com.studysync.service;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.repository.StudyGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudyGroupService business logic.
 * Tests group creation, member management, and validation.
 */
@DisplayName("StudyGroupService Tests")
public class StudyGroupServiceTest {
    private StudyGroupService groupService;

    @Mock
    private StudyGroupRepository groupRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        groupService = new StudyGroupService(groupRepository);
    }

    @Test
    @DisplayName("Should create a study group with valid input")
    void testCreateGroup_Success() {
        // Arrange
        String name = "CS101 Study Group";
        Long courseId = 1L;
        Integer maxCapacity = 10;
        Long creatorId = 1L;

        StudyGroup mockGroup = StudyGroup.create(name, courseId, maxCapacity, Privacy.PUBLIC, creatorId);
        when(groupRepository.searchByName(name)).thenReturn(new ArrayList<>());
        doNothing().when(groupRepository).save(any(StudyGroup.class));

        // Act
        StudyGroup result = groupService.createGroup(name, courseId, maxCapacity, Privacy.PUBLIC, creatorId);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(groupRepository).save(any(StudyGroup.class));
    }

    @Test
    @DisplayName("Should throw exception when group name already exists")
    void testCreateGroup_DuplicateName() {
        // Arrange
        String name = "Existing Group";
        Long courseId = 1L;
        StudyGroup existingGroup = StudyGroup.create(name, courseId, 5, Privacy.PRIVATE, 1L);
        List<StudyGroup> existing = new ArrayList<>();
        existing.add(existingGroup);
        when(groupRepository.searchByName(name)).thenReturn(existing);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            groupService.createGroup(name, courseId, 10, Privacy.PUBLIC, 2L)
        );
        verify(groupRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should retrieve group by ID successfully")
    void testGetGroupById_Success() {
        // Arrange
        Long groupId = 1L;
        StudyGroup mockGroup = StudyGroup.create("Test Group", 1L, 10, Privacy.PUBLIC, 1L);
        when(groupRepository.findById(groupId)).thenReturn(java.util.Optional.of(mockGroup));

        // Act
        StudyGroup result = groupService.getGroupById(groupId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Group", result.getName());
        verify(groupRepository).findById(groupId);
    }

    @Test
    @DisplayName("Should throw exception when group not found by ID")
    void testGetGroupById_NotFound() {
        // Arrange
        Long groupId = 999L;
        when(groupRepository.findById(groupId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            groupService.getGroupById(groupId)
        );
    }

    @Test
    @DisplayName("Should get all groups")
    void testGetAllGroups_Success() {
        // Arrange
        List<StudyGroup> mockGroups = new ArrayList<>();
        mockGroups.add(StudyGroup.create("Group1", 1L, 5, Privacy.PUBLIC, 1L));
        mockGroups.add(StudyGroup.create("Group2", 2L, 10, Privacy.PRIVATE, 2L));
        when(groupRepository.findAll()).thenReturn(mockGroups);

        // Act
        List<StudyGroup> result = groupService.getAllGroups();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(groupRepository).findAll();
    }

    @Test
    @DisplayName("Should get public groups only")
    void testGetPublicGroups_Success() {
        // Arrange
        List<StudyGroup> allGroups = new ArrayList<>();
        StudyGroup publicGroup = StudyGroup.create("Public", 1L, 5, Privacy.PUBLIC, 1L);
        StudyGroup privateGroup = StudyGroup.create("Private", 2L, 10, Privacy.PRIVATE, 2L);
        allGroups.add(publicGroup);
        allGroups.add(privateGroup);
        when(groupRepository.findAll()).thenReturn(allGroups);

        // Act
        List<StudyGroup> result = groupService.getPublicGroups();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get groups by course ID")
    void testGetGroupsByCourse_Success() {
        // Arrange
        Long courseId = 1L;
        List<StudyGroup> mockGroups = new ArrayList<>();
        mockGroups.add(StudyGroup.create("Math101", courseId, 8, Privacy.PUBLIC, 1L));
        when(groupRepository.findByCourseId(courseId)).thenReturn(mockGroups);

        // Act
        List<StudyGroup> result = groupService.getGroupsByCourse(courseId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(groupRepository).findByCourseId(courseId);
    }

    @Test
    @DisplayName("Should delete group by creator only")
    void testDeleteGroup_CreatorOnly() {
        // Arrange
        Long groupId = 1L;
        Long creatorId = 1L;
        StudyGroup mockGroup = StudyGroup.create("Group", 1L, 5, Privacy.PUBLIC, creatorId);
        mockGroup.setGroupId(groupId);
        when(groupRepository.findById(groupId)).thenReturn(java.util.Optional.of(mockGroup));
        doNothing().when(groupRepository).deleteById(groupId);

        // Act
        groupService.deleteGroup(groupId, creatorId);

        // Assert
        verify(groupRepository).deleteById(groupId);
    }

    @Test
    @DisplayName("Should throw exception when non-creator tries to delete")
    void testDeleteGroup_NonCreator() {
        // Arrange
        Long groupId = 1L;
        Long creatorId = 1L;
        Long otherUserId = 2L;
        StudyGroup mockGroup = StudyGroup.create("Group", 1L, 5, Privacy.PUBLIC, creatorId);
        mockGroup.setGroupId(groupId);
        when(groupRepository.findById(groupId)).thenReturn(java.util.Optional.of(mockGroup));

        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
            groupService.deleteGroup(groupId, otherUserId)
        );
        verify(groupRepository, never()).deleteById(groupId);
    }

    @Test
    @DisplayName("Should check if group has available slots")
    void testHasAvailableSlots_Success() {
        // Arrange
        Long groupId = 1L;
        StudyGroup mockGroup = StudyGroup.create("Group", 1L, 10, Privacy.PUBLIC, 1L);
        mockGroup.setGroupId(groupId);
        when(groupRepository.findById(groupId)).thenReturn(java.util.Optional.of(mockGroup));

        // Act
        boolean result = groupService.hasAvailableSlots(groupId);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should get group count")
    void testGetGroupCount_Success() {
        // Arrange
        when(groupRepository.count()).thenReturn(3L);

        // Act
        long result = groupService.getGroupCount();

        // Assert
        assertEquals(3L, result);
        verify(groupRepository).count();
    }
}
