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

        System.out.println("Button type  : " + button.getClass().getSimpleName());
        System.out.println("TextBox type : " + textBox.getClass().getSimpleName());

        assertNotNull(button, "WindowsFactory must produce a non-null Button");
        assertNotNull(textBox, "WindowsFactory must produce a non-null TextBox");
        assertInstanceOf(WindowsButton.class, button,
                "WindowsFactory must produce a WindowsButton");
        assertInstanceOf(WindowsTextBox.class, textBox,
                "WindowsFactory must produce a WindowsTextBox");

        System.out.println("✓ PASS — Windows factory verified: correct component types created");
    }

    @Test
    void testMacFactoryCreatesMacComponents() {
        System.out.println("\n=== TEST: Mac Factory Creates Mac Components ===");
        System.out.println("Creating Mac GUI factory and components...");

        GUIFactory factory = new MacFactory();
        Button button = factory.createButton();
        TextBox textBox = factory.createTextBox();

        System.out.println("Button type  : " + button.getClass().getSimpleName());
        System.out.println("TextBox type : " + textBox.getClass().getSimpleName());

        assertNotNull(button, "MacFactory must produce a non-null Button");
        assertNotNull(textBox, "MacFactory must produce a non-null TextBox");
        assertInstanceOf(MacButton.class, button,
                "MacFactory must produce a MacButton");
        assertInstanceOf(MacTextBox.class, textBox,
                "MacFactory must produce a MacTextBox");

        System.out.println("✓ PASS — Mac factory verified: correct component types created");
    }

    @Test
    void testApplicationUIRendersWithoutError() {
        System.out.println("\n=== TEST: Application UI Renders Without Error ===");
        System.out.println("Initializing ApplicationUI with Windows factory...");

        GUIFactory factory = new WindowsFactory();
        ApplicationUI app = new ApplicationUI(factory);

        System.out.println("Calling renderUI()...");
        assertDoesNotThrow(app::renderUI,
                "ApplicationUI.renderUI() must not throw any exception");

        System.out.println("✓ PASS — ApplicationUI rendered successfully without errors");
    }

    @Test
    void testTextBoxStoresAndRetrievesText() {
        System.out.println("\n=== TEST: TextBox Stores and Retrieves Text ===");

        TextBox winTextBox = new WindowsFactory().createTextBox();
        System.out.println("Setting text on WindowsTextBox: 'Hello StudySync'");
        winTextBox.setText("Hello StudySync");
        System.out.println("Retrieved text: " + winTextBox.getText());
        assertEquals("Hello StudySync", winTextBox.getText(),
                "WindowsTextBox must store and return the text that was set");

        TextBox macTextBox = new MacFactory().createTextBox();
        System.out.println("Setting text on MacTextBox: 'Hello StudySync'");
        macTextBox.setText("Hello StudySync");
        System.out.println("Retrieved text: " + macTextBox.getText());
        assertEquals("Hello StudySync", macTextBox.getText(),
                "MacTextBox must store and return the text that was set");

        System.out.println("✓ PASS — both TextBox implementations store and retrieve text correctly");
    }

    @Test
    void testWindowsAndMacComponentsAreDistinctTypes() {
        System.out.println("\n=== TEST: Windows and Mac Components Are Distinct Types ===");
        System.out.println("Creating buttons and text boxes from both factories...");

        GUIFactory windowsFactory = new WindowsFactory();
        GUIFactory macFactory = new MacFactory();

        Button winBtn = windowsFactory.createButton();
        Button macBtn = macFactory.createButton();
        TextBox winBox = windowsFactory.createTextBox();
        TextBox macBox = macFactory.createTextBox();

        System.out.println("Windows button : " + winBtn.getClass().getSimpleName());
        System.out.println("Mac button     : " + macBtn.getClass().getSimpleName());
        System.out.println("Windows textbox: " + winBox.getClass().getSimpleName());
        System.out.println("Mac textbox    : " + macBox.getClass().getSimpleName());

        assertNotEquals(winBtn.getClass(), macBtn.getClass(),
                "Windows and Mac buttons must be different types");
        assertNotEquals(winBox.getClass(), macBox.getClass(),
                "Windows and Mac text boxes must be different types");

        System.out.println("✓ PASS — each platform has independent component implementations");
    }
}
