package com.balazsholczer.circuitbreaker;

import java.util.function.Supplier;

public class CircuitBreaker {
    
    private CircuitBreakerState state = CircuitBreakerState.CLOSED;
    private int failureCount = 0;
    private long lastFailureTime = 0;
    
    private final int failureThreshold;
    private final long timeout;
    
    public CircuitBreaker(int failureThreshold, long timeout) {
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
        System.out.println("CircuitBreaker: Created with threshold=" + failureThreshold + ", timeout=" + timeout + "ms");
    }
    
    public <T> T call(Supplier<T> operation) {
        if (state == CircuitBreakerState.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime > timeout) {
                state = CircuitBreakerState.HALF_OPEN;
                System.out.println("CircuitBreaker: Transitioning to HALF_OPEN");
            } else {
                System.out.println("CircuitBreaker: Circuit is OPEN - failing fast");
                throw new CircuitBreakerException("Circuit breaker is OPEN");
            }
        }
        
        try {
            T result = operation.get();
            onSuccess();
            return result;
        } catch (Exception e) {
            onFailure();
            throw new CircuitBreakerException("Operation failed", e);
        }
    }
    
    private void onSuccess() {
        failureCount = 0;
        if (state == CircuitBreakerState.HALF_OPEN) {
            state = CircuitBreakerState.CLOSED;
            System.out.println("CircuitBreaker: Success in HALF_OPEN - transitioning to CLOSED");
        }
    }
    
    private void onFailure() {
        failureCount++;
        lastFailureTime = System.currentTimeMillis();
        
        if (failureCount >= failureThreshold) {
            state = CircuitBreakerState.OPEN;
            System.out.println("CircuitBreaker: Failure threshold reached - transitioning to OPEN");
        } else {
            System.out.println("CircuitBreaker: Failure " + failureCount + "/" + failureThreshold);
        }
    }
    
    public CircuitBreakerState getState() {
        return state;
    }
    
    public int getFailureCount() {
        return failureCount;
    }
}