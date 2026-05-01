package com.studysync.creational.abstractfactory;

public class MacTextBox implements TextBox {
    private String text;
    @Override
    public void render() { System.out.println("Rendering Mac-style text box"); }
    @Override
    public void setText(String text) { this.text = text; }
    @Override
    public String getText() { return text; }
}
