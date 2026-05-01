package com.studysync.creational.abstractfactory;

public class WindowsButton implements Button {
    @Override
    public void render() { System.out.println("Rendering Windows-style button"); }
    @Override
    public void onClick() { System.out.println("Windows button clicked"); }
}
