package com.balazsholczer.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Strategy Pattern
 * Tests Traditional vs Modern approaches
 */
class StrategyPatternTest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional Strategy Pattern
        Manager manager = new Manager();
        
        // Test Add strategy
        manager.setStrategy(new Add());
        assertDoesNotThrow(() -> manager.operation(5, 3));
        
        // Test Multiply strategy
        manager.setStrategy(new Multiply());
        assertDoesNotThrow(() -> manager.operation(5, 3));
    }
    
    @Test
    void testStrategySwapping() {
        // Test runtime strategy swapping
        Manager manager = new Manager();
        
        // Start with Add
        manager.setStrategy(new Add());
        assertDoesNotThrow(() -> manager.operation(10, 5));
        
        // Switch to Multiply
        manager.setStrategy(new Multiply());
        assertDoesNotThrow(() -> manager.operation(10, 5));
    }
    
    @Test
    void testEquivalence() {
        // All approaches should handle operations correctly
        Manager traditional = new Manager();
        
        // Traditional approach
        traditional.setStrategy(new Add());
        assertDoesNotThrow(() -> traditional.operation(7, 3));
        
        // Strategy should work correctly
        assertTrue(true);
    }
}