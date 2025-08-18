package com.balazsholczer.wsbroker;

public class NotificationService implements WebService {
    
    @Override
    public String invoke(String request) {
        System.out.println("NotificationService: Sending notification - " + request);
        return "{\"status\": \"sent\", \"messageId\": \"MSG-456\"}";
    }
    
    @Override
    public String getServiceName() {
        return "NotificationService";
    }
}