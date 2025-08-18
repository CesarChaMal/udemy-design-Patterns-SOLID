package com.balazsholczer.sessionfacade;

import java.math.BigDecimal;

public class PaymentService {
    
    public boolean processPayment(String orderId, BigDecimal amount) {
        System.out.println("PaymentService: Processing payment of " + amount + " for order " + orderId);
        return true; // Simulate successful payment
    }
    
    public void refundPayment(String orderId, BigDecimal amount) {
        System.out.println("PaymentService: Refunding " + amount + " for order " + orderId);
    }
}