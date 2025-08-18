package com.balazsholczer.serviceactivator;

public class OrderService implements BusinessService {
    
    @Override
    public void processMessage(Message message) {
        System.out.println("OrderService: Processing order message - " + message.getId());
        
        // Simulate order processing
        try {
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("OrderService: Completed processing order - " + message.getPayload());
    }
}