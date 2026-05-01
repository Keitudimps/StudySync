package com.studysync.creational.simplefactory;

public interface Notification {
    void send(String recipient, String message);
    String getType();
}
