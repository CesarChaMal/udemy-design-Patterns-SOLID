package com.balazsholczer.saga;

public interface SagaStep {
    boolean execute();
    void compensate();
    String getStepName();
}