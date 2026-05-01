package com.studysync.creational.factorymethod;

public class CreditCardProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        System.out.println("Processing $" + amount + " via Credit Card: " + accountInfo);
        return true;
    }
    @Override
    public String getProcessorName() { return "CREDIT_CARD"; }
}
