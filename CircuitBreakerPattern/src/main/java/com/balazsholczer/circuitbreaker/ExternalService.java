package com.balazsholczer.circuitbreaker;

public class ExternalService {
    
    private boolean isHealthy = true;
    private int callCount = 0;
    
    public String callService() {
        callCount++;
        System.out.println("ExternalService: Call #" + callCount);
        
        // Simulate service becoming unhealthy after 3 calls
        if (callCount > 3 && callCount <= 8) {
            isHealthy = false;
        } else if (callCount > 8) {
            isHealthy = true; // Service recovers
        }
        
        if (!isHealthy) {
            System.out.println("ExternalService: Service is unhealthy - throwing exception");
            throw new RuntimeException("Service unavailable");
        }
        
        System.out.println("ExternalService: Service call successful");
        return "Service response #" + callCount;
    }
    
    public void reset() {
        callCount = 0;
        isHealthy = true;
        System.out.println("ExternalService: Reset to healthy state");
    }
}