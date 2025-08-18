package com.balazsholczer.saga;

public class PaymentStep implements SagaStep {
    private boolean executed = false;
    
    @Override
    public boolean execute() {
        System.out.println("PaymentStep: Processing payment");
        executed = true;
        return true; // Simulate success
    }
    
    @Override
    public void compensate() {
        if (executed) {
            System.out.println("PaymentStep: Refunding payment");
            executed = false;
        }
    }
    
    @Override
    public String getStepName() {
        return "Payment";
    }
}