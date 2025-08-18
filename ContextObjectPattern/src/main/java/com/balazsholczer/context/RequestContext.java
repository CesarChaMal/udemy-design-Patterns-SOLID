package com.balazsholczer.context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RequestContext {
    
    private final String requestId;
    private final LocalDateTime timestamp;
    private final Map<String, Object> attributes;
    private String userId;
    private String sessionId;
    private String clientIP;
    private String userAgent;
    
    public RequestContext() {
        this.requestId = "REQ-" + System.currentTimeMillis();
        this.timestamp = LocalDateTime.now();
        this.attributes = new HashMap<>();
        System.out.println("RequestContext: Created context " + requestId);
    }
    
    public String getRequestId() { return requestId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { 
        this.userId = userId;
        System.out.println("RequestContext: Set userId - " + userId);
    }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { 
        this.sessionId = sessionId;
        System.out.println("RequestContext: Set sessionId - " + sessionId);
    }
    
    public String getClientIP() { return clientIP; }
    public void setClientIP(String clientIP) { 
        this.clientIP = clientIP;
        System.out.println("RequestContext: Set clientIP - " + clientIP);
    }
    
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { 
        this.userAgent = userAgent;
        System.out.println("RequestContext: Set userAgent - " + userAgent);
    }
    
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
        System.out.println("RequestContext: Set attribute " + name + " = " + value);
    }
    
    public Object getAttribute(String name) {
        return attributes.get(name);
    }
    
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }
    
    public void removeAttribute(String name) {
        attributes.remove(name);
        System.out.println("RequestContext: Removed attribute " + name);
    }
    
    @Override
    public String toString() {
        return "RequestContext{id='" + requestId + "', userId='" + userId + 
               "', sessionId='" + sessionId + "', attributes=" + attributes.size() + "}";
    }
}