package com.studysync.creational;

import com.studysync.creational.abstractfactory.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GUIFactoryTest {
    
    @Test
    void testWindowsFactoryCreatesWindowsComponents() {
        System.out.println("\n=== TEST: Windows Factory Creates Windows Components ===");
        System.out.println("Creating Windows GUI factory and components...");
        
        GUIFactory factory = new WindowsFactory();
        Button button = factory.createButton();
        TextBox textBox = factory.createTextBox();
        
        System.out.println("Button type: " + button.getClass().getSimpleName());
        System.out.println("TextBox type: " + textBox.getClass().getSimpleName());
        
        assertTrue(button instanceof WindowsButton);
        assertTrue(textBox instanceof WindowsTextBox);
        
        System.out.println("✓ Windows factory verified: correct component types created");
    }
    
    @Test
    void testMacFactoryCreatesMacComponents() {
        System.out.println("\n=== TEST: Mac Factory Creates Mac Components ===");
        System.out.println("Creating Mac GUI factory and components...");
        
        GUIFactory factory = new MacFactory();
        Button button = factory.createButton();
        TextBox textBox = factory.createTextBox();
        
        System.out.println("Button type: " + button.getClass().getSimpleName());
        System.out.println("TextBox type: " + textBox.getClass().getSimpleName());
        
        assertTrue(button instanceof MacButton);
        assertTrue(textBox instanceof MacTextBox);
        
        System.out.println("✓ Mac factory verified: correct component types created");
    }
    
    @Test
    void testApplicationUIRendersWithoutError() {
        System.out.println("\n=== TEST: Application UI Renders Without Error ===");
        System.out.println("Initializing ApplicationUI with Windows factory...");
        
        GUIFactory factory = new WindowsFactory();
        ApplicationUI app = new ApplicationUI(factory);
        
        System.out.println("Rendering UI...");
        assertDoesNotThrow(() -> app.renderUI());
        
        System.out.println("✓ ApplicationUI rendered successfully without errors");
    }
    
    @Test
    void testComponentsAreIndependent() {
        System.out.println("\n=== TEST: Platform Components Are Independent ===");
        System.out.println("Creating buttons from Windows and Mac factories...");
        
        GUIFactory windowsFactory = new WindowsFactory();
        GUIFactory macFactory = new MacFactory();
        
        Button winBtn = windowsFactory.createButton();
        Button macBtn = macFactory.createButton();
        
        System.out.println("Windows button: " + winBtn.getClass().getSimpleName());
        System.out.println("Mac button: " + macBtn.getClass().getSimpleName());
        
        assertNotEquals(winBtn.getClass(), macBtn.getClass());
        
        System.out.println("✓ Components verified: each platform has independent implementations");
    }
}
