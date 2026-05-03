package com.studysync.creational;

import com.studysync.creational.builder.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class StudyGroupBuilderTest {

    @Test
    void testBuildMinimalGroup() {
        System.out.println("\n=== TEST: Minimal Group Build ===");
        System.out.println("Building group with required fields only (defaults applied)...");

        StudyGroupDTO group = StudyGroupBuilder.builder("Math Study", "MATH101")
                .build();

        System.out.println("Name         : " + group.getName());
        System.out.println("Course Code  : " + group.getCourseCode());
        System.out.println("Max Capacity : " + group.getMaxCapacity());
        System.out.println("Privacy      : " + group.getPrivacy());

        assertNotNull(group, "Builder must produce a non-null StudyGroupDTO");
        assertEquals("Math Study", group.getName(),
                "Group name must match the value passed to builder()");
        assertEquals("MATH101", group.getCourseCode(),
                "Course code must match the value passed to builder()");
        assertEquals(10, group.getMaxCapacity(),
                "Default max capacity must be 10");
        assertEquals("PUBLIC", group.getPrivacy(),
                "Default privacy must be PUBLIC");

        System.out.println("✓ PASS — all default values applied correctly");
    }

    @Test
    void testBuildFullGroup() {
        System.out.println("\n=== TEST: Full Group Build ===");
        System.out.println("Building group with all optional fields set...");

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

        System.out.println("Name         : " + group.getName());
        System.out.println("Course Code  : " + group.getCourseCode());
        System.out.println("Description  : " + group.getDescription());
        System.out.println("Max Capacity : " + group.getMaxCapacity());
        System.out.println("Privacy      : " + group.getPrivacy());
        System.out.println("Tags         : " + group.getTags());
        System.out.println("Location     : " + group.getLocation());
        System.out.println("Meeting Time : " + group.getMeetingTime());

        assertNotNull(group, "Builder must produce a non-null StudyGroupDTO");
        assertEquals("Physics 202", group.getName());
        assertEquals("PHYS202", group.getCourseCode());
        assertEquals("Weekly problem-solving sessions", group.getDescription(),
                "Description must match the value set via description()");
        assertEquals(15, group.getMaxCapacity(),
                "Max capacity must match the value set via maxCapacity()");
        assertEquals("PRIVATE", group.getPrivacy(),
                "Privacy must be PRIVATE after calling privateGroup()");
        assertEquals(2, group.getTags().size(),
                "Tags list must contain exactly 2 entries");
        assertTrue(group.getTags().contains("difficult"), "Tags must contain 'difficult'");
        assertTrue(group.getTags().contains("weekly"), "Tags must contain 'weekly'");
        assertEquals("Room 3.24", group.getLocation(),
                "Location must match the value set via location()");
        assertEquals(meetingTime, group.getMeetingTime(),
                "Meeting time must match the value set via meetingTime()");

        System.out.println("✓ PASS — all fields set and verified correctly");
    }

    @Test
    void testCapacityValidationBelowMinimum() {
        System.out.println("\n=== TEST: Capacity Validation — Below Minimum ===");
        System.out.println("Attempting to set capacity to 1 (minimum is 2)...");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> StudyGroupBuilder.builder("Group", "CS101").maxCapacity(1),
                "Setting capacity below 2 must throw IllegalArgumentException"
        );

        System.out.println("Exception caught: " + exception.getMessage());

        assertEquals("Capacity must be between 2 and 50", exception.getMessage(),
                "Exception message must describe the valid capacity range");

        System.out.println("✓ PASS — below-minimum capacity correctly rejected");
    }

    @Test
    void testCapacityValidationAboveMaximum() {
        System.out.println("\n=== TEST: Capacity Validation — Above Maximum ===");
        System.out.println("Attempting to set capacity to 51 (maximum is 50)...");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> StudyGroupBuilder.builder("Group", "CS101").maxCapacity(51),
                "Setting capacity above 50 must throw IllegalArgumentException"
        );

        System.out.println("Exception caught: " + exception.getMessage());

        assertEquals("Capacity must be between 2 and 50", exception.getMessage(),
                "Exception message must describe the valid capacity range");

        System.out.println("✓ PASS — above-maximum capacity correctly rejected");
    }

    @Test
    void testFluentApiMethodChaining() {
        System.out.println("\n=== TEST: Fluent API Method Chaining ===");
        System.out.println("Building group using a full fluent chain...");

        StudyGroupDTO group = StudyGroupBuilder.builder("Chained", "TEST101")
                .description("Test description")
                .maxCapacity(5)
                .publicGroup()
                .addTag("tag1")
                .addTag("tag2")
                .build();

        System.out.println("Name         : " + group.getName());
        System.out.println("Description  : " + group.getDescription());
        System.out.println("Max Capacity : " + group.getMaxCapacity());
        System.out.println("Privacy      : " + group.getPrivacy());
        System.out.println("Tags         : " + group.getTags());

        assertNotNull(group, "Fluent chain must produce a non-null StudyGroupDTO");
        assertEquals("Chained", group.getName());
        assertEquals("Test description", group.getDescription());
        assertEquals(5, group.getMaxCapacity());
        assertEquals("PUBLIC", group.getPrivacy());
        assertEquals(2, group.getTags().size(),
                "Tags list must contain exactly 2 entries after two addTag() calls");
        assertTrue(group.getTags().contains("tag1"), "Tags must contain 'tag1'");
        assertTrue(group.getTags().contains("tag2"), "Tags must contain 'tag2'");

        System.out.println("✓ PASS — fluent API method chaining working correctly");
    }
}
