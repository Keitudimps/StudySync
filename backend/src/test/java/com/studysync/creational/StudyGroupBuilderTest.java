package com.studysync.creational;

import com.studysync.creational.builder.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class StudyGroupBuilderTest {
    
    @Test
    void testBuildMinimalGroup() {
        System.out.println("\n=== TEST: Minimal Group Build ===");
        System.out.println("Building group with default values...");
        
        StudyGroupDTO group = StudyGroupBuilder.builder("Math Study", "MATH101")
                .build();
        
        System.out.println("✓ Group created: " + group.getName());
        System.out.println("  Course Code: " + group.getCourseCode());
        System.out.println("  Max Capacity: " + group.getMaxCapacity());
        System.out.println("  Privacy: " + group.getPrivacy());
        
        assertEquals("Math Study", group.getName());
        assertEquals("MATH101", group.getCourseCode());
        assertEquals(10, group.getMaxCapacity());
        assertEquals("PUBLIC", group.getPrivacy());
        
        System.out.println("✓ All assertions passed!");
    }
    
    @Test
    void testBuildFullGroup() {
        System.out.println("\n=== TEST: Full Group Build ===");
        System.out.println("Building group with all optional fields...");
        
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
        
        System.out.println("✓ Full group created: " + group.getName());
        System.out.println("  Description: " + group.getDescription());
        System.out.println("  Max Capacity: " + group.getMaxCapacity());
        System.out.println("  Privacy: " + group.getPrivacy());
        System.out.println("  Tags: " + group.getTags());
        System.out.println("  Location: " + group.getLocation());
        
        assertEquals("Physics 202", group.getName());
        assertEquals(15, group.getMaxCapacity());
        assertEquals("PRIVATE", group.getPrivacy());
        assertEquals(2, group.getTags().size());
        
        System.out.println("✓ All assertions passed!");
    }
    
    @Test
    void testCapacityValidation() {
        System.out.println("\n=== TEST: Capacity Validation ===");
        System.out.println("Testing invalid capacity (1 < 2)...");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            System.out.println("Attempting to set capacity to 1...");
            StudyGroupBuilder.builder("Group", "CS101").maxCapacity(1);
        });
        
        System.out.println("✓ Exception caught: " + exception.getMessage());
        assertEquals("Capacity must be between 2 and 50", exception.getMessage());
        System.out.println("✓ Validation working correctly!");
    }
    
    @Test
    void testChainingMethods() {
        System.out.println("\n=== TEST: Method Chaining ===");
        System.out.println("Testing fluent API method chaining...");
        
        StudyGroupDTO group = StudyGroupBuilder.builder("Chained", "TEST101")
                .description("Test")
                .maxCapacity(5)
                .publicGroup()
                .addTag("tag1")
                .addTag("tag2")
                .build();
        
        System.out.println("✓ Chained group created: " + group.getName());
        System.out.println("  Has " + group.getTags().size() + " tags: " + group.getTags());
        
        assertNotNull(group);
        System.out.println("✓ Fluent API working correctly!");
    }
}
