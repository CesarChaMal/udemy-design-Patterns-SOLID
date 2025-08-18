package com.balazsholczer.template;

import java.util.Arrays;
import java.util.function.Consumer;

public class FunctionalSortTemplate {
    
    public static void sort(int[] numbers, String algorithmName, 
                           Consumer<int[]> sortingLogic) {
        
        // Template method steps
        initialize(algorithmName);
        sortingLogic.accept(numbers);
        printResults(numbers);
    }
    
    private static void initialize(String algorithmName) {
        System.out.println(algorithmName + " initializing...");
    }
    
    private static void printResults(int[] numbers) {
        Arrays.stream(numbers).forEach(n -> System.out.print(n + " "));
        System.out.println();
    }
    
    public static final Consumer<int[]> BUBBLE_SORT = numbers -> {
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
    };
    
    public static final Consumer<int[]> INSERTION_SORT = numbers -> {
        for (int i = 1; i < numbers.length; i++) {
            int temp = numbers[i];
            int j = i;
            while (j > 0 && numbers[j - 1] > temp) {
                numbers[j] = numbers[j - 1];
                j = j - 1;
            }
            numbers[j] = temp;
        }
    };
}