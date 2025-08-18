package com.balazsholczer.serviceactivator;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class FunctionalServiceActivator {
    
    private final Map<String, Consumer<Message>> handlers;
    private final ExecutorService executorService;
    
    public FunctionalServiceActivator() {
        this.executorService = Executors.newFixedThreadPool(3);
        this.handlers = Map.of(
            "ORDER", message -> {
                System.out.println("Functional: Processing order - " + message.getPayload());
                try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("Functional: Order processed - " + message.getId());
            },
            "EMAIL", message -> {
                System.out.println("Functional: Sending email - " + message.getPayload());
                try { Thread.sleep(300); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("Functional: Email sent - " + message.getId());
            },
            "NOTIFICATION", message -> {
                System.out.println("Functional: Sending notification - " + message.getPayload());
                try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                System.out.println("Functional: Notification sent - " + message.getId());
            }
        );
        
        System.out.println("FunctionalServiceActivator: Initialized with functional handlers");
    }
    
    public CompletableFuture<Void> activateService(Message message) {
        System.out.println("FunctionalServiceActivator: Activating service for " + message.getType());
        
        Consumer<Message> handler = handlers.get(message.getType());
        if (handler != null) {
            return CompletableFuture.runAsync(() -> handler.accept(message), executorService);
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }
    
    public CompletableFuture<Void> activateMultipleServices(Message... messages) {
        CompletableFuture<Void>[] futures = new CompletableFuture[messages.length];
        
        for (int i = 0; i < messages.length; i++) {
            futures[i] = activateService(messages[i]);
        }
        
        return CompletableFuture.allOf(futures);
    }
    
    public void shutdown() {
        executorService.shutdown();
        System.out.println("FunctionalServiceActivator: Shutdown initiated");
    }
}