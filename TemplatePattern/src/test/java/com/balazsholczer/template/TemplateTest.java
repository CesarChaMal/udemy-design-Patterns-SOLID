package com.balazsholczer.template;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Template Pattern
 * Tests Traditional, Enum, Functional, and Record approaches
 */
class TemplateTest {
    
    @Test
    void testTraditionalTemplate() {
        int[] numbers = {5, 2, 8, 1, 9};
        
        BubbleSort bubbleSort = new BubbleSort(numbers.clone());
        assertDoesNotThrow(() -> bubbleSort.sort());
        
        InsertionSort insertionSort = new InsertionSort(numbers.clone());
        assertDoesNotThrow(() -> insertionSort.sort());
    }
    
    @Test
    void testEnumTemplate() {
        int[] numbers = {5, 2, 8, 1, 9};
        
        assertDoesNotThrow(() -> EnumSortTemplate.BUBBLE_SORT.sort(numbers.clone()));
        assertDoesNotThrow(() -> EnumSortTemplate.INSERTION_SORT.sort(numbers.clone()));
    }
    
    @Test
    void testFunctionalTemplate() {
        int[] numbers = {5, 2, 8, 1, 9};
        
        assertDoesNotThrow(() -> FunctionalSortTemplate.sort(
            numbers.clone(), "Bubble Sort", FunctionalSortTemplate.BUBBLE_SORT));
        
        assertDoesNotThrow(() -> FunctionalSortTemplate.sort(
            numbers.clone(), "Insertion Sort", FunctionalSortTemplate.INSERTION_SORT));
    }
    
    @Test
    void testRecordTemplate() {
        int[] numbers = {5, 2, 8, 1, 9};
        
        RecordSortTemplate.SortAlgorithm bubbleSort = 
            RecordSortTemplate.SortAlgorithm.bubbleSort();
        assertDoesNotThrow(() -> bubbleSort.sort(numbers.clone()));
        
        RecordSortTemplate.SortAlgorithm insertionSort = 
            RecordSortTemplate.SortAlgorithm.insertionSort();
        assertDoesNotThrow(() -> insertionSort.sort(numbers.clone()));
    }
    
    @Test
    void testSortingCorrectness() {
        int[] unsorted = {5, 2, 8, 1, 9};
        int[] expected = {1, 2, 5, 8, 9};
        
        // Test traditional approach
        int[] bubbleArray = unsorted.clone();
        BubbleSort bubbleSort = new BubbleSort(bubbleArray);
        bubbleSort.sort();
        assertArrayEquals(expected, bubbleArray);
        
        // Test enum approach
        int[] enumArray = unsorted.clone();
        EnumSortTemplate.BUBBLE_SORT.sort(enumArray);
        assertArrayEquals(expected, enumArray);
        
        // Test functional approach
        int[] functionalArray = unsorted.clone();
        FunctionalSortTemplate.sort(functionalArray, "Test", FunctionalSortTemplate.BUBBLE_SORT);
        assertArrayEquals(expected, functionalArray);
        
        // Test record approach
        int[] recordArray = unsorted.clone();
        RecordSortTemplate.SortAlgorithm.bubbleSort().sort(recordArray);
        assertArrayEquals(expected, recordArray);
    }
    
    @Test
    void testTemplateSteps() {
        int[] numbers = {3, 1, 2};
        
        // All approaches should follow template method pattern:
        // 1. Initialize
        // 2. Sort
        // 3. Print results
        
        assertDoesNotThrow(() -> {
            new BubbleSort(numbers.clone()).sort();
            EnumSortTemplate.BUBBLE_SORT.sort(numbers.clone());
            FunctionalSortTemplate.sort(numbers.clone(), "Test", FunctionalSortTemplate.BUBBLE_SORT);
            RecordSortTemplate.SortAlgorithm.bubbleSort().sort(numbers.clone());
        });
    }
    
    @Test
    void testCustomFunctionalSort() {
        int[] numbers = {3, 1, 4, 1, 5};
        
        // Test custom sorting logic
        assertDoesNotThrow(() -> FunctionalSortTemplate.sort(
            numbers.clone(), 
            "Custom Sort", 
            arr -> java.util.Arrays.sort(arr)
        ));
    }
    
    @Test
    void testEquivalence() {
        int[] original = {7, 3, 9, 1, 5};
        
        // All approaches should produce same sorted result
        int[] traditional = original.clone();
        int[] enumResult = original.clone();
        int[] functional = original.clone();
        int[] record = original.clone();
        
        new BubbleSort(traditional).sort();
        EnumSortTemplate.BUBBLE_SORT.sort(enumResult);
        FunctionalSortTemplate.sort(functional, "Test", FunctionalSortTemplate.BUBBLE_SORT);
        RecordSortTemplate.SortAlgorithm.bubbleSort().sort(record);
        
        assertArrayEquals(traditional, enumResult);
        assertArrayEquals(traditional, functional);
        assertArrayEquals(traditional, record);
    }
    
    @Test
    void testRecordFactoryMethods() {
        int[] numbers = {4, 2, 7, 1};
        
        // Test factory methods
        RecordSortTemplate.SortAlgorithm bubble = 
            RecordSortTemplate.SortAlgorithm.bubbleSort();
        RecordSortTemplate.SortAlgorithm insertion = 
            RecordSortTemplate.SortAlgorithm.insertionSort();
        
        assertDoesNotThrow(() -> bubble.sort(numbers.clone()));
        assertDoesNotThrow(() -> insertion.sort(numbers.clone()));
        
        assertEquals("Bubble sort", bubble.name());
        assertEquals("Insertion sort", insertion.name());
    }
}