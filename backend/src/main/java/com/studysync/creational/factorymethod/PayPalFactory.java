package com.studysync.creational.factorymethod;

public class PayPalFactory extends PaymentProcessorFactory {
    @Override
    public PaymentProcessor createProcessor() {
        return new PayPalProcessor();
    }
}
