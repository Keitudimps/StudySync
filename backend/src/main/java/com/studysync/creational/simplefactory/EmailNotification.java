package com.studysync.creational.simplefactory;

public class EmailNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending EMAIL to " + recipient + ": " + message);
    }
    @Override
    public String getType() { return "EMAIL"; }
}
