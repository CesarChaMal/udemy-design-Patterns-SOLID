package com.balazsholczer.retry;

import java.util.function.Supplier;

public class RetryableOperation<T> {
    private final RetryPolicy policy;
    
    public RetryableOperation(RetryPolicy policy) {
        this.policy = policy;
    }
    
    public T execute(Supplier<T> operation) throws Exception {
        Exception lastException = null;
        
        for (int attempt = 1; attempt <= policy.getMaxAttempts(); attempt++) {
            try {
                System.out.println("RetryableOperation: Attempt " + attempt + "/" + policy.getMaxAttempts());
                return operation.get();
            } catch (Exception e) {
                lastException = e;
                System.out.println("RetryableOperation: Attempt " + attempt + " failed - " + e.getMessage());
                
                if (attempt < policy.getMaxAttempts()) {
                    long delay = policy.calculateDelay(attempt);
                    System.out.println("RetryableOperation: Waiting " + delay + "ms before retry");
                    
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                }
            }
        }
        
        System.out.println("RetryableOperation: All attempts failed");
        throw new RuntimeException("Operation failed after " + policy.getMaxAttempts() + " attempts", lastException);
    }
}