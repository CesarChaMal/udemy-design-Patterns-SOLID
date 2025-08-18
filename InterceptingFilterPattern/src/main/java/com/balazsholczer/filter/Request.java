package com.balazsholczer.filter;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String uri;
    private String method;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, Object> attributes = new HashMap<>();
    
    public Request(String uri, String method) {
        this.uri = uri;
        this.method = method;
    }
    
    public String getUri() { return uri; }
    public String getMethod() { return method; }
    
    public void setHeader(String name, String value) { headers.put(name, value); }
    public String getHeader(String name) { return headers.get(name); }
    
    public void setAttribute(String name, Object value) { attributes.put(name, value); }
    public Object getAttribute(String name) { return attributes.get(name); }
    
    @Override
    public String toString() {
        return method + " " + uri;
    }
}