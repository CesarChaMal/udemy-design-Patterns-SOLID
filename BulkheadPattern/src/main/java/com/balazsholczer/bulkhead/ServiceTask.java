package com.balazsholczer.bulkhead;

import java.util.concurrent.Callable;

public class ServiceTask implements Callable<String> {
    private final String serviceName;
    private final int processingTime;
    private final boolean shouldFail;
    
    public ServiceTask(String serviceName, int processingTime, boolean shouldFail) {
        this.serviceName = serviceName;
        this.processingTime = processingTime;
        this.shouldFail = shouldFail;
    }
    
    @Override
    public String call() throws Exception {
        System.out.println("ServiceTask: " + serviceName + " started processing");
        
        Thread.sleep(processingTime);
        
        if (shouldFail) {
            System.out.println("ServiceTask: " + serviceName + " failed");
            throw new RuntimeException(serviceName + " service failed");
        }
        
        System.out.println("ServiceTask: " + serviceName + " completed successfully");
        return serviceName + " result";
    }
}