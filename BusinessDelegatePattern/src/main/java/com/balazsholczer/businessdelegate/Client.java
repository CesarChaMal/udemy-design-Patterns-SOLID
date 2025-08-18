package com.balazsholczer.businessdelegate;

public class Client {
    
    private final BusinessDelegate businessDelegate;
    
    public Client() {
        this.businessDelegate = new BusinessDelegate();
    }
    
    public void processOrder(String orderData) {
        businessDelegate.setServiceType("order");
        String result = businessDelegate.doTask(orderData);
        System.out.println("Client received: " + result);
    }
    
    public void processPayment(String paymentData) {
        businessDelegate.setServiceType("payment");
        String result = businessDelegate.doTask(paymentData);
        System.out.println("Client received: " + result);
    }
}