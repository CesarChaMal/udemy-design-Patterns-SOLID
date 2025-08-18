package com.balazsholczer.chainofresponsibility;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamChain {
    
    public record RequestRecord(String type, String content, int priority) {}
    
    public record Handler(String name, Predicate<RequestRecord> condition, Function<RequestRecord, String> processor) {
        
        public Optional<String> tryHandle(RequestRecord request) {
            if (condition.test(request)) {
                return Optional.of(processor.apply(request));
            }
            return Optional.empty();
        }
    }
    
    private static final List<Handler> HANDLERS = List.of(
        new Handler("INFO", req -> "INFO".equals(req.type()), 
                   req -> "StreamChain: Processed INFO - " + req.content()),
        new Handler("WARNING", req -> "WARNING".equals(req.type()), 
                   req -> "StreamChain: Processed WARNING - " + req.content()),
        new Handler("ERROR", req -> "ERROR".equals(req.type()), 
                   req -> "StreamChain: Processed ERROR - " + req.content()),
        new Handler("PRIORITY", req -> req.priority() > 5, 
                   req -> "StreamChain: High priority request - " + req.content())
    );
    
    public static void processRequest(RequestRecord request) {
        String result = HANDLERS.stream()
                               .map(handler -> handler.tryHandle(request))
                               .filter(Optional::isPresent)
                               .map(Optional::get)
                               .findFirst()
                               .orElse("StreamChain: No handler found for " + request);
        
        System.out.println(result);
    }
    
    public static void processAllHandlers(RequestRecord request) {
        List<String> results = HANDLERS.stream()
                                      .map(handler -> handler.tryHandle(request))
                                      .filter(Optional::isPresent)
                                      .map(Optional::get)
                                      .toList();
        
        if (results.isEmpty()) {
            System.out.println("StreamChain: No handlers matched " + request);
        } else {
            results.forEach(System.out::println);
        }
    }
}