package com.balazsholczer.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Strategy Pattern
 * Tests Traditional, Enum, Functional, and Stream approaches
 */
class StrategyTest {
    
    @Test
    void testTraditionalStrategy() {
        Manager manager = new Manager();
        manager.setStrategy(new Add());
        assertDoesNotThrow(() -> manager.operation(3, 4));
        
        manager.setStrategy(new Multiply());
        assertDoesNotThrow(() -> manager.operation(3, 4));
    }
    
    @Test
    void testEnumStrategyManager() {
        EnumStrategyManager manager = new EnumStrategyManager();
        manager.setStrategy(EnumStrategyManager.Operation.ADD);
        assertEquals(7, manager.operation(3, 4));
        
        manager.setStrategy(EnumStrategyManager.Operation.MULTIPLY);
        assertEquals(12, manager.operation(3, 4));
        
        manager.setStrategy(EnumStrategyManager.Operation.SUBTRACT);
        assertEquals(-1, manager.operation(3, 4));
        
        manager.setStrategy(EnumStrategyManager.Operation.DIVIDE);
        assertEquals(0, manager.operation(3, 4));
    }
    
    @Test
    void testFunctionalManager() {
        FunctionalManager manager = new FunctionalManager();
        manager.setStrategy((a, b) -> a + b);
        assertEquals(7, manager.operation(3, 4));
        
        manager.setStrategy((a, b) -> a * b);
        assertEquals(12, manager.operation(3, 4));
        
        manager.setStrategy(FunctionalManager.ADD);
        assertEquals(7, manager.operation(3, 4));
        
        manager.setStrategy(FunctionalManager.MULTIPLY);
        assertEquals(12, manager.operation(3, 4));
        
        manager.setStrategy(FunctionalManager.SUBTRACT);
        assertEquals(-1, manager.operation(3, 4));
    }
    
    @Test
    void testStreamStrategyManager() {
        assertEquals(7, StreamStrategyManager.execute("add", 3, 4));
        assertEquals(12, StreamStrategyManager.execute("multiply", 3, 4));
        assertEquals(-1, StreamStrategyManager.execute("subtract", 3, 4));
        assertEquals(0, StreamStrategyManager.execute("divide", 3, 4));
        assertEquals(0, StreamStrategyManager.execute("unknown", 3, 4));
        assertDoesNotThrow(() -> StreamStrategyManager.executeAll(3, 4));
        
        int chainResult = StreamStrategyManager.executeChain(2, "add", "multiply");
        assertTrue(chainResult > 0);
    }
    
    @Test
    void testEquivalence() {
        // Modern approaches should produce same results for same operations
        EnumStrategyManager enumManager = new EnumStrategyManager();
        enumManager.setStrategy(EnumStrategyManager.Operation.ADD);
        
        FunctionalManager functional = new FunctionalManager();
        functional.setStrategy(FunctionalManager.ADD);
        
        int a = 5, b = 3;
        assertEquals(8, enumManager.operation(a, b));
        assertEquals(8, functional.operation(a, b));
        assertEquals(8, StreamStrategyManager.execute("add", a, b));
        
        // Test multiplication equivalence
        enumManager.setStrategy(EnumStrategyManager.Operation.MULTIPLY);
        functional.setStrategy(FunctionalManager.MULTIPLY);
        
        assertEquals(15, enumManager.operation(a, b));
        assertEquals(15, functional.operation(a, b));
        assertEquals(15, StreamStrategyManager.execute("multiply", a, b));
    }
    
    @Test
    void testTraditionalApproachBehavior() {
        // Traditional approach prints results, test it doesn't throw
        Manager manager = new Manager();
        
        manager.setStrategy(new Add());
        assertDoesNotThrow(() -> manager.operation(10, 5));
        
        manager.setStrategy(new Multiply());
        assertDoesNotThrow(() -> manager.operation(10, 5));
    }
}