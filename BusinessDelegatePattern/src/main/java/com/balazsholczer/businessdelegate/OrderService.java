package com.balazsholczer.businessdelegate;

public class OrderService implements BusinessService {
    
    @Override
    public String processRequest(String request) {
        System.out.println("OrderService: Processing order request - " + request);
        return "Order processed: " + request;
    }
}