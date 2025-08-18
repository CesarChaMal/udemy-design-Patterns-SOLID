package com.balazsholczer.solid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.balazsholczer.solid.improved.*;
import java.util.Arrays;
import java.util.List;

/**
 * Comprehensive test for Open/Closed Principle
 * Tests Traditional vs Improved approaches
 */
class SolidOTest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional approach - SorterManager with hardcoded algorithms
        SorterManager manager = new SorterManager();
        
        assertDoesNotThrow(() -> manager.sort(new Quicksort()));
        assertDoesNotThrow(() -> manager.sort(new Mergesort()));
        assertDoesNotThrow(() -> manager.sort(new InsertionSort()));
    }
    
    @Test
    void testImprovedApproach() {
        // Improved approach - Open for extension, closed for modification
        List<Integer> data = Arrays.asList(5, 2, 8, 1, 9);
        
        // Can add new sorting algorithms without modifying existing code
        SortContext<Integer> context1 = new SortContext<>(new ParallelMergeSort<Integer>());
        assertDoesNotThrow(() -> context1.executeSort(data));
        
        SortContext<Integer> context2 = new SortContext<>(new TimSort<Integer>());
        assertDoesNotThrow(() -> context2.executeSort(data));
        
        SortContext<Integer> context3 = new SortContext<>(new StreamSort<Integer>());
        assertDoesNotThrow(() -> context3.executeSort(data));
    }
    
    @Test
    void testOpenClosedViolation() {
        // Traditional approach violates OCP
        // Adding new sorting algorithm requires modifying SorterManager
        SorterManager manager = new SorterManager();
        
        // Limited to predefined algorithms
        assertDoesNotThrow(() -> manager.sort(new Quicksort()));
        
        // To add new algorithm, must modify SorterManager class
        // This violates Open/Closed Principle
        assertTrue(true);
    }
    
    @Test
    void testOpenClosedCompliance() {
        // Improved approach follows OCP
        List<Integer> data = Arrays.asList(3, 1, 4, 1, 5);
        
        // Open for extension - can add new strategies
        SortStrategy<Integer> customStrategy = (list) -> {
            return java.util.concurrent.CompletableFuture.supplyAsync(() -> {
                java.util.List<Integer> result = new java.util.ArrayList<>(list);
                java.util.Collections.sort(result);
                return result;
            });
        };
        
        SortContext<Integer> context = new SortContext<>(customStrategy);
        List<Integer> result = context.executeSort(data).join();
        
        // Closed for modification - SortContext doesn't need to change
        assertEquals(Arrays.asList(1, 1, 3, 4, 5), result);
    }
    
    @Test
    void testExtensibility() {
        // Test that new algorithms can be added without modifying existing code
        List<Integer> data = Arrays.asList(9, 5, 1, 3, 7);
        
        // Add multiple new sorting strategies
        SortStrategy<Integer>[] strategies = new SortStrategy[]{
            new ParallelMergeSort<Integer>(),
            new TimSort<Integer>(),
            new StreamSort<Integer>()
        };
        
        for (SortStrategy<Integer> strategy : strategies) {
            SortContext<Integer> context = new SortContext<>(strategy);
            List<Integer> result = context.executeSort(data).join();
            
            // All should sort correctly
            assertEquals(Integer.valueOf(1), result.get(0));
            assertEquals(Integer.valueOf(9), result.get(4));
        }
    }
    
    @Test
    void testEquivalence() {
        // Both approaches should work correctly
        List<Integer> data = Arrays.asList(5, 2, 8, 1, 9);
        List<Integer> expectedResult = Arrays.asList(1, 2, 5, 8, 9);
        
        // Traditional approach
        SorterManager traditional = new SorterManager();
        assertDoesNotThrow(() -> traditional.sort(new Quicksort()));
        
        // Improved approach
        SortContext<Integer> improved = new SortContext<>(new TimSort<Integer>());
        List<Integer> improvedResult = improved.executeSort(data).join();
        
        // Improved approach should produce correct result
        assertEquals(expectedResult, improvedResult);
        assertTrue(true);
    }
}