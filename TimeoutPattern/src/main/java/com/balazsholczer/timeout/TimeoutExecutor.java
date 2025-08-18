package com.balazsholczer.timeout;

import java.util.concurrent.*;

public class TimeoutExecutor {
    private final ExecutorService executor;
    
    public TimeoutExecutor() {
        this.executor = Executors.newCachedThreadPool();
    }
    
    public <T> T executeWithTimeout(Callable<T> task, long timeoutMs) throws TimeoutException, Exception {
        System.out.println("TimeoutExecutor: Executing task with " + timeoutMs + "ms timeout");
        
        Future<T> future = executor.submit(task);
        
        try {
            T result = future.get(timeoutMs, TimeUnit.MILLISECONDS);
            System.out.println("TimeoutExecutor: Task completed within timeout");
            return result;
        } catch (java.util.concurrent.TimeoutException e) {
            future.cancel(true);
            System.out.println("TimeoutExecutor: Task timed out after " + timeoutMs + "ms");
            throw new TimeoutException("Operation timed out after " + timeoutMs + "ms");
        } catch (ExecutionException e) {
            System.out.println("TimeoutExecutor: Task failed with exception");
            throw new Exception("Task execution failed", e.getCause());
        }
    }
    
    public void shutdown() {
        executor.shutdown();
        System.out.println("TimeoutExecutor: Shutdown completed");
    }
}