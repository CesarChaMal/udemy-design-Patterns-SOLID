package com.balazsholczer.eventstore;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventStore {
    private final Map<String, List<StoredEvent>> streams = new ConcurrentHashMap<>();
    private final List<StoredEvent> globalEventLog = new ArrayList<>();
    
    public void appendEvent(String streamId, Object event) {
        StoredEvent storedEvent = new StoredEvent(
            UUID.randomUUID().toString(),
            streamId,
            event.getClass().getSimpleName(),
            event,
            LocalDateTime.now(),
            getNextVersion(streamId)
        );
        
        streams.computeIfAbsent(streamId, k -> new ArrayList<>()).add(storedEvent);
        globalEventLog.add(storedEvent);
        
        System.out.println("EventStore: Appended " + storedEvent.getEventType() + 
                          " to stream " + streamId + " (version " + storedEvent.getVersion() + ")");
    }
    
    public List<StoredEvent> getEventStream(String streamId) {
        return streams.getOrDefault(streamId, new ArrayList<>());
    }
    
    public List<StoredEvent> getEventStream(String streamId, long fromVersion) {
        return streams.getOrDefault(streamId, new ArrayList<>())
                .stream()
                .filter(event -> event.getVersion() >= fromVersion)
                .collect(Collectors.toList());
    }
    
    public List<StoredEvent> getAllEvents() {
        return new ArrayList<>(globalEventLog);
    }
    
    public List<StoredEvent> getEventsByType(String eventType) {
        return globalEventLog.stream()
                .filter(event -> event.getEventType().equals(eventType))
                .collect(Collectors.toList());
    }
    
    private long getNextVersion(String streamId) {
        List<StoredEvent> stream = streams.get(streamId);
        return stream == null ? 1 : stream.size() + 1;
    }
    
    public static class StoredEvent {
        private final String eventId;
        private final String streamId;
        private final String eventType;
        private final Object eventData;
        private final LocalDateTime timestamp;
        private final long version;
        
        public StoredEvent(String eventId, String streamId, String eventType, 
                          Object eventData, LocalDateTime timestamp, long version) {
            this.eventId = eventId;
            this.streamId = streamId;
            this.eventType = eventType;
            this.eventData = eventData;
            this.timestamp = timestamp;
            this.version = version;
        }
        
        public String getEventId() { return eventId; }
        public String getStreamId() { return streamId; }
        public String getEventType() { return eventType; }
        public Object getEventData() { return eventData; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public long getVersion() { return version; }
        
        @Override
        public String toString() {
            return "StoredEvent{eventType='" + eventType + "', streamId='" + streamId + 
                   "', version=" + version + ", timestamp=" + timestamp + "}";
        }
    }
}