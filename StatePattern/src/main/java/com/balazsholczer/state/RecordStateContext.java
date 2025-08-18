package com.balazsholczer.state;

import java.util.function.Function;

public class RecordStateContext {
    
    public record StateData(String name, String description, Function<RecordStateContext, StateData> action) {
        
        public StateData execute(RecordStateContext context) {
            System.out.println("This is " + name + " state...");
            return action.apply(context);
        }
        
        public static StateData on() {
            return new StateData("on", "On state...", context -> {
                context.currentState = StateData.on();
                return context.currentState;
            });
        }
        
        public static StateData off() {
            return new StateData("off", "Off state...", context -> {
                context.currentState = StateData.off();
                return context.currentState;
            });
        }
    }
    
    private StateData currentState = StateData.off();
    
    public void setState(StateData state) {
        this.currentState = state;
    }
    
    public StateData doAction() {
        return currentState.execute(this);
    }
    
    public StateData getState() {
        return currentState;
    }
}