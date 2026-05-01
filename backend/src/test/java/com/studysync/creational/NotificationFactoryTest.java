package com.studysync.creational;

import com.studysync.creational.simplefactory.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {
    
    @Test
    void testCreateEmailNotification() {
        Notification notification = NotificationFactory.createNotification("EMAIL");
        assertNotNull(notification);
        assertEquals("EMAIL", notification.getType());
        assertDoesNotThrow(() -> notification.send("test@example.com", "Hello"));
    }
    
    @Test
    void testCreateSMSNotification() {
        Notification notification = NotificationFactory.createNotification("SMS");
        assertNotNull(notification);
        assertEquals("SMS", notification.getType());
    }
    
    @Test
    void testCreatePushNotification() {
        Notification notification = NotificationFactory.createNotification("PUSH");
        assertNotNull(notification);
        assertEquals("PUSH", notification.getType());
    }
    
    @Test
    void testUnknownTypeThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NotificationFactory.createNotification("WHATSAPP");
        });
        assertEquals("Unknown notification type: WHATSAPP", exception.getMessage());
    }
    
    @Test
    void testCaseInsensitiveType() {
        Notification notification = NotificationFactory.createNotification("email");
        assertEquals("EMAIL", notification.getType());
    }
}
