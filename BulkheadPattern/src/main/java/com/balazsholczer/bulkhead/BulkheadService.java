package com.balazsholczer.bulkhead;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class BulkheadService {
    private final Map<String, ResourcePool> resourcePools = new HashMap<>();
    
    public void createBulkhead(String serviceName, int poolSize) {
        ResourcePool pool = new ResourcePool(serviceName + "Pool", poolSize);
        resourcePools.put(serviceName, pool);
        System.out.println("BulkheadService: Created bulkhead for " + serviceName);
    }
    
    public Future<String> executeTask(String serviceName, ServiceTask task) {
        ResourcePool pool = resourcePools.get(serviceName);
        if (pool == null) {
            throw new IllegalArgumentException("No bulkhead found for service: " + serviceName);
        }
        
        System.out.println("BulkheadService: Executing task in " + pool.getName());
        return pool.submit(task);
    }
    
    public void shutdown() {
        System.out.println("BulkheadService: Shutting down all bulkheads");
        resourcePools.values().forEach(ResourcePool::shutdown);
    }
}