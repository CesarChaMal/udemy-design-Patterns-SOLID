package com.balazsholczer.serviceactivator;

public class EmailService implements BusinessService {
    
    @Override
    public void processMessage(Message message) {
        System.out.println("EmailService: Processing email message - " + message.getId());
        
        // Simulate email sending
        try {
            Thread.sleep(500); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("EmailService: Sent email - " + message.getPayload());
    }
}