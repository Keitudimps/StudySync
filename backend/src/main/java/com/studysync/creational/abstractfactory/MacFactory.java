package com.studysync.creational.abstractfactory;

public class MacFactory implements GUIFactory {
    @Override
    public Button createButton() { return new MacButton(); }
    @Override
    public TextBox createTextBox() { return new MacTextBox(); }
}
