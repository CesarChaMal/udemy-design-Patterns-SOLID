package com.balazsholczer.state;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for State Pattern
 * Tests Traditional, Enum, and Functional approaches
 */
class StateTest {
    
    @Test
    void testTraditionalState() {
        Context context = new Context();
        OnState onState = new OnState();
        OffState offState = new OffState();
        
        context.setState(onState);
        assertDoesNotThrow(() -> context.getState().doAction(context));
        
        context.setState(offState);
        assertDoesNotThrow(() -> context.getState().doAction(context));
    }
    
    @Test
    void testEnumStateContext() {
        EnumStateContext context = new EnumStateContext();
        
        // Test initial state
        assertEquals(EnumStateContext.DeviceState.OFF, context.getState());
        
        // Test state actions
        assertDoesNotThrow(() -> context.doAction());
        
        // Test toggle functionality
        context.toggle();
        assertEquals(EnumStateContext.DeviceState.ON, context.getState());
        
        context.toggle();
        assertEquals(EnumStateContext.DeviceState.OFF, context.getState());
        
        // Test manual state setting
        context.setState(EnumStateContext.DeviceState.ON);
        assertEquals(EnumStateContext.DeviceState.ON, context.getState());
        assertDoesNotThrow(() -> context.doAction());
    }
    
    @Test
    void testFunctionalStateContext() {
        FunctionalStateContext context = new FunctionalStateContext();
        
        context.setState(FunctionalStateContext.ON_STATE);
        assertDoesNotThrow(() -> context.doAction());
        assertEquals("On state...", context.getStateName());
        
        context.setState(FunctionalStateContext.OFF_STATE);
        assertDoesNotThrow(() -> context.doAction());
        assertEquals("Off state...", context.getStateName());
    }
    
    @Test
    void testStateTransitions() {
        EnumStateContext enumContext = new EnumStateContext();
        
        // Test multiple transitions
        for (int i = 0; i < 5; i++) {
            EnumStateContext.DeviceState expectedState = 
                (i % 2 == 0) ? EnumStateContext.DeviceState.OFF : EnumStateContext.DeviceState.ON;
            assertEquals(expectedState, enumContext.getState());
            enumContext.toggle();
        }
    }
    
    @Test
    void testCustomFunctionalStates() {
        FunctionalStateContext context = new FunctionalStateContext();
        
        // Test custom state
        context.setState(ctx -> {
            System.out.println("Custom state action");
            ctx.setStateName("Custom state");
        });
        
        assertDoesNotThrow(() -> context.doAction());
        assertEquals("Custom state", context.getStateName());
    }
    
    @Test
    void testStatePolymorphism() {
        Context context = new Context();
        
        State[] states = {new OnState(), new OffState()};
        
        for (State state : states) {
            context.setState(state);
            assertDoesNotThrow(() -> context.getState().doAction(context));
        }
    }
    
    @Test
    void testEquivalence() {
        // All approaches should handle state changes correctly
        Context traditional = new Context();
        traditional.setState(new OnState());
        
        EnumStateContext enumContext = new EnumStateContext();
        enumContext.setState(EnumStateContext.DeviceState.ON);
        
        FunctionalStateContext functional = new FunctionalStateContext();
        functional.setState(FunctionalStateContext.ON_STATE);
        
        // All should execute state actions without throwing
        assertDoesNotThrow(() -> traditional.getState().doAction(traditional));
        assertDoesNotThrow(() -> enumContext.doAction());
        assertDoesNotThrow(() -> functional.doAction());
        
        // Enum approach provides additional functionality
        assertEquals(EnumStateContext.DeviceState.ON, enumContext.getState());
        assertEquals("On state...", functional.getStateName());
    }
    
    @Test
    void testEnumStateToggling() {
        EnumStateContext context = new EnumStateContext();
        
        // Test that toggle changes state correctly
        EnumStateContext.DeviceState initialState = context.getState();
        context.toggle();
        assertNotEquals(initialState, context.getState());
        
        // Toggle back
        context.toggle();
        assertEquals(initialState, context.getState());
    }
}