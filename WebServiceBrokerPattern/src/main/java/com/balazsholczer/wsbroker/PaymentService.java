package com.balazsholczer.wsbroker;

public class PaymentService implements WebService {
    
    @Override
    public String invoke(String request) {
        System.out.println("PaymentService: Processing payment request - " + request);
        return "{\"status\": \"success\", \"transactionId\": \"PAY-123\"}";
    }
    
    @Override
    public String getServiceName() {
        return "PaymentService";
    }
}