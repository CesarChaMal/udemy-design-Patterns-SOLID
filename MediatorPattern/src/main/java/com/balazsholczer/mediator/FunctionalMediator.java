package com.balazsholczer.mediator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FunctionalMediator {
    
    public record Message(String content, String from, String to) {}
    
    public record Participant(String id, Consumer<Message> messageHandler) {
        public void send(String content, String to, FunctionalMediator mediator) {
            mediator.route(new Message(content, id, to));
        }
    }
    
    private final Map<String, Participant> participants = new ConcurrentHashMap<>();
    private final BiConsumer<String, Message> logger;
    
    public FunctionalMediator(BiConsumer<String, Message> logger) {
        this.logger = logger;
    }
    
    public FunctionalMediator() {
        this((action, msg) -> System.out.println("FunctionalMediator: " + action + " - " + msg));
    }
    
    public void register(Participant participant) {
        participants.put(participant.id(), participant);
        logger.accept("REGISTER", new Message("", participant.id(), ""));
    }
    
    public void unregister(String participantId) {
        participants.remove(participantId);
        logger.accept("UNREGISTER", new Message("", participantId, ""));
    }
    
    public void route(Message message) {
        logger.accept("ROUTE", message);
        
        if ("ALL".equals(message.to())) {
            // Broadcast to all except sender
            participants.values().stream()
                      .filter(p -> !p.id().equals(message.from()))
                      .forEach(p -> p.messageHandler().accept(message));
        } else {
            // Direct message
            Participant recipient = participants.get(message.to());
            if (recipient != null) {
                recipient.messageHandler().accept(message);
            } else {
                logger.accept("ERROR", new Message("Recipient not found: " + message.to(), "SYSTEM", message.from()));
            }
        }
    }
    
    public static Participant createParticipant(String id, Consumer<Message> handler) {
        return new Participant(id, handler);
    }
    
    public static Participant createParticipant(String id) {
        return new Participant(id, msg -> 
            System.out.println(id + " received: '" + msg.content() + "' from " + msg.from()));
    }
}