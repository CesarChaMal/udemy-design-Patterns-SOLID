package com.balazsholczer.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Adapter that allows V9 client code to work with V14 API
 * This adapter enabled gradual migration over several releases
 */
public class StarTeamAdapter extends StarTeamV9API {
    
    private final StarTeamV14API v14API;
    
    public StarTeamAdapter(StarTeamV14API v14API) {
        this.v14API = v14API;
        System.out.println("StarTeamAdapter: Initialized with V14 API backend");
    }
    
    // Override V9 method to use V14 implementation
    @Override
    public void checkoutFile(String fileName) {
        Map<String, Object> defaultOptions = new HashMap<>();
        defaultOptions.put("overwrite", true);
        defaultOptions.put("recursive", false);
        
        v14API.checkoutFileWithOptions(fileName, defaultOptions);
    }
    
    // Override V9 method to use V14 implementation
    @Override
    public void checkinFile(String fileName, String comment) {
        Map<String, String> defaultMetadata = new HashMap<>();
        defaultMetadata.put("source", "legacy-adapter");
        defaultMetadata.put("migration", "v9-to-v14");
        
        v14API.checkinFileWithMetadata(fileName, comment, defaultMetadata);
    }
    
    // Override V9 method to use V14 implementation
    @Override
    public List<String> getFileHistory(String fileName) {
        List<Map<String, Object>> detailedHistory = v14API.getDetailedFileHistory(fileName, 10);
        
        // Convert detailed history back to simple version list for V9 compatibility
        return detailedHistory.stream()
                             .map(entry -> (String) entry.get("version"))
                             .collect(Collectors.toList());
    }
    
    // Override V9 method to use V14 implementation
    @Override
    public boolean lockFile(String fileName) {
        return v14API.acquireFileLock(fileName, "EXCLUSIVE", 30000); // 30 second timeout
    }
    
    // Override V9 method to use V14 implementation
    @Override
    public void unlockFile(String fileName) {
        v14API.releaseFileLock(fileName, false); // Don't force unlock
    }
    
    // Override V9 method to use V14 implementation
    @Override
    public String getFileStatus(String fileName) {
        Map<String, Object> detailedStatus = v14API.getFileStatusWithDetails(fileName);
        
        // Extract simple status for V9 compatibility
        return (String) detailedStatus.get("status");
    }
    
    // Additional method to demonstrate gradual migration capability
    public Map<String, Object> getDetailedStatus(String fileName) {
        System.out.println("StarTeamAdapter: Providing enhanced V14 functionality");
        return v14API.getFileStatusWithDetails(fileName);
    }
}