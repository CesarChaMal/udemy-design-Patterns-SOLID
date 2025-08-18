package com.balazsholczer.adapter;

import java.util.List;
import java.util.Map;

/**
 * New StarTeam API Version 14
 * Updated API with changed method signatures and new parameters
 */
public class StarTeamV14API {
    
    // New method signatures in Version 14 - incompatible with V9
    public boolean checkoutFileWithOptions(String filePath, Map<String, Object> options) {
        System.out.println("StarTeam V14: Checking out file: " + filePath + " with options: " + options);
        return true;
    }
    
    public boolean checkinFileWithMetadata(String filePath, String comment, Map<String, String> metadata) {
        System.out.println("StarTeam V14: Checking in file: " + filePath + " with comment: " + comment + " and metadata: " + metadata);
        return true;
    }
    
    public List<Map<String, Object>> getDetailedFileHistory(String filePath, int maxEntries) {
        System.out.println("StarTeam V14: Getting detailed history for: " + filePath + " (max " + maxEntries + " entries)");
        return List.of(
            Map.of("version", "v1.0", "author", "john", "date", "2023-01-01"),
            Map.of("version", "v1.1", "author", "jane", "date", "2023-02-01"),
            Map.of("version", "v1.2", "author", "bob", "date", "2023-03-01")
        );
    }
    
    public boolean acquireFileLock(String filePath, String lockType, long timeoutMs) {
        System.out.println("StarTeam V14: Acquiring " + lockType + " lock for: " + filePath + " (timeout: " + timeoutMs + "ms)");
        return true;
    }
    
    public boolean releaseFileLock(String filePath, boolean force) {
        System.out.println("StarTeam V14: Releasing lock for: " + filePath + " (force: " + force + ")");
        return true;
    }
    
    public Map<String, Object> getFileStatusWithDetails(String filePath) {
        System.out.println("StarTeam V14: Getting detailed status for: " + filePath);
        return Map.of(
            "status", "CURRENT",
            "lastModified", "2023-03-15",
            "size", 1024,
            "checksum", "abc123"
        );
    }
}