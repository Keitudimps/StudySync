package com.studysync.creational.factorymethod;

public class PayPalProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        System.out.println("Processing $" + amount + " via PayPal: " + accountInfo);
        return true;
    }
    @Override
    public String getProcessorName() { return "PAYPAL"; }
}
