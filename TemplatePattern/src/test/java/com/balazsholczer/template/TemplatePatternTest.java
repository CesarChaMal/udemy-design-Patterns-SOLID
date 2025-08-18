package com.balazsholczer.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.function.Function;

/**
 * Comprehensive test class for Template Pattern - Traditional vs Modern approaches
 */
public class TemplatePatternTest {
    
    private int[] testArray;
    private int[] expectedSorted;
    
    @BeforeEach
    void setUp() {
        testArray = new int[]{5, 2, 8, 1, 9, 3};
        expectedSorted = new int[]{1, 2, 3, 5, 8, 9};
    }
    
    @Test
    void testTraditionalTemplatePattern() {
        // Traditional template method pattern
        int[] bubbleArray = testArray.clone();
        int[] insertionArray = testArray.clone();
        
        Algorithm bubbleSort = new BubbleSort(bubbleArray);
        Algorithm insertionSort = new InsertionSort(insertionArray);
        
        bubbleSort.sort();
        insertionSort.sort();
        
        assertArrayEquals(expectedSorted, bubbleArray);
        assertArrayEquals(expectedSorted, insertionArray);
        
        // Both should follow same template structure
        assertTrue(bubbleSort instanceof Algorithm);
        assertTrue(insertionSort instanceof Algorithm);
    }
    
    @Test
    void testFunctionalTemplatePattern() {
        // Functional template approach using static methods
        int[] bubbleArray = testArray.clone();
        int[] insertionArray = testArray.clone();
        
        assertDoesNotThrow(() -> FunctionalSortTemplate.sort(
            bubbleArray, "Bubble Sort", FunctionalSortTemplate.BUBBLE_SORT));
        assertDoesNotThrow(() -> FunctionalSortTemplate.sort(
            insertionArray, "Insertion Sort", FunctionalSortTemplate.INSERTION_SORT));
        
        assertArrayEquals(expectedSorted, bubbleArray);
        assertArrayEquals(expectedSorted, insertionArray);
    }
    
    @Test
    void testEnumTemplatePattern() {
        // Enum-based template pattern
        int[] bubbleArray = testArray.clone();
        int[] insertionArray = testArray.clone();
        
        assertDoesNotThrow(() -> EnumSortTemplate.BUBBLE_SORT.sort(bubbleArray));
        assertDoesNotThrow(() -> EnumSortTemplate.INSERTION_SORT.sort(insertionArray));
        
        assertArrayEquals(expectedSorted, bubbleArray);
        assertArrayEquals(expectedSorted, insertionArray);
    }
    
    @Test
    void testRecordTemplatePattern() {
        // Record-based template pattern
        RecordSortTemplate.SortAlgorithm bubbleAlgorithm = 
            RecordSortTemplate.SortAlgorithm.bubbleSort();
        RecordSortTemplate.SortAlgorithm insertionAlgorithm = 
            RecordSortTemplate.SortAlgorithm.insertionSort();
        
        int[] bubbleArray = testArray.clone();
        int[] insertionArray = testArray.clone();
        
        assertDoesNotThrow(() -> bubbleAlgorithm.sort(bubbleArray));
        assertDoesNotThrow(() -> insertionAlgorithm.sort(insertionArray));
        
        assertArrayEquals(expectedSorted, bubbleArray);
        assertArrayEquals(expectedSorted, insertionArray);
        
        assertEquals("Bubble sort", bubbleAlgorithm.name());
        assertEquals("Insertion sort", insertionAlgorithm.name());
    }
    
    @Test
    void testTemplateMethodStructure() {
        // Test that template method defines algorithm structure
        int[] array = testArray.clone();
        Algorithm algorithm = new BubbleSort(array);
        
        // Template method should call initialize, sorting, printResults in order
        assertDoesNotThrow(() -> algorithm.sort());
        
        // Array should be sorted after template execution
        assertArrayEquals(expectedSorted, array);
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify all approaches produce same results
        int[] traditionalArray = testArray.clone();
        int[] functionalArray = testArray.clone();
        int[] enumArray = testArray.clone();
        int[] recordArray = testArray.clone();
        
        Algorithm traditional = new BubbleSort(traditionalArray);
        traditional.sort();
        
        FunctionalSortTemplate.sort(functionalArray, "Bubble", FunctionalSortTemplate.BUBBLE_SORT);
        
        EnumSortTemplate.BUBBLE_SORT.sort(enumArray);
        
        RecordSortTemplate.SortAlgorithm.bubbleSort().sort(recordArray);
        
        // All should produce same result
        assertArrayEquals(expectedSorted, traditionalArray);
        assertArrayEquals(expectedSorted, functionalArray);
        assertArrayEquals(expectedSorted, enumArray);
        assertArrayEquals(expectedSorted, recordArray);
    }
    
    @Test
    void testTemplateFlexibility() {
        // Test template pattern's flexibility for different implementations
        int[] customArray = testArray.clone();
        
        // Functional approach allows easy customization
        FunctionalSortTemplate.sort(customArray, "Custom Sort", 
            arr -> Arrays.sort(arr)); // Using built-in sort
        
        assertArrayEquals(expectedSorted, customArray);
    }
    
    @Test
    void testHookMethods() {
        // Test template pattern execution
        int[] array = testArray.clone();
        Algorithm algorithm = new BubbleSort(array);
        
        assertDoesNotThrow(() -> algorithm.sort());
        assertArrayEquals(expectedSorted, array);
    }
    
    @Test
    void testPerformanceComparison() {
        // Test that different implementations sort correctly
        int[] largeArray = new int[100];
        Random random = new Random(42); // Fixed seed for reproducible results
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = random.nextInt(100);
        }
        
        int[] bubbleArray = largeArray.clone();
        int[] insertionArray = largeArray.clone();
        
        // All should sort correctly
        new BubbleSort(bubbleArray).sort();
        new InsertionSort(insertionArray).sort();
        
        // Results should be equivalent (both sorted)
        Arrays.sort(largeArray); // Expected result
        assertArrayEquals(largeArray, bubbleArray);
        assertArrayEquals(largeArray, insertionArray);
    }
}