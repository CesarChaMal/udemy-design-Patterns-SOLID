package com.balazsholczer.eventsourcing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStore {
    private final Map<String, List<Event>> events = new HashMap<>();
    
    public void saveEvent(Event event) {
        events.computeIfAbsent(event.getAggregateId(), k -> new ArrayList<>()).add(event);
        System.out.println("EventStore: Saved " + event.getEventType() + " for " + event.getAggregateId());
    }
    
    public List<Event> getEvents(String aggregateId) {
        return events.getOrDefault(aggregateId, new ArrayList<>());
    }
    
    public List<Event> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();
        events.values().forEach(allEvents::addAll);
        return allEvents;
    }
}