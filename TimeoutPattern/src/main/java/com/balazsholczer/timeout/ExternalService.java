package com.balazsholczer.timeout;

public class ExternalService {
    
    public String fastOperation() {
        System.out.println("ExternalService: Executing fast operation");
        try {
            Thread.sleep(100); // 100ms
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Fast result";
    }
    
    public String slowOperation() {
        System.out.println("ExternalService: Executing slow operation");
        try {
            Thread.sleep(3000); // 3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted";
        }
        return "Slow result";
    }
    
    public String failingOperation() {
        System.out.println("ExternalService: Executing failing operation");
        throw new RuntimeException("Service unavailable");
    }
}