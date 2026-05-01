package com.studysync.creational.abstractfactory;

public class WindowsTextBox implements TextBox {
    private String text;
    @Override
    public void render() { System.out.println("Rendering Windows-style text box"); }
    @Override
    public void setText(String text) { this.text = text; }
    @Override
    public String getText() { return text; }
}
