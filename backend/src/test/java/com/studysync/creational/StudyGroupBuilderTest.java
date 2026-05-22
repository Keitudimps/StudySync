package com.studysync.creational;

import com.studysync.creational.builder.*;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Builder Pattern — StudyGroupBuilder")
class StudyGroupBuilderTest {

    @Test
    @DisplayName("Minimal build uses correct defaults")
    void testBuildMinimalGroup() {

        System.out.println("\n--- TEST: Minimal Group Build ---");

        StudyGroupDTO group = StudyGroupBuilder
                .builder("Math Study", "MATH101")
                .build();

        System.out.println("Name: " + group.getName());
        System.out.println("Course: " + group.getCourseCode());
        System.out.println("Capacity: " + group.getMaxCapacity());
        System.out.println("Privacy: " + group.getPrivacy());

        assertEquals("Math Study", group.getName(),
                "Name must match what was passed to builder()");
        System.out.println("Assertion passed: name matches.");

        assertEquals("MATH101", group.getCourseCode(),
                "Course code must match");
        System.out.println("Assertion passed: course code matches.");

        assertEquals(10, group.getMaxCapacity(),
                "Default capacity must be 10");
        System.out.println("Assertion passed: default capacity correct.");

        assertEquals("PUBLIC", group.getPrivacy(),
                "Default privacy must be PUBLIC");
        System.out.println("Assertion passed: privacy correct.");

        assertTrue(group.getTags().isEmpty(),
                "Tags must be empty");
        System.out.println("Assertion passed: tags are empty.");

        System.out.println("PASS");
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

        assertEquals("Physics 202", group.getName());
        System.out.println("Assertion passed: name correct.");

        assertEquals("PHYS202", group.getCourseCode());
        System.out.println("Assertion passed: course code correct.");

        assertEquals("Weekly problem-solving sessions", group.getDescription());
        System.out.println("Assertion passed: description correct.");

        assertEquals(15, group.getMaxCapacity());
        System.out.println("Assertion passed: capacity correct.");

        assertEquals("PRIVATE", group.getPrivacy());
        System.out.println("Assertion passed: privacy correct.");

        assertEquals(2, group.getTags().size());
        System.out.println("Assertion passed: tag count correct.");

        assertTrue(group.getTags().contains("difficult"));
        System.out.println("Assertion passed: difficult tag exists.");

        assertTrue(group.getTags().contains("weekly"));
        System.out.println("Assertion passed: weekly tag exists.");

        assertEquals("Room 3.24", group.getLocation());
        System.out.println("Assertion passed: location correct.");

        assertEquals(meetingTime, group.getMeetingTime());
        System.out.println("Assertion passed: meeting time correct.");

        System.out.println("PASS");
    }

    @Test
    @DisplayName("Capacity below 2 throws IllegalArgumentException")
    void testCapacityTooLow() {

        System.out.println("\n--- TEST: Capacity Too Low ---");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> StudyGroupBuilder.builder("Group", "CS101").maxCapacity(1)
        );

        System.out.println("Exception message: " + ex.getMessage());

        assertEquals("Capacity must be between 2 and 50", ex.getMessage());
        System.out.println("Assertion passed: correct exception message.");

        System.out.println("PASS");
    }

    @Test
    @DisplayName("Capacity above 50 throws IllegalArgumentException")
    void testCapacityTooHigh() {

        System.out.println("\n--- TEST: Capacity Too High ---");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> StudyGroupBuilder.builder("Group", "CS101").maxCapacity(51)
        );

        System.out.println("Exception message: " + ex.getMessage());

        assertEquals("Capacity must be between 2 and 50", ex.getMessage());
        System.out.println("Assertion passed: correct exception message.");

        System.out.println("PASS");
    }
}