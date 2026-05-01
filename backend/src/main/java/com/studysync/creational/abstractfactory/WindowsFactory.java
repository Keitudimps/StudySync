package com.studysync.creational.abstractfactory;

public class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() { return new WindowsButton(); }
    @Override
    public TextBox createTextBox() { return new WindowsTextBox(); }
}
