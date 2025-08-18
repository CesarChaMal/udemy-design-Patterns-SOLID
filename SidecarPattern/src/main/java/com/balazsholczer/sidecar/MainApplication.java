package com.balazsholczer.sidecar;

import java.util.ArrayList;
import java.util.List;

public class MainApplication {
    private final String applicationName;
    private final List<Sidecar> sidecars = new ArrayList<>();
    private LoggingSidecar loggingSidecar;
    private MetricsSidecar metricsSidecar;
    
    public MainApplication(String applicationName) {
        this.applicationName = applicationName;
        setupSidecars();
    }
    
    private void setupSidecars() {
        loggingSidecar = new LoggingSidecar();
        metricsSidecar = new MetricsSidecar();
        
        sidecars.add(loggingSidecar);
        sidecars.add(metricsSidecar);
    }
    
    public void start() {
        System.out.println("MainApplication: Starting " + applicationName);
        
        // Start all sidecars
        for (Sidecar sidecar : sidecars) {
            sidecar.start();
        }
        
        System.out.println("MainApplication: " + applicationName + " started with " + sidecars.size() + " sidecars");
    }
    
    public void stop() {
        System.out.println("MainApplication: Stopping " + applicationName);
        
        // Stop all sidecars
        for (Sidecar sidecar : sidecars) {
            sidecar.stop();
        }
        
        System.out.println("MainApplication: " + applicationName + " stopped");
    }
    
    public void processRequest(String request) {
        System.out.println("MainApplication: Processing request - " + request);
        
        // Log the request
        loggingSidecar.collectLogs("Request processed: " + request);
        
        // Record metrics
        metricsSidecar.recordMetric("requests_processed");
        
        // Simulate processing
        System.out.println("MainApplication: Request processed successfully");
    }
}