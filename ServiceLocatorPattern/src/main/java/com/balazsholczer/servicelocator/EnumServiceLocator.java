package com.balazsholczer.servicelocator;

import java.util.function.Supplier;

public enum EnumServiceLocator {
    DATABASE_SERVICE("databaseService", DatabaseService::new),
    MESSAGING_SERVICE("messagingService", MessagingService::new);
    
    private final String name;
    private final Supplier<Service> factory;
    private volatile Service instance;
    
    EnumServiceLocator(String name, Supplier<Service> factory) {
        this.name = name;
        this.factory = factory;
    }
    
    public Service getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = factory.get();
                }
            }
        }
        return instance;
    }
    
    public static Service getService(String name) {
        for (EnumServiceLocator locator : values()) {
            if (locator.name.equals(name)) {
                return locator.getInstance();
            }
        }
        return new Service() {
            @Override
            public String getName() {
                return "unknown";
            }
            
            @Override
            public void execute() {
                System.out.println("Unknown service: " + name);
            }
        };
    }
}