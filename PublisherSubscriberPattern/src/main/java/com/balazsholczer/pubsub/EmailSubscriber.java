package com.balazsholczer.pubsub;

public class EmailSubscriber implements Subscriber {
    private final String subscriberId;
    
    public EmailSubscriber(String subscriberId) {
        this.subscriberId = subscriberId;
    }
    
    @Override
    public void onMessage(Message message) {
        System.out.println("EmailSubscriber[" + subscriberId + "]: Sending email for " + message);
    }
    
    @Override
    public String getSubscriberId() {
        return subscriberId;
    }
}