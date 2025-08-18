package com.balazsholczer.filter;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode = 200;
    private String content;
    private Map<String, String> headers = new HashMap<>();
    
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public void setHeader(String name, String value) { headers.put(name, value); }
    public String getHeader(String name) { return headers.get(name); }
    
    @Override
    public String toString() {
        return "Response{status=" + statusCode + ", content='" + content + "'}";
    }
}