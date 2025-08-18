package com.balazsholczer.composite;

public class Leaf implements Component {
    
    private final String name;
    
    public Leaf(String name) {
        this.name = name;
    }
    
    @Override
    public void operation() {
        System.out.println("Leaf: " + name + " operation");
    }
    
    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException("Cannot add to a leaf");
    }
    
    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf");
    }
    
    @Override
    public Component getChild(int index) {
        throw new UnsupportedOperationException("Leaf has no children");
    }
}