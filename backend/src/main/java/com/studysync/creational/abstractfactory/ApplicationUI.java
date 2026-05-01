package com.studysync.creational.abstractfactory;

public class ApplicationUI {
    private final Button button;
    private final TextBox textBox;
    
    public ApplicationUI(GUIFactory factory) {
        this.button = factory.createButton();
        this.textBox = factory.createTextBox();
    }
    
    public void renderUI() {
        button.render();
        textBox.render();
    }
}
