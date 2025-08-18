package com.balazsholczer.abstractfactory;

public class WindowsButton implements Button {
    
    @Override
    public void render() {
        System.out.println("Rendering Windows button");
    }
    
    @Override
    public void onClick() {
        System.out.println("Windows button clicked");
    }
}