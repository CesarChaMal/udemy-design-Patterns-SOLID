package com.balazsholczer.apigateway;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String path;
    private String method;
    private Map<String, String> headers;
    private String body;
    
    public Request(String path, String method) {
        this.path = path;
        this.method = method;
        this.headers = new HashMap<>();
    }
    
    public String getPath() { return path; }
    public String getMethod() { return method; }
    public Map<String, String> getHeaders() { return headers; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    
    @Override
    public String toString() {
        return method + " " + path;
    }
}