package com.balazsholczer.retry;

public class RetryPolicy {
    private final int maxAttempts;
    private final long baseDelayMs;
    private final double backoffMultiplier;
    
    public RetryPolicy(int maxAttempts, long baseDelayMs, double backoffMultiplier) {
        this.maxAttempts = maxAttempts;
        this.baseDelayMs = baseDelayMs;
        this.backoffMultiplier = backoffMultiplier;
    }
    
    public int getMaxAttempts() { return maxAttempts; }
    public long getBaseDelayMs() { return baseDelayMs; }
    public double getBackoffMultiplier() { return backoffMultiplier; }
    
    public long calculateDelay(int attempt) {
        return (long) (baseDelayMs * Math.pow(backoffMultiplier, attempt - 1));
    }
}