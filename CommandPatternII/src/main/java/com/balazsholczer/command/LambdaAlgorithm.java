package com.balazsholczer.command;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LambdaAlgorithm {
    
    private final BlockingQueue<Runnable> commandQueue = new ArrayBlockingQueue<>(10);
    
    public void produce() {
        try {
            for (int i = 0; i < 10; i++) {
                Task task = new Task(i + 1);
                commandQueue.put(task::solveProblem);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void consume() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                commandQueue.take().run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}