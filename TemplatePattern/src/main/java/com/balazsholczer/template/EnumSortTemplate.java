package com.balazsholczer.template;

import java.util.Arrays;
import java.util.function.Consumer;

public enum EnumSortTemplate {
    BUBBLE_SORT("Bubble sort", numbers -> {
        int n = numbers.length;
        int temp;
        while (n != 0) {
            temp = 0;
            for (int i = 1; i < n; i++) {
                if (numbers[i - 1] > numbers[i]) {
                    int swap = numbers[i - 1];
                    numbers[i - 1] = numbers[i];
                    numbers[i] = swap;
                    temp = i;
                }
            }
            n = temp;
        }
    }),
    
    INSERTION_SORT("Insertion sort", numbers -> {
        for (int i = 1; i < numbers.length; i++) {
            int temp = numbers[i];
            int j = i;
            while (j > 0 && numbers[j - 1] > temp) {
                numbers[j] = numbers[j - 1];
                j = j - 1;
            }
            numbers[j] = temp;
        }
    });
    
    private final String name;
    private final Consumer<int[]> sortingLogic;
    
    EnumSortTemplate(String name, Consumer<int[]> sortingLogic) {
        this.name = name;
        this.sortingLogic = sortingLogic;
    }
    
    public void sort(int[] numbers) {
        initialize();
        sortingLogic.accept(numbers);
        printResults(numbers);
    }
    
    private void initialize() {
        System.out.println(name + " initializing...");
    }
    
    private void printResults(int[] numbers) {
        Arrays.stream(numbers).forEach(n -> System.out.print(n + " "));
        System.out.println();
    }
}