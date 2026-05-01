package com.studysync.creational.factorymethod;

public class CryptoProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount, String accountInfo) {
        System.out.println("Processing $" + amount + " via Crypto wallet: " + accountInfo);
        return true;
    }
    @Override
    public String getProcessorName() { return "CRYPTO"; }
}
