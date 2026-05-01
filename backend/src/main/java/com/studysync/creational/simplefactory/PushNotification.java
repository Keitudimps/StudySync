package com.studysync.creational.simplefactory;

public class PushNotification implements Notification {
    @Override
    public void send(String recipient, String message) {
        System.out.println("Sending PUSH to " + recipient + ": " + message);
    }
    @Override
    public String getType() { return "PUSH"; }
}
