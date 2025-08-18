package com.balazsholczer.command;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class StreamAlgorithm {
    
    public static void executeAsync() {
        var executor = Executors.newFixedThreadPool(2);
        
        var producer = CompletableFuture.runAsync(() -> 
            IntStream.rangeClosed(1, 10)
                .mapToObj(Task::new)
                .map(Task::asRunnable)
                .forEach(Runnable::run), executor);
        
        var consumer = CompletableFuture.runAsync(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            System.out.println("Consumer finished");
        }, executor);
        
        CompletableFuture.allOf(producer, consumer).join();
        executor.shutdown();
    }
}