package com.balazsholczer.bulkhead;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public class ResourcePool {
    private final String name;
    private final ExecutorService executor;
    
    public ResourcePool(String name, int poolSize) {
        this.name = name;
        this.executor = Executors.newFixedThreadPool(poolSize);
        System.out.println("ResourcePool: Created " + name + " with " + poolSize + " threads");
    }
    
    public <T> Future<T> submit(Callable<T> task) {
        System.out.println("ResourcePool: Submitting task to " + name);
        return executor.submit(task);
    }
    
    public void shutdown() {
        executor.shutdown();
        System.out.println("ResourcePool: Shutdown " + name);
    }
    
    public String getName() {
        return name;
    }
}