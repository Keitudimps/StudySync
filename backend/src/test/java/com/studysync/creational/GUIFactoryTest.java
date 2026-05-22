package com.studysync.creational;

import com.studysync.creational.abstractfactory.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Abstract Factory Pattern — GUIFactory, WindowsFactory, MacFactory")
class GUIFactoryTest {

    @Test
    @DisplayName("WindowsFactory creates WindowsButton and WindowsTextBox instances")
    void testWindowsFactoryCreatesWindowsComponents() {
        System.out.println("\n--- TEST: Windows Factory Creates Windows Components ---");

        GUIFactory factory = new WindowsFactory();
        Button button   = factory.createButton();
        TextBox textBox = factory.createTextBox();

        System.out.println("  Button class  : " + button.getClass().getSimpleName());
        System.out.println("  TextBox class : " + textBox.getClass().getSimpleName());

        assertInstanceOf(WindowsButton.class, button,"WindowsFactory.createButton() must return a WindowsButton — " +
                "if this fails, the factory was changed to return a different type");
        System.out.println("Assertion passed: expected result matches actual result.");

        assertInstanceOf(WindowsTextBox.class, textBox,"WindowsFactory.createTextBox() must return a WindowsTextBox");
        System.out.println("Assertion passed: expected result matches actual result.");


        // Verify render() executes without throwing
        assertDoesNotThrow(button::render,   "WindowsButton.render() must not throw");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertDoesNotThrow(textBox::render,  "WindowsTextBox.render() must not throw");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("MacFactory creates MacButton and MacTextBox instances")
    void testMacFactoryCreatesMacComponents() {
        System.out.println("\n--- TEST: Mac Factory Creates Mac Components ---");

        GUIFactory factory = new MacFactory();
        Button button   = factory.createButton();
        TextBox textBox = factory.createTextBox();

        System.out.println("  Button class  : " + button.getClass().getSimpleName());
        System.out.println("  TextBox class : " + textBox.getClass().getSimpleName());

        assertInstanceOf(MacButton.class, button,"MacFactory.createButton() must return a MacButton — " +
                "if this fails, the factory was changed to return a different type");
        System.out.println("Assertion passed: expected result matches actual result.");

        assertInstanceOf(MacTextBox.class, textBox,      "MacFactory.createTextBox() must return a MacTextBox");
        System.out.println("Assertion passed: expected result matches actual result.");


        assertDoesNotThrow(button::render,  "MacButton.render() must not throw");
        System.out.println("Assertion passed: expected result matches actual result.");
        assertDoesNotThrow(textBox::render, "MacTextBox.render() must not throw");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("Windows and Mac factories produce different component classes")
    void testFactoriesProduceDifferentComponents() {
        System.out.println("\n--- TEST: Factories Produce Independent Component Types ---");

        GUIFactory winFactory = new WindowsFactory();
        GUIFactory macFactory = new MacFactory();

        Button winBtn = winFactory.createButton();
        System.out.println("Inserted Object: " + winBtn);
        Button macBtn = macFactory.createButton();
        System.out.println("Inserted Object: " + macBtn);
        TextBox winBox = winFactory.createTextBox();
        System.out.println("Inserted Object: " + winBox);
        TextBox macBox = macFactory.createTextBox();
        System.out.println("Inserted Object: " + macBox);

        System.out.println("  Windows button : " + winBtn.getClass().getSimpleName());
        System.out.println("  Mac button     : " + macBtn.getClass().getSimpleName());
        System.out.println("  Windows textbox: " + winBox.getClass().getSimpleName());
        System.out.println("  Mac textbox    : " + macBox.getClass().getSimpleName());

        assertNotEquals(winBtn.getClass(), macBtn.getClass(), "Windows and Mac buttons must be different classes — " +
                "if this fails, both factories return the same button type");
        System.out.println("Assertion passed: expected result matches actual result.");

        assertNotEquals(winBox.getClass(), macBox.getClass(),    "Windows and Mac textboxes must be different classes");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PASS");
    }

    @Test
    @DisplayName("ApplicationUI renders all components without throwing an exception")
    void testApplicationUIRendersWithoutError() {
        System.out.println("\n--- TEST: ApplicationUI Renders Without Error ---");

        GUIFactory winFactory = new WindowsFactory();
        GUIFactory macFactory = new MacFactory();

        ApplicationUI winApp = new ApplicationUI(winFactory);
        ApplicationUI macApp = new ApplicationUI(macFactory);

        System.out.println("  Rendering Windows UI...");
        assertDoesNotThrow(winApp::renderUI,   "ApplicationUI.renderUI() with WindowsFactory must not throw");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  Rendering Mac UI...");
        assertDoesNotThrow(macApp::renderUI, "ApplicationUI.renderUI() with MacFactory must not throw");
        System.out.println("Assertion passed: expected result matches actual result.");

        System.out.println("  PASS");
    }
}