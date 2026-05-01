package com.studysync.creational;

import com.studysync.creational.factorymethod.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorFactoryTest {
    
    @Test
    void testCreditCardProcessor() {
        PaymentProcessorFactory factory = new CreditCardFactory();
        PaymentProcessor processor = factory.createProcessor();
        assertEquals("CREDIT_CARD", processor.getProcessorName());
        assertTrue(processor.processPayment(50.00, "4111-1111-1111-1111"));
    }
    
    @Test
    void testPayPalProcessor() {
        PaymentProcessorFactory factory = new PayPalFactory();
        PaymentProcessor processor = factory.createProcessor();
        assertEquals("PAYPAL", processor.getProcessorName());
        assertTrue(processor.processPayment(75.50, "user@paypal.com"));
    }
    
    @Test
    void testCryptoProcessor() {
        PaymentProcessorFactory factory = new CryptoFactory();
        PaymentProcessor processor = factory.createProcessor();
        assertEquals("CRYPTO", processor.getProcessorName());
        assertTrue(processor.processPayment(0.01, "0xABC123..."));
    }
    
    @Test
    void testTemplateMethod() {
        PaymentProcessorFactory factory = new CreditCardFactory();
        boolean result = factory.processPayment(100.00, "card-number");
        assertTrue(result);
    }
}
