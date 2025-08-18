package com.balazsholczer.sidecar;

public class LoggingSidecar implements Sidecar {
    private boolean running = false;
    
    @Override
    public void start() {
        running = true;
        System.out.println("LoggingSidecar: Started - collecting and forwarding logs");
    }
    
    @Override
    public void stop() {
        running = false;
        System.out.println("LoggingSidecar: Stopped");
    }
    
    @Override
    public String getName() {
        return "LoggingSidecar";
    }
    
    public void collectLogs(String logMessage) {
        if (running) {
            System.out.println("LoggingSidecar: Collected log - " + logMessage);
            forwardToLogAggregator(logMessage);
        }
    }
    
    private void forwardToLogAggregator(String logMessage) {
        System.out.println("LoggingSidecar: Forwarded to log aggregator - " + logMessage);
    }
}