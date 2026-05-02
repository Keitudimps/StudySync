package com.studysync.creational;

import com.studysync.creational.factorymethod.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorFactoryTest {
    
    @Test
    void testCreditCardProcessor() {
        System.out.println("\n=== TEST: Credit Card Processor ===");
        System.out.println("Creating CreditCardFactory...");
        
        PaymentProcessorFactory factory = new CreditCardFactory();
        PaymentProcessor processor = factory.createProcessor();
        
        System.out.println("Processor name: " + processor.getProcessorName());
        assertEquals("CREDIT_CARD", processor.getProcessorName());
        
        System.out.println("Processing payment of $50.00...");
        assertTrue(processor.processPayment(50.00, "4111-1111-1111-1111"));
        
        System.out.println("✓ Credit card processor working correctly");
    }
    
    @Test
    void testPayPalProcessor() {
        System.out.println("\n=== TEST: PayPal Processor ===");
        System.out.println("Creating PayPalFactory...");
        
        PaymentProcessorFactory factory = new PayPalFactory();
        PaymentProcessor processor = factory.createProcessor();
        
        System.out.println("Processor name: " + processor.getProcessorName());
        assertEquals("PAYPAL", processor.getProcessorName());
        
        System.out.println("Processing payment of $75.50...");
        assertTrue(processor.processPayment(75.50, "user@paypal.com"));
        
        System.out.println("✓ PayPal processor working correctly");
    }
    
    @Test
    void testCryptoProcessor() {
        System.out.println("\n=== TEST: Crypto Processor ===");
        System.out.println("Creating CryptoFactory...");
        
        PaymentProcessorFactory factory = new CryptoFactory();
        PaymentProcessor processor = factory.createProcessor();
        
        System.out.println("Processor name: " + processor.getProcessorName());
        assertEquals("CRYPTO", processor.getProcessorName());
        
        System.out.println("Processing crypto payment of 0.01 BTC...");
        assertTrue(processor.processPayment(0.01, "0xABC123..."));
        
        System.out.println("✓ Crypto processor working correctly");
    }
    
    @Test
    void testTemplateMethod() {
        System.out.println("\n=== TEST: Template Method Pattern ===");
        System.out.println("Testing factory template method...");
        
        PaymentProcessorFactory factory = new CreditCardFactory();
        System.out.println("Processing $100.00 payment...");
        
        boolean result = factory.processPayment(100.00, "card-number");
        assertTrue(result);
        
        System.out.println("✓ Template method pattern working correctly");
    }
}
