package com.balazsholczer.pubsub;

import java.time.LocalDateTime;

public class Message {
    private final String topic;
    private final String content;
    private final LocalDateTime timestamp;
    
    public Message(String topic, String content) {
        this.topic = topic;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getTopic() { return topic; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return "Message{topic='" + topic + "', content='" + content + "'}";
    }
}