package com.balazsholczer.pubsub;

public interface Subscriber {
    void onMessage(Message message);
    String getSubscriberId();
}