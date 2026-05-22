package com.studysync.creational;

import com.studysync.creational.factorymethod.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Factory Method Pattern — PaymentProcessorFactory subclasses")
class PaymentProcessorFactoryTest {

    @Test
    @DisplayName("CreditCardFactory creates a processor named 'CREDIT_CARD' that processes payments")
    void testCreditCardProcessor() {
        System.out.println("\n--- TEST: Credit Card Processor ---");

        PaymentProcessorFactory factory   = new CreditCardFactory();
        PaymentProcessor        processor = factory.createProcessor();

        System.out.println("  Processor class : " + processor.getClass().getSimpleName());
        System.out.println("  Processor name  : " + processor.getProcessorName());

        assertInstanceOf(CreditCardProcessor.class, processor, "CreditCardFactory must create a CreditCardProcessor — " +
                "if this fails, createProcessor() was changed to return a different type");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("CREDIT_CARD", processor.getProcessorName(),"Processor name must be 'CREDIT_CARD' — if this fails, getProcessorName() was changed");
        System.out.println("Assertion passed: expected result matches actual result.");


        boolean result = processor.processPayment(50.00, "4111-1111-1111-1111");
        System.out.println("  processPayment result : " + result);
        assertTrue(result, "processPayment() must return true for a valid payment — if this fails, the method now returns false");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  PASS");
    }

    @Test
    @DisplayName("PayPalFactory creates a processor named 'PAYPAL' that processes payments")
    void testPayPalProcessor() {
        System.out.println("\n--- TEST: PayPal Processor ---");

        PaymentProcessorFactory factory   = new PayPalFactory();
        PaymentProcessor        processor = factory.createProcessor();

        System.out.println("  Processor class : " + processor.getClass().getSimpleName());
        System.out.println("  Processor name  : " + processor.getProcessorName());

        assertInstanceOf(PayPalProcessor.class, processor,   "PayPalFactory must create a PayPalProcessor");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("PAYPAL", processor.getProcessorName(),  "Processor name must be 'PAYPAL'");
        System.out.println("Assertion passed: expected result matches actual result.");


        boolean result = processor.processPayment(75.50, "user@paypal.com");
        System.out.println("  processPayment result : " + result);
        assertTrue(result, "processPayment() must return true");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("CryptoFactory creates a processor named 'CRYPTO' that processes payments")
    void testCryptoProcessor() {
        System.out.println("\n--- TEST: Crypto Processor ---");

        PaymentProcessorFactory factory   = new CryptoFactory();
        PaymentProcessor        processor = factory.createProcessor();

        System.out.println("  Processor class : " + processor.getClass().getSimpleName());
        System.out.println("  Processor name  : " + processor.getProcessorName());

        assertInstanceOf(CryptoProcessor.class, processor, "CryptoFactory must create a CryptoProcessor");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertEquals("CRYPTO", processor.getProcessorName(),   "Processor name must be 'CRYPTO'");
        System.out.println("Assertion passed: expected result matches actual result.");


        boolean result = processor.processPayment(0.01, "0xABC123...");
        System.out.println("  processPayment result : " + result);
        assertTrue(result, "processPayment() must return true");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Template method on factory delegates to the correct processor")
    void testTemplateMethodDelegation() {
        System.out.println("\n--- TEST: Template Method Delegates to Correct Processor ---");

        // The factory's processPayment() is the template method
        // It must internally call createProcessor() and delegate to it
        PaymentProcessorFactory creditFactory = new CreditCardFactory();
        PaymentProcessorFactory paypalFactory = new PayPalFactory();

        System.out.println("  CreditCardFactory.processPayment(100.00, ...) ...");
        boolean creditResult = creditFactory.processPayment(100.00, "card-number");
        System.out.println("  Result : " + creditResult);
        assertTrue(creditResult,    "CreditCardFactory template method must return true");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PayPalFactory.processPayment(200.00, ...) ...");
        boolean paypalResult = paypalFactory.processPayment(200.00, "email@paypal.com");
        System.out.println("  Result : " + paypalResult);
        assertTrue(paypalResult,    "PayPalFactory template method must return true");
        System.out.println("Assertion passed: expected result matches actual result.");


        System.out.println("  PASS");
    }
}