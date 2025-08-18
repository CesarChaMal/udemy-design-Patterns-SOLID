package com.balazsholczer.pagecontroller;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String method;
    private String path;
    private Map<String, String> parameters;
    
    public HttpRequest(String method, String path) {
        this.method = method;
        this.path = path;
        this.parameters = new HashMap<>();
    }
    
    public String getMethod() { return method; }
    public String getPath() { return path; }
    public Map<String, String> getParameters() { return parameters; }
    
    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }
    
    public String getParameter(String key) {
        return parameters.get(key);
    }
    
    @Override
    public String toString() {
        return method + " " + path;
    }
}