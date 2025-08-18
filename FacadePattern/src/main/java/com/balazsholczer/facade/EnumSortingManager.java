package com.balazsholczer.facade;

public enum EnumSortingManager {
    BUBBLE(() -> System.out.println("Bubbesort...")),
    MERGE(() -> System.out.println("Mergesort...")),
    HEAP(() -> System.out.println("Heapsort..."));
    
    private final Runnable algorithm;
    
    EnumSortingManager(Runnable algorithm) {
        this.algorithm = algorithm;
    }
    
    public void sort() {
        algorithm.run();
    }
    
    public static void sortAll() {
        for (EnumSortingManager manager : values()) {
            manager.sort();
        }
    }
}