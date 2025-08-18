package com.balazsholczer.frontcontroller;

import java.util.HashMap;
import java.util.Map;

public class Request {
    
    private final String uri;
    private final String method;
    private final Map<String, String> parameters;
    private final Map<String, Object> attributes;
    
    public Request(String uri, String method) {
        this.uri = uri;
        this.method = method;
        this.parameters = new HashMap<>();
        this.attributes = new HashMap<>();
    }
    
    public String getUri() { return uri; }
    public String getMethod() { return method; }
    
    public void setParameter(String name, String value) {
        parameters.put(name, value);
    }
    
    public String getParameter(String name) {
        return parameters.get(name);
    }
    
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
    
    public Object getAttribute(String name) {
        return attributes.get(name);
    }
    
    @Override
    public String toString() {
        return method + " " + uri + " params=" + parameters;
    }
}