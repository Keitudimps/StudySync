package com.studysync.creational;

import com.studysync.creational.builder.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Builder Pattern — StudyGroupBuilder")
class StudyGroupBuilderTest {

    @Test
    @DisplayName("Minimal build uses correct defaults: capacity=10, privacy=PUBLIC")
    void testBuildMinimalGroup() {
        System.out.println("\n--- TEST: Minimal Group Build ---");

        StudyGroupDTO group = StudyGroupBuilder
            .builder("Math Study", "MATH101")
            .build();

        System.out.println("  Name        : " + group.getName());
        System.out.println("  Course      : " + group.getCourseCode());
        System.out.println("  Capacity    : " + group.getMaxCapacity());
        System.out.println("  Privacy     : " + group.getPrivacy());
        System.out.println("  Tags count  : " + group.getTags().size());

        assertEquals("Math Study", group.getName(),
            "Name must match what was passed to builder()");
        assertEquals("MATH101", group.getCourseCode(),
            "Course code must match what was passed to builder()");
        assertEquals(10, group.getMaxCapacity(),
            "Default capacity must be 10 — if this fails, the default was changed in StudyGroupBuilder");
        assertEquals("PUBLIC", group.getPrivacy(),
            "Default privacy must be PUBLIC — if this fails, the default was changed in StudyGroupBuilder");
        assertTrue(group.getTags().isEmpty(),
            "Tags must be empty when no tags were added");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Full build sets all optional fields correctly")
    void testBuildFullGroup() {
        System.out.println("\n--- TEST: Full Group Build ---");

        LocalDateTime meetingTime = LocalDateTime.now().plusDays(3);

        StudyGroupDTO group = StudyGroupBuilder
            .builder("Physics 202", "PHYS202")
            .description("Weekly problem-solving sessions")
            .maxCapacity(15)
            .privateGroup()
            .addTag("difficult")
            .addTag("weekly")
            .meetingTime(meetingTime)
            .location("Room 3.24")
            .build();

        System.out.println("  Name        : " + group.getName());
        System.out.println("  Capacity    : " + group.getMaxCapacity());
        System.out.println("  Privacy     : " + group.getPrivacy());
        System.out.println("  Tags        : " + group.getTags());
        System.out.println("  Location    : " + group.getLocation());
        System.out.println("  Description : " + group.getDescription());

        assertEquals("Physics 202",                   group.getName());
        assertEquals("PHYS202",                       group.getCourseCode());
        assertEquals("Weekly problem-solving sessions", group.getDescription(),
            "Description must match what was set");
        assertEquals(15,                              group.getMaxCapacity(),
            "Capacity must be 15 as set");
        assertEquals("PRIVATE",                       group.getPrivacy(),
            "Privacy must be PRIVATE after calling privateGroup()");
        assertEquals(2,                               group.getTags().size(),
            "Exactly 2 tags must be present");
        assertTrue(group.getTags().contains("difficult"),
            "Tag 'difficult' must be present");
        assertTrue(group.getTags().contains("weekly"),
            "Tag 'weekly' must be present");
        assertEquals("Room 3.24",                     group.getLocation(),
            "Location must match what was set");
        assertEquals(meetingTime,                     group.getMeetingTime(),
            "Meeting time must match what was set");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Capacity below 2 throws IllegalArgumentException")
    void testCapacityTooLow() {
        System.out.println("\n--- TEST: Capacity Validation (too low) ---");
        System.out.println("  Attempting capacity = 1 (minimum is 2)...");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> StudyGroupBuilder.builder("Group", "CS101").maxCapacity(1),
            "Capacity of 1 must throw IllegalArgumentException"
        );

        System.out.println("  Exception message : " + ex.getMessage());
        assertEquals("Capacity must be between 2 and 50", ex.getMessage(),
            "Exception message must match exactly");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Capacity above 50 throws IllegalArgumentException")
    void testCapacityTooHigh() {
        System.out.println("\n--- TEST: Capacity Validation (too high) ---");
        System.out.println("  Attempting capacity = 51 (maximum is 50)...");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> StudyGroupBuilder.builder("Group", "CS101").maxCapacity(51),
            "Capacity of 51 must throw IllegalArgumentException"
        );

        System.out.println("  Exception message : " + ex.getMessage());
        assertEquals("Capacity must be between 2 and 50", ex.getMessage(),
            "Exception message must match exactly");

        System.out.println("  PASS");
    }
}
