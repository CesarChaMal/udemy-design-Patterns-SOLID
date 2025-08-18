package com.balazsholczer.mediator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamMediator {
    
    public record StreamMessage(String id, String type, Object payload, String sender, long timestamp) {
        public StreamMessage(String type, Object payload, String sender) {
            this(java.util.UUID.randomUUID().toString(), type, payload, sender, System.currentTimeMillis());
        }
    }
    
    public static class MessageSubscriber implements Flow.Subscriber<StreamMessage> {
        private final String subscriberId;
        private final Predicate<StreamMessage> filter;
        private Flow.Subscription subscription;
        
        public MessageSubscriber(String subscriberId, Predicate<StreamMessage> filter) {
            this.subscriberId = subscriberId;
            this.filter = filter;
        }
        
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
            System.out.println("StreamMediator: " + subscriberId + " subscribed");
        }
        
        @Override
        public void onNext(StreamMessage message) {
            if (filter.test(message)) {
                System.out.println(subscriberId + " processed: " + message.type() + " from " + message.sender());
            }
            subscription.request(1);
        }
        
        @Override
        public void onError(Throwable throwable) {
            System.err.println(subscriberId + " error: " + throwable.getMessage());
        }
        
        @Override
        public void onComplete() {
            System.out.println(subscriberId + " completed");
        }
    }
    
    private final SubmissionPublisher<StreamMessage> publisher = new SubmissionPublisher<>();
    private final Map<String, MessageSubscriber> subscribers = new ConcurrentHashMap<>();
    
    public void subscribe(String subscriberId, Predicate<StreamMessage> filter) {
        MessageSubscriber subscriber = new MessageSubscriber(subscriberId, filter);
        subscribers.put(subscriberId, subscriber);
        publisher.subscribe(subscriber);
    }
    
    public void unsubscribe(String subscriberId) {
        MessageSubscriber subscriber = subscribers.remove(subscriberId);
        if (subscriber != null && subscriber.subscription != null) {
            subscriber.subscription.cancel();
            System.out.println("StreamMediator: " + subscriberId + " unsubscribed");
        }
    }
    
    public void publish(StreamMessage message) {
        System.out.println("StreamMediator: Publishing " + message.type() + " from " + message.sender());
        publisher.submit(message);
    }
    
    public void publish(String type, Object payload, String sender) {
        publish(new StreamMessage(type, payload, sender));
    }
    
    public void close() {
        publisher.close();
    }
    
    // Utility methods for common filters
    public static Predicate<StreamMessage> typeFilter(String type) {
        return msg -> type.equals(msg.type());
    }
    
    public static Predicate<StreamMessage> senderFilter(String sender) {
        return msg -> sender.equals(msg.sender());
    }
    
    public static Predicate<StreamMessage> excludeSender(String sender) {
        return msg -> !sender.equals(msg.sender());
    }
    
    public List<String> getActiveSubscribers() {
        return subscribers.keySet().stream().collect(Collectors.toList());
    }
}