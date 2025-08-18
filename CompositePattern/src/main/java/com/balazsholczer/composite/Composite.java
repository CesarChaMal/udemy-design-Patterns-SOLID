package com.balazsholczer.composite;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
    
    private final String name;
    private final List<Component> children = new ArrayList<>();
    
    public Composite(String name) {
        this.name = name;
    }
    
    @Override
    public void operation() {
        System.out.println("Composite: " + name + " operation");
        for (Component child : children) {
            child.operation();
        }
    }
    
    @Override
    public void add(Component component) {
        children.add(component);
    }
    
    @Override
    public void remove(Component component) {
        children.remove(component);
    }
    
    @Override
    public Component getChild(int index) {
        return children.get(index);
    }
}