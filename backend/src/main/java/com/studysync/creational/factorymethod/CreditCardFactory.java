package com.studysync.creational.factorymethod;

public class CreditCardFactory extends PaymentProcessorFactory {
    @Override
    public PaymentProcessor createProcessor() {
        return new CreditCardProcessor();
    }
}
