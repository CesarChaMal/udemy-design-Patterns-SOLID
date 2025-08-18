package com.balazsholczer.serviceactivator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceActivator {
    
    private final Map<String, BusinessService> services;
    private final ExecutorService executorService;
    
    public ServiceActivator() {
        this.services = new HashMap<>();
        this.executorService = Executors.newFixedThreadPool(5);
        
        // Register services
        services.put("ORDER", new OrderService());
        services.put("EMAIL", new EmailService());
        
        System.out.println("ServiceActivator: Initialized with thread pool");
    }
    
    public void activateService(Message message) {
        System.out.println("ServiceActivator: Received message - " + message);
        
        BusinessService service = services.get(message.getType());
        if (service != null) {
            // Asynchronously activate the service
            executorService.submit(() -> {
                System.out.println("ServiceActivator: Activating service for " + message.getType() + 
                                 " on thread " + Thread.currentThread().getName());
                service.processMessage(message);
                System.out.println("ServiceActivator: Service activation completed for " + message.getId());
            });
        } else {
            System.out.println("ServiceActivator: No service found for message type - " + message.getType());
        }
    }
    
    public void registerService(String messageType, BusinessService service) {
        services.put(messageType, service);
        System.out.println("ServiceActivator: Registered service for " + messageType);
    }
    
    public void shutdown() {
        executorService.shutdown();
        System.out.println("ServiceActivator: Shutdown initiated");
    }
}