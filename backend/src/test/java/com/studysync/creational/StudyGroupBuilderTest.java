package com.studysync.creational;

import com.studysync.creational.builder.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class StudyGroupBuilderTest {
    
    @Test
    void testBuildMinimalGroup() {
        StudyGroupDTO group = StudyGroupBuilder.builder("Math Study", "MATH101")
                .build();
        
        assertEquals("Math Study", group.getName());
        assertEquals("MATH101", group.getCourseCode());
        assertEquals(10, group.getMaxCapacity());
        assertEquals("PUBLIC", group.getPrivacy());
    }
    
    @Test
    void testBuildFullGroup() {
        LocalDateTime meetingTime = LocalDateTime.now().plusDays(3);
        
        StudyGroupDTO group = StudyGroupBuilder.builder("Physics 202", "PHYS202")
                .description("Weekly problem-solving sessions")
                .maxCapacity(15)
                .privateGroup()
                .addTag("difficult")
                .addTag("weekly")
                .meetingTime(meetingTime)
                .location("Room 3.24")
                .build();
        
        assertEquals("Physics 202", group.getName());
        assertEquals(15, group.getMaxCapacity());
        assertEquals("PRIVATE", group.getPrivacy());
        assertEquals(2, group.getTags().size());
    }
    
    @Test
    void testCapacityValidation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            StudyGroupBuilder.builder("Group", "CS101").maxCapacity(1);
        });
        assertEquals("Capacity must be between 2 and 50", exception.getMessage());
    }
    
    @Test
    void testChainingMethods() {
        StudyGroupDTO group = StudyGroupBuilder.builder("Chained", "TEST101")
                .description("Test")
                .maxCapacity(5)
                .publicGroup()
                .addTag("tag1")
                .addTag("tag2")
                .build();
        
        assertNotNull(group);
    }
}
