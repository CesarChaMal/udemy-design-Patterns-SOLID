package com.balazsholczer.serviceactivator;

import java.time.LocalDateTime;

public class Message {
    private String id;
    private String type;
    private String payload;
    private LocalDateTime timestamp;
    
    public Message(String id, String type, String payload) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getId() { return id; }
    public String getType() { return type; }
    public String getPayload() { return payload; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return "Message{id='" + id + "', type='" + type + "', payload='" + payload + "'}";
    }
}