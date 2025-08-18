package com.balazsholczer.state;

public class EnumStateContext {
    
    public enum DeviceState {
        ON("On state...") {
            @Override
            public void doAction(EnumStateContext context) {
                System.out.println("This is on state...");
                context.currentState = this;
            }
            
            @Override
            public DeviceState toggle() {
                return OFF;
            }
        },
        OFF("Off state...") {
            @Override
            public void doAction(EnumStateContext context) {
                System.out.println("This is off state...");
                context.currentState = this;
            }
            
            @Override
            public DeviceState toggle() {
                return ON;
            }
        };
        
        private final String description;
        
        DeviceState(String description) {
            this.description = description;
        }
        
        public abstract void doAction(EnumStateContext context);
        public abstract DeviceState toggle();
        
        @Override
        public String toString() {
            return description;
        }
    }
    
    private DeviceState currentState = DeviceState.OFF;
    
    public void setState(DeviceState state) {
        this.currentState = state;
    }
    
    public void doAction() {
        currentState.doAction(this);
    }
    
    public void toggle() {
        currentState = currentState.toggle();
        doAction();
    }
    
    public DeviceState getState() {
        return currentState;
    }
}