package com.balazsholczer.flyweight;

public class Context {
    
    private final Flyweight flyweight;
    private final String extrinsicState;
    
    public Context(String intrinsicState, String extrinsicState) {
        this.flyweight = FlyweightFactory.getFlyweight(intrinsicState);
        this.extrinsicState = extrinsicState;
    }
    
    public void operation() {
        flyweight.operation(extrinsicState);
    }
}