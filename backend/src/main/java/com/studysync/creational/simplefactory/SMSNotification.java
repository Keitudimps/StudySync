package com.studysync.creational.simplefactory;

public class SMSNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
    @Override
    public String getType() { return "SMS"; }
}
