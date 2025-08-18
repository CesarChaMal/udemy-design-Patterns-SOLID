package com.balazsholczer.retry;

public class UnreliableService {
    private int callCount = 0;
    private final double successRate;
    
    public UnreliableService(double successRate) {
        this.successRate = successRate;
    }
    
    public String callService() {
        callCount++;
        System.out.println("UnreliableService: Call #" + callCount);
        
        if (Math.random() > successRate) {
            throw new RuntimeException("Service temporarily unavailable");
        }
        
        return "Service response #" + callCount;
    }
    
    public void reset() {
        callCount = 0;
    }
}