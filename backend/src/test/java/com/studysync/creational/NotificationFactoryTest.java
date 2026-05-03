package com.studysync.creational;

import com.studysync.creational.simplefactory.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void testCreateEmailNotification() {
        System.out.println("\n=== TEST: Create Email Notification ===");
        System.out.println("Creating EMAIL notification via factory...");

        Notification notification = NotificationFactory.createNotification("EMAIL");

        System.out.println("Concrete type  : " + notification.getClass().getSimpleName());
        System.out.println("Reported type  : " + notification.getType());

        assertNotNull(notification, "Factory must return a non-null EMAIL notification");
        assertInstanceOf(EmailNotification.class, notification,
                "Factory must return an EmailNotification for type 'EMAIL'");
        assertEquals("EMAIL", notification.getType(),
                "Email notification must report type as 'EMAIL'");

        System.out.println("Calling send()...");
        assertDoesNotThrow(() -> notification.send("test@example.com", "Session starts at 10am"),
                "EmailNotification.send() must not throw any exception");

        System.out.println("✓ PASS — email notification created and send() executed successfully");
    }

    @Test
    void testCreateSMSNotification() {
        System.out.println("\n=== TEST: Create SMS Notification ===");
        System.out.println("Creating SMS notification via factory...");

        Notification notification = NotificationFactory.createNotification("SMS");

        System.out.println("Concrete type  : " + notification.getClass().getSimpleName());
        System.out.println("Reported type  : " + notification.getType());

        assertNotNull(notification, "Factory must return a non-null SMS notification");
        assertInstanceOf(SMSNotification.class, notification,
                "Factory must return an SMSNotification for type 'SMS'");
        assertEquals("SMS", notification.getType(),
                "SMS notification must report type as 'SMS'");

        System.out.println("Calling send()...");
        assertDoesNotThrow(() -> notification.send("+27821234567", "Your session is in 1 hour"),
                "SMSNotification.send() must not throw any exception");

        System.out.println("✓ PASS — SMS notification created and send() executed successfully");
    }

    @Test
    void testCreatePushNotification() {
        System.out.println("\n=== TEST: Create Push Notification ===");
        System.out.println("Creating PUSH notification via factory...");

        Notification notification = NotificationFactory.createNotification("PUSH");

        System.out.println("Concrete type  : " + notification.getClass().getSimpleName());
        System.out.println("Reported type  : " + notification.getType());

        assertNotNull(notification, "Factory must return a non-null PUSH notification");
        assertInstanceOf(PushNotification.class, notification,
                "Factory must return a PushNotification for type 'PUSH'");
        assertEquals("PUSH", notification.getType(),
                "Push notification must report type as 'PUSH'");

        System.out.println("Calling send()...");
        assertDoesNotThrow(() -> notification.send("device-token-abc", "New session scheduled"),
                "PushNotification.send() must not throw any exception");

        System.out.println("✓ PASS — push notification created and send() executed successfully");
    }

    @Test
    void testUnknownTypeThrowsException() {
        System.out.println("\n=== TEST: Unknown Type Throws Exception ===");
        System.out.println("Attempting to create notification with invalid type 'WHATSAPP'...");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> NotificationFactory.createNotification("WHATSAPP"),
                "Factory must throw IllegalArgumentException for unknown type 'WHATSAPP'"
        );

        System.out.println("Exception caught: " + exception.getMessage());

        assertEquals("Unknown notification type: WHATSAPP", exception.getMessage(),
                "Exception message must identify the unknown type");

        System.out.println("✓ PASS — exception handling working correctly");
    }

    @Test
    void testCaseInsensitiveType() {
        System.out.println("\n=== TEST: Case Insensitive Type ===");
        System.out.println("Creating notification with lowercase 'email'...");

        Notification lower = NotificationFactory.createNotification("email");
        System.out.println("Type from 'email'  : " + lower.getType());

        System.out.println("Creating notification with mixed-case 'Email'...");
        Notification mixed = NotificationFactory.createNotification("Email");
        System.out.println("Type from 'Email'  : " + mixed.getType());

        assertNotNull(lower, "Factory must handle lowercase type 'email'");
        assertNotNull(mixed, "Factory must handle mixed-case type 'Email'");
        assertEquals("EMAIL", lower.getType(),
                "Notification created with 'email' must normalise type to 'EMAIL'");
        assertEquals("EMAIL", mixed.getType(),
                "Notification created with 'Email' must normalise type to 'EMAIL'");

        System.out.println("✓ PASS — case insensitive handling working correctly");
    }
}
