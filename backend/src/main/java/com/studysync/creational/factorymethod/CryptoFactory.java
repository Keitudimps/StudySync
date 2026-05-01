package com.studysync.creational.factorymethod;

public class CryptoFactory extends PaymentProcessorFactory {
    @Override
    public PaymentProcessor createProcessor() {
        return new CryptoProcessor();
    }
}
