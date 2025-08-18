package com.balazsholczer.businessdelegate;

public class PaymentService implements BusinessService {
    
    @Override
    public String processRequest(String request) {
        System.out.println("PaymentService: Processing payment request - " + request);
        return "Payment processed: " + request;
    }
}