package com.studysync.creational.factorymethod;

public abstract class PaymentProcessorFactory {
    public abstract PaymentProcessor createProcessor();
    
    public boolean processPayment(double amount, String accountInfo) {
        PaymentProcessor processor = createProcessor();
        System.out.print("[" + processor.getProcessorName() + "] ");
        return processor.processPayment(amount, accountInfo);
    }
}
