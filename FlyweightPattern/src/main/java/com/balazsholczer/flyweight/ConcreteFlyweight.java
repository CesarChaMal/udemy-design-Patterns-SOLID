package com.balazsholczer.flyweight;

public class ConcreteFlyweight implements Flyweight {
    
    private final String intrinsicState;
    
    public ConcreteFlyweight(String intrinsicState) {
        this.intrinsicState = intrinsicState;
    }
    
    @Override
    public void operation(String extrinsicState) {
        System.out.println("Flyweight: intrinsic=" + intrinsicState + ", extrinsic=" + extrinsicState);
    }
    
    public String getIntrinsicState() {
        return intrinsicState;
    }
}