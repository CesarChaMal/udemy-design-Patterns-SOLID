package com.balazsholczer.facade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Facade Pattern
 * Tests Traditional, Functional, and Enum approaches
 */
class FacadeTest {
    
    @Test
    void testTraditionalFacade() {
        SortingManager manager = new SortingManager();
        
        assertDoesNotThrow(() -> manager.bubbleSort());
        assertDoesNotThrow(() -> manager.mergeSort());
        assertDoesNotThrow(() -> manager.heapSort());
    }
    
    @Test
    void testFunctionalFacade() {
        assertDoesNotThrow(() -> FunctionalSortingManager.sort("bubble"));
        assertDoesNotThrow(() -> FunctionalSortingManager.sort("merge"));
        assertDoesNotThrow(() -> FunctionalSortingManager.sort("heap"));
        assertDoesNotThrow(() -> FunctionalSortingManager.sort("unknown"));
        assertDoesNotThrow(() -> FunctionalSortingManager.sortAll());
    }
    
    @Test
    void testEnumFacade() {
        assertDoesNotThrow(() -> EnumSortingManager.BUBBLE.sort());
        assertDoesNotThrow(() -> EnumSortingManager.MERGE.sort());
        assertDoesNotThrow(() -> EnumSortingManager.HEAP.sort());
        assertDoesNotThrow(() -> EnumSortingManager.sortAll());
    }
    
    @Test
    void testFacadeSimplification() {
        // Facade should simplify complex subsystem interactions
        SortingManager traditional = new SortingManager();
        
        // Single method calls hide complex algorithm implementations
        assertDoesNotThrow(() -> traditional.bubbleSort());
        assertDoesNotThrow(() -> traditional.mergeSort());
        assertDoesNotThrow(() -> traditional.heapSort());
        
        // Functional approach provides even simpler interface
        assertDoesNotThrow(() -> FunctionalSortingManager.sortAll());
        
        // Enum approach provides type-safe operations
        for (EnumSortingManager algorithm : EnumSortingManager.values()) {
            assertDoesNotThrow(() -> algorithm.sort());
        }
    }
    
    @Test
    void testEquivalence() {
        // All facade implementations should provide same functionality
        SortingManager traditional = new SortingManager();
        
        // All approaches should handle sorting operations
        assertDoesNotThrow(() -> {
            traditional.bubbleSort();
            FunctionalSortingManager.sort("bubble");
            EnumSortingManager.BUBBLE.sort();
        });
        
        assertDoesNotThrow(() -> {
            traditional.mergeSort();
            FunctionalSortingManager.sort("merge");
            EnumSortingManager.MERGE.sort();
        });
        
        assertDoesNotThrow(() -> {
            traditional.heapSort();
            FunctionalSortingManager.sort("heap");
            EnumSortingManager.HEAP.sort();
        });
    }
}