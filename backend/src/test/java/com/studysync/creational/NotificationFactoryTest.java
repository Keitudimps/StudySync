package com.studysync.creational;

import com.studysync.creational.simplefactory.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Simple Factory Pattern — NotificationFactory")
class NotificationFactoryTest {

    @Test
    @DisplayName("EMAIL type creates an EmailNotification that returns 'EMAIL' from getType()")
    void testCreateEmailNotification() {
        System.out.println("\n--- TEST: Create Email Notification ---");

        Notification notification = NotificationFactory.createNotification("EMAIL");
        System.out.println("Inserted Object: " + notification);

        assertNotNull(notification, "Factory must return a non-null object");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("EMAIL", notification.getType(),
                "getType() must return exactly 'EMAIL' — if this fails, EmailNotification.getType() was changed");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  Type returned : " + notification.getType());
        System.out.println("  Class created : " + notification.getClass().getSimpleName());

        // Verify send() actually executes without throwing
        assertDoesNotThrow(() -> notification.send("student@uni.ac.za", "Test message"),
                "send() must not throw any exception");

        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  send() result : completed without exception");
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("SMS type creates an SMSNotification that returns 'SMS' from getType()")
    void testCreateSMSNotification() {
        System.out.println("\n--- TEST: Create SMS Notification ---");

        Notification notification = NotificationFactory.createNotification("SMS");
        System.out.println("Inserted Object: " + notification);

        assertNotNull(notification, "Factory must return a non-null object");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(SMSNotification.class, notification,
                "Factory must create an SMSNotification instance — if this fails, the factory switch case was changed");
        System.out.println("Assertion passed: expected result matches actual result.");

        assertEquals("SMS", notification.getType(),
                "getType() must return exactly 'SMS'");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  Type returned : " + notification.getType());
        System.out.println("  Instance of   : " + notification.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("PUSH type creates a PushNotification that returns 'PUSH' from getType()")
    void testCreatePushNotification() {
        System.out.println("\n--- TEST: Create Push Notification ---");

        Notification notification = NotificationFactory.createNotification("PUSH");
        System.out.println("Inserted Object: " + notification);

        assertNotNull(notification, "Factory must return a non-null object");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertInstanceOf(PushNotification.class, notification,
                "Factory must create a PushNotification instance");
        System.out.println("Assertion passed: expected result matches actual result.");

        assertEquals("PUSH", notification.getType(),
                "getType() must return exactly 'PUSH'");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  Type returned : " + notification.getType());
        System.out.println("  Instance of   : " + notification.getClass().getSimpleName());
        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Unknown type throws IllegalArgumentException with correct message")
    void testUnknownTypeThrowsException() {
        System.out.println("\n--- TEST: Unknown Type Throws Exception ---");

        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> NotificationFactory.createNotification("WHATSAPP"),
            "Factory must throw IllegalArgumentException for unknown types"
        );

        System.out.println("  Exception type    : " + ex.getClass().getSimpleName());
        System.out.println("  Exception message : " + ex.getMessage());

        assertEquals("Unknown notification type: WHATSAPP", ex.getMessage(),
                "Exception message must match exactly — if this fails, the error message format was changed");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Lowercase 'email' is treated the same as 'EMAIL' (case-insensitive)")
    void testCaseInsensitiveType() {
        System.out.println("\n--- TEST: Case-Insensitive Input ---");

        Notification lower = NotificationFactory.createNotification("email");
        System.out.println("Inserted Object: " + lower);
        Notification upper = NotificationFactory.createNotification("EMAIL");
        System.out.println("Inserted Object: " + upper);

        assertNotNull(lower, "Lowercase 'email' must produce a non-null result");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals(lower.getType(), upper.getType(),

                "Both 'email' and 'EMAIL' must produce a notification with the same getType() value — " +
                        "if this fails, toUpperCase() was removed from the factory");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  'email' → getType() : " + lower.getType());
        System.out.println("  'EMAIL' → getType() : " + upper.getType());
        System.out.println("  Both match          : " + lower.getType().equals(upper.getType()));
        System.out.println("  PASS");
    }
}