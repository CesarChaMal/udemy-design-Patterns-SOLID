package com.balazsholczer.apigateway;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode;
    private String body;
    private Map<String, String> headers;
    
    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = new HashMap<>();
    }
    
    public int getStatusCode() { return statusCode; }
    public String getBody() { return body; }
    public Map<String, String> getHeaders() { return headers; }
    
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    
    @Override
    public String toString() {
        return "Response{status=" + statusCode + ", body='" + body + "'}";
    }
}