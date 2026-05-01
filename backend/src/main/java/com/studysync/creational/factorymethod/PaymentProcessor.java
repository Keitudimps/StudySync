package com.studysync.creational.factorymethod;

public interface PaymentProcessor {
    boolean processPayment(double amount, String accountInfo);
    String getProcessorName();
}
