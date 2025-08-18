package com.balazsholczer.template;

import java.util.Arrays;

public class ModernTemplateDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Template Pattern ===");
        int[] numbers1 = {100, 5, 7, -6, 0};
        Algorithm bubbleSort = new BubbleSort(numbers1.clone());
        bubbleSort.sort();
        System.out.println();
        
        Algorithm insertionSort = new InsertionSort(numbers1.clone());
        insertionSort.sort();
        System.out.println();
        
        System.out.println("\n=== Functional Template Pattern ===");
        int[] numbers2 = {100, 5, 7, -6, 0};
        FunctionalSortTemplate.sort(numbers2.clone(), "Bubble sort", 
                                   FunctionalSortTemplate.BUBBLE_SORT);
        
        FunctionalSortTemplate.sort(numbers2.clone(), "Insertion sort", 
                                   FunctionalSortTemplate.INSERTION_SORT);
        
        System.out.println("\n=== Enum Template Pattern ===");
        int[] numbers3 = {100, 5, 7, -6, 0};
        EnumSortTemplate.BUBBLE_SORT.sort(numbers3.clone());
        EnumSortTemplate.INSERTION_SORT.sort(numbers3.clone());
        
        System.out.println("\n=== Record Template Pattern ===");
        int[] numbers4 = {100, 5, 7, -6, 0};
        RecordSortTemplate.SortAlgorithm.bubbleSort().sort(numbers4.clone());
        RecordSortTemplate.SortAlgorithm.insertionSort().sort(numbers4.clone());
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Custom Lambda:");
        FunctionalSortTemplate.sort(numbers1.clone(), "Arrays sort", Arrays::sort);
        
        System.out.println("Enum - All Algorithms:");
        for (EnumSortTemplate algorithm : EnumSortTemplate.values()) {
            System.out.print(algorithm.name() + ": ");
            algorithm.sort(numbers1.clone());
        }
        
        System.out.println("Record - Custom Algorithm:");
        var customSort = new RecordSortTemplate.SortAlgorithm("Custom sort", Arrays::sort);
        customSort.sort(numbers1.clone());
    }
}