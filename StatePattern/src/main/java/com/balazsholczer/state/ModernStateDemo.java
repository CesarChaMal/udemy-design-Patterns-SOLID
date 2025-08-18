package com.balazsholczer.state;

import static com.balazsholczer.state.EnumStateContext.DeviceState;

public class ModernStateDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional State Pattern ===");
        Context traditional = new Context();
        State onState = new OnState();
        onState.doAction(traditional);
        System.out.println("State: " + traditional.getState().toString());
        
        State offState = new OffState();
        offState.doAction(traditional);
        System.out.println("State: " + traditional.getState().toString());
        
        System.out.println("\n=== Functional State Pattern ===");
        FunctionalStateContext functional = new FunctionalStateContext();
        functional.setState(FunctionalStateContext.ON_STATE);
        functional.doAction();
        System.out.println("State: " + functional.getStateName());
        
        functional.setState(FunctionalStateContext.OFF_STATE);
        functional.doAction();
        System.out.println("State: " + functional.getStateName());
        
        System.out.println("\n=== Enum State Pattern ===");
        EnumStateContext enumContext = new EnumStateContext();
        enumContext.setState(DeviceState.ON);
        enumContext.doAction();
        System.out.println("State: " + enumContext.getState());
        
        enumContext.setState(DeviceState.OFF);
        enumContext.doAction();
        System.out.println("State: " + enumContext.getState());
        
        System.out.println("\n=== Record State Pattern ===");
        RecordStateContext record = new RecordStateContext();
        record.setState(RecordStateContext.StateData.on());
        record.doAction();
        System.out.println("State: " + record.getState().description());
        
        record.setState(RecordStateContext.StateData.off());
        record.doAction();
        System.out.println("State: " + record.getState().description());
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Enum - Toggle Functionality:");
        enumContext.toggle();
        System.out.println("After toggle: " + enumContext.getState());
        enumContext.toggle();
        System.out.println("After toggle: " + enumContext.getState());
        
        System.out.println("Functional - Lambda States:");
        functional.setState(context -> {
            System.out.println("This is custom lambda state...");
            context.setStateName("Custom state...");
        });
        functional.doAction();
        System.out.println("State: " + functional.getStateName());
    }
}