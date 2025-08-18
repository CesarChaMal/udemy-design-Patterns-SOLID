package com.balazsholczer.eventsourcing;

import java.time.LocalDateTime;

public abstract class Event {
    private final String aggregateId;
    private final LocalDateTime timestamp;
    
    public Event(String aggregateId) {
        this.aggregateId = aggregateId;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getAggregateId() { return aggregateId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public abstract String getEventType();
}