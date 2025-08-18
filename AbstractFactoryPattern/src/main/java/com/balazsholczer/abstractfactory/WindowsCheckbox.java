package com.balazsholczer.abstractfactory;

public class WindowsCheckbox implements Checkbox {
    
    @Override
    public void render() {
        System.out.println("Rendering Windows checkbox");
    }
    
    @Override
    public void toggle() {
        System.out.println("Windows checkbox toggled");
    }
}