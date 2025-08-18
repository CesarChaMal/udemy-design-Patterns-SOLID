package com.balazsholczer.virtualthread;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Virtual Thread Pattern Demo ===");
        
        // Virtual Thread Executor
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            System.out.println("Creating 1000 virtual threads...");
            
            IntStream.range(0, 1000)
                .forEach(i -> executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofMillis(100));
                        if (i % 100 == 0) {
                            System.out.println("Virtual thread " + i + " completed on: " + Thread.currentThread());
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }));
        }
        
        System.out.println("All virtual threads completed!");
    }
}