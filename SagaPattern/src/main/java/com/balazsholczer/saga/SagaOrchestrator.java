package com.balazsholczer.saga;

import java.util.ArrayList;
import java.util.List;

public class SagaOrchestrator {
    private final List<SagaStep> steps = new ArrayList<>();
    private final List<SagaStep> executedSteps = new ArrayList<>();
    
    public void addStep(SagaStep step) {
        steps.add(step);
    }
    
    public boolean executeSaga() {
        System.out.println("SagaOrchestrator: Starting saga execution");
        
        for (SagaStep step : steps) {
            System.out.println("SagaOrchestrator: Executing " + step.getStepName());
            
            if (step.execute()) {
                executedSteps.add(step);
                System.out.println("SagaOrchestrator: " + step.getStepName() + " succeeded");
            } else {
                System.out.println("SagaOrchestrator: " + step.getStepName() + " failed - starting compensation");
                compensate();
                return false;
            }
        }
        
        System.out.println("SagaOrchestrator: Saga completed successfully");
        return true;
    }
    
    private void compensate() {
        System.out.println("SagaOrchestrator: Starting compensation");
        
        // Compensate in reverse order
        for (int i = executedSteps.size() - 1; i >= 0; i--) {
            SagaStep step = executedSteps.get(i);
            System.out.println("SagaOrchestrator: Compensating " + step.getStepName());
            step.compensate();
        }
        
        executedSteps.clear();
        System.out.println("SagaOrchestrator: Compensation completed");
    }
}