package com.balazsholczer.sidecar;

import java.util.HashMap;
import java.util.Map;

public class MetricsSidecar implements Sidecar {
    private boolean running = false;
    private final Map<String, Integer> metrics = new HashMap<>();
    
    @Override
    public void start() {
        running = true;
        System.out.println("MetricsSidecar: Started - collecting metrics");
    }
    
    @Override
    public void stop() {
        running = false;
        System.out.println("MetricsSidecar: Stopped");
    }
    
    @Override
    public String getName() {
        return "MetricsSidecar";
    }
    
    public void recordMetric(String metricName) {
        if (running) {
            metrics.put(metricName, metrics.getOrDefault(metricName, 0) + 1);
            System.out.println("MetricsSidecar: Recorded " + metricName + " = " + metrics.get(metricName));
            exportMetrics();
        }
    }
    
    private void exportMetrics() {
        System.out.println("MetricsSidecar: Exported metrics to monitoring system");
    }
}