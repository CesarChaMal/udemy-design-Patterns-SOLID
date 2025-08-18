package com.balazsholczer.saga;

public class InventoryStep implements SagaStep {
    private boolean executed = false;
    
    @Override
    public boolean execute() {
        System.out.println("InventoryStep: Reserving inventory");
        executed = true;
        return Math.random() > 0.3; // 70% success rate
    }
    
    @Override
    public void compensate() {
        if (executed) {
            System.out.println("InventoryStep: Releasing inventory");
            executed = false;
        }
    }
    
    @Override
    public String getStepName() {
        return "Inventory";
    }
}