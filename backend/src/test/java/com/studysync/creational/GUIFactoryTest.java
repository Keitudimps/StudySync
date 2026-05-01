package com.studysync.creational;

import com.studysync.creational.abstractfactory.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GUIFactoryTest {
    
    @Test
    void testWindowsFactoryCreatesWindowsComponents() {
        GUIFactory factory = new WindowsFactory();
        Button button = factory.createButton();
        TextBox textBox = factory.createTextBox();
        
        assertTrue(button instanceof WindowsButton);
        assertTrue(textBox instanceof WindowsTextBox);
    }
    
    @Test
    void testMacFactoryCreatesMacComponents() {
        GUIFactory factory = new MacFactory();
        Button button = factory.createButton();
        TextBox textBox = factory.createTextBox();
        
        assertTrue(button instanceof MacButton);
        assertTrue(textBox instanceof MacTextBox);
    }
    
    @Test
    void testApplicationUIRendersWithoutError() {
        GUIFactory factory = new WindowsFactory();
        ApplicationUI app = new ApplicationUI(factory);
        assertDoesNotThrow(() -> app.renderUI());
    }
    
    @Test
    void testComponentsAreIndependent() {
        GUIFactory windowsFactory = new WindowsFactory();
        GUIFactory macFactory = new MacFactory();
        
        Button winBtn = windowsFactory.createButton();
        Button macBtn = macFactory.createButton();
        
        assertNotEquals(winBtn.getClass(), macBtn.getClass());
    }
}
