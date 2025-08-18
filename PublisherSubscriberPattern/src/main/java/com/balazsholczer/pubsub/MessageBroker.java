package com.balazsholczer.pubsub;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MessageBroker {
    private final Map<String, List<Subscriber>> topicSubscribers = new ConcurrentHashMap<>();
    
    public void subscribe(String topic, Subscriber subscriber) {
        topicSubscribers.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(subscriber);
        System.out.println("MessageBroker: " + subscriber.getSubscriberId() + " subscribed to " + topic);
    }
    
    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
            System.out.println("MessageBroker: " + subscriber.getSubscriberId() + " unsubscribed from " + topic);
        }
    }
    
    public void publish(Message message) {
        System.out.println("MessageBroker: Publishing " + message);
        
        List<Subscriber> subscribers = topicSubscribers.get(message.getTopic());
        if (subscribers != null) {
            for (Subscriber subscriber : subscribers) {
                try {
                    subscriber.onMessage(message);
                } catch (Exception e) {
                    System.err.println("MessageBroker: Error delivering to " + subscriber.getSubscriberId() + ": " + e.getMessage());
                }
            }
        } else {
            System.out.println("MessageBroker: No subscribers for topic " + message.getTopic());
        }
    }
}