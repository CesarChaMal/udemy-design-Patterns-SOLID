package com.balazsholczer.dbperservice;

import java.util.HashMap;
import java.util.Map;

public abstract class Database {
    protected final String serviceName;
    protected final Map<String, Object> data = new HashMap<>();
    
    public Database(String serviceName) {
        this.serviceName = serviceName;
        System.out.println("Database: Initialized " + serviceName + " database");
    }
    
    public void save(String key, Object value) {
        data.put(key, value);
        System.out.println(serviceName + "DB: Saved " + key);
    }
    
    public Object find(String key) {
        Object result = data.get(key);
        System.out.println(serviceName + "DB: Retrieved " + key + " = " + result);
        return result;
    }
    
    public void delete(String key) {
        data.remove(key);
        System.out.println(serviceName + "DB: Deleted " + key);
    }
    
    public String getServiceName() {
        return serviceName;
    }
}