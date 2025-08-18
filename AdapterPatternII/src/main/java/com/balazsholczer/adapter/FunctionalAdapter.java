package com.balazsholczer.adapter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Functional approach to adapter pattern using lambda expressions
 */
public class FunctionalAdapter {
    
    public record APICall<T, R>(String name, Function<T, R> implementation) {
        public R execute(T input) {
            System.out.println("FunctionalAdapter: Executing " + name);
            return implementation.apply(input);
        }
    }
    
    private final StarTeamV14API v14API;
    public final APICall<String, Void> checkoutFile;
    public final APICall<CheckinRequest, Void> checkinFile;
    public final APICall<String, List<String>> getFileHistory;
    public final APICall<String, String> getFileStatus;
    
    public FunctionalAdapter(StarTeamV14API v14API) {
        this.v14API = v14API;
        
        // Initialize functional adapters after v14API is set
        this.checkoutFile = new APICall<>(
            "checkoutFile",
            fileName -> {
                v14API.checkoutFileWithOptions(fileName, Map.of("overwrite", true));
                return null;
            }
        );
        
        this.checkinFile = new APICall<>(
            "checkinFile",
            request -> {
                v14API.checkinFileWithMetadata(request.fileName(), request.comment(), 
                                             Map.of("adapter", "functional"));
                return null;
            }
        );
        
        this.getFileHistory = new APICall<>(
            "getFileHistory",
            fileName -> v14API.getDetailedFileHistory(fileName, 10)
                              .stream()
                              .map(entry -> (String) entry.get("version"))
                              .toList()
        );
        
        this.getFileStatus = new APICall<>(
            "getFileStatus",
            fileName -> (String) v14API.getFileStatusWithDetails(fileName).get("status")
        );
    }
    
    public record CheckinRequest(String fileName, String comment) {}
}