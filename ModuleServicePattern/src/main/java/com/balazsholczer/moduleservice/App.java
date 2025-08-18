package com.balazsholczer.moduleservice;

import java.util.ServiceLoader;

public class App {
    public interface MessageService {
        String getMessage();
    }
    
    public static class DefaultMessageService implements MessageService {
        @Override
        public String getMessage() {
            return "Hello from Default Service!";
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Module Service Pattern Demo ===");
        
        // ServiceLoader pattern (simplified without module-info)
        MessageService service = new DefaultMessageService();
        System.out.println("Service message: " + service.getMessage());
        
        // Demonstrate service loading concept
        ServiceLoader<MessageService> loader = ServiceLoader.load(MessageService.class);
        System.out.println("Available services: " + loader.stream().count());
    }
}