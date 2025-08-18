package com.balazsholczer.mediator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventMediator {
    
    public record Event(String type, Object data, String source) {}
    
    public record Subscription(String eventType, Consumer<Event> handler, String subscriberId) {}
    
    private final Map<String, List<Subscription>> subscriptions = new ConcurrentHashMap<>();
    
    public void subscribe(String eventType, String subscriberId, Consumer<Event> handler) {
        subscriptions.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                    .add(new Subscription(eventType, handler, subscriberId));
        System.out.println("EventMediator: " + subscriberId + " subscribed to " + eventType);
    }
    
    public void unsubscribe(String eventType, String subscriberId) {
        List<Subscription> subs = subscriptions.get(eventType);
        if (subs != null) {
            subs.removeIf(sub -> sub.subscriberId().equals(subscriberId));
            System.out.println("EventMediator: " + subscriberId + " unsubscribed from " + eventType);
        }
    }
    
    public void publish(Event event) {
        System.out.println("EventMediator: Publishing " + event.type() + " from " + event.source());
        List<Subscription> subs = subscriptions.get(event.type());
        if (subs != null) {
            subs.forEach(sub -> {
                try {
                    sub.handler().accept(event);
                } catch (Exception e) {
                    System.err.println("Error handling event: " + e.getMessage());
                }
            });
        }
    }
    
    public void publish(String eventType, Object data, String source) {
        publish(new Event(eventType, data, source));
    }
    
    public int getSubscriberCount(String eventType) {
        return subscriptions.getOrDefault(eventType, List.of()).size();
    }
}