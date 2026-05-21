package com.studysync.controller;

import com.studysync.domain.Privacy;
import com.studysync.domain.StudyGroup;
import com.studysync.dto.StudyGroupDTO;
import com.studysync.service.StudyGroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("StudyGroupController Integration Tests")
public class StudyGroupControllerTest {
    private StudyGroupController studyGroupController;

    @Mock
    private StudyGroupService studyGroupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        studyGroupController = new StudyGroupController(studyGroupService);
    }

    @Test
    @DisplayName("POST /api/groups - Should return 201 Created")
    void testCreateGroup_Created() {
        StudyGroupDTO input = new StudyGroupDTO("DBMS Group", "Database revision", 10, "public", 1L, 1L);
        StudyGroup group = new StudyGroup("DBMS Group", "Database revision", Privacy.PUBLIC, 10, 1L, 1L);
        group.setGroupId(1L);

        when(studyGroupService.createGroup("DBMS Group", 1L, 10, Privacy.PUBLIC, 1L)).thenReturn(group);

        ResponseEntity<StudyGroupDTO> response = studyGroupController.createGroup(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("DBMS Group", response.getBody().getName());
        verify(studyGroupService).createGroup("DBMS Group", 1L, 10, Privacy.PUBLIC, 1L);
    }

    @Test
    @DisplayName("GET /api/groups - Should return 200 OK")
    void testGetAllGroups_Success() {
        StudyGroup group = new StudyGroup("Java Group", "Spring Boot revision", Privacy.PUBLIC, 8, 2L, 3L);
        when(studyGroupService.getAllGroups()).thenReturn(List.of(group));

        ResponseEntity<List<StudyGroupDTO>> response = studyGroupController.getAllGroups();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(studyGroupService).getAllGroups();
    }

    @Test
    @DisplayName("GET /api/groups/{groupId} - Should return 404 Not Found")
    void testGetGroupById_NotFound() {
        when(studyGroupService.getGroupById(99L)).thenThrow(new IllegalArgumentException("Group not found"));

        ResponseEntity<StudyGroupDTO> response = studyGroupController.getGroupById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /api/groups/{groupId} - Should return 403 for non-creator")
    void testDeleteGroup_Forbidden() {
        doThrow(new IllegalStateException("Only creator can delete")).when(studyGroupService).deleteGroup(1L, 9L);

        ResponseEntity<Void> response = studyGroupController.deleteGroup(1L, 9L);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
