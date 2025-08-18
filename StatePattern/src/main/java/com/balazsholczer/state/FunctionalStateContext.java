package com.balazsholczer.state;

import java.util.function.Consumer;

public class FunctionalStateContext {
    
    private Consumer<FunctionalStateContext> currentState;
    private String stateName;
    
    public static final Consumer<FunctionalStateContext> ON_STATE = context -> {
        System.out.println("This is on state...");
        context.stateName = "On state...";
    };
    
    public static final Consumer<FunctionalStateContext> OFF_STATE = context -> {
        System.out.println("This is off state...");
        context.stateName = "Off state...";
    };
    
    public void setState(Consumer<FunctionalStateContext> state) {
        this.currentState = state;
    }
    
    public void doAction() {
        if (currentState != null) {
            currentState.accept(this);
        }
    }
    
    public String getStateName() {
        return stateName;
    }
    
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}