package com.balazsholczer.facade;

public class ModernFacadeDemo {
    
    public static void main(String[] args) {
        
        System.out.println("=== Traditional Facade Pattern ===");
        SortingManager traditional = new SortingManager();
        traditional.bubbleSort();
        traditional.mergeSort();
        traditional.heapSort();
        
        System.out.println("\n=== Functional Facade Pattern ===");
        FunctionalSortingManager.sort("bubble");
        FunctionalSortingManager.sort("merge");
        FunctionalSortingManager.sort("heap");
        
        System.out.println("\n=== Enum Facade Pattern ===");
        EnumSortingManager.BUBBLE.sort();
        EnumSortingManager.MERGE.sort();
        EnumSortingManager.HEAP.sort();
        
        System.out.println("\n=== Stream Facade Pattern ===");
        StreamSortingManager.sortAll();
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Sort All:");
        FunctionalSortingManager.sortAll();
        
        System.out.println("Enum - Sort All:");
        EnumSortingManager.sortAll();
        
        System.out.println("Stream - Sort by Index (0,2):");
        StreamSortingManager.sortByIndex(0, 2);
    }
}