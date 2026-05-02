package com.studysync.creational;

import com.studysync.creational.simplefactory.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {
    
    @Test
    void testCreateEmailNotification() {
        System.out.println("\n=== TEST: Create Email Notification ===");
        System.out.println("Creating EMAIL notification type...");
        
        Notification notification = NotificationFactory.createNotification("EMAIL");
        assertNotNull(notification);
        
        System.out.println("Notification type: " + notification.getType());
        assertEquals("EMAIL", notification.getType());
        
        System.out.println("Testing send method...");
        assertDoesNotThrow(() -> notification.send("test@example.com", "Hello"));
        
        System.out.println("✓ Email notification created and working");
    }
    
    @Test
    void testCreateSMSNotification() {
        System.out.println("\n=== TEST: Create SMS Notification ===");
        System.out.println("Creating SMS notification type...");
        
        Notification notification = NotificationFactory.createNotification("SMS");
        assertNotNull(notification);
        
        System.out.println("Notification type: " + notification.getType());
        assertEquals("SMS", notification.getType());
        
        System.out.println("✓ SMS notification created successfully");
    }
    
    @Test
    void testCreatePushNotification() {
        System.out.println("\n=== TEST: Create Push Notification ===");
        System.out.println("Creating PUSH notification type...");
        
        Notification notification = NotificationFactory.createNotification("PUSH");
        assertNotNull(notification);
        
        System.out.println("Notification type: " + notification.getType());
        assertEquals("PUSH", notification.getType());
        
        System.out.println("✓ Push notification created successfully");
    }
    
    @Test
    void testUnknownTypeThrowsException() {
        System.out.println("\n=== TEST: Unknown Type Throws Exception ===");
        System.out.println("Attempting to create notification with invalid type 'WHATSAPP'...");
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NotificationFactory.createNotification("WHATSAPP");
        });
        
        System.out.println("Exception caught: " + exception.getMessage());
        assertEquals("Unknown notification type: WHATSAPP", exception.getMessage());
        
        System.out.println("✓ Exception handling working correctly");
    }
    
    @Test
    void testCaseInsensitiveType() {
        System.out.println("\n=== TEST: Case Insensitive Type ===");
        System.out.println("Creating notification with lowercase type 'email'...");
        
        Notification notification = NotificationFactory.createNotification("email");
        
        System.out.println("Created type normalized to: " + notification.getType());
        assertEquals("EMAIL", notification.getType());
        
        System.out.println("✓ Case insensitive handling working correctly");
    }
}
