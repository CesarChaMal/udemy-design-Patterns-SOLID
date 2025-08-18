package com.balazsholczer.pagecontroller;

public class HttpResponse {
    private int statusCode;
    private String content;
    private String contentType;
    
    public HttpResponse(int statusCode, String content, String contentType) {
        this.statusCode = statusCode;
        this.content = content;
        this.contentType = contentType;
    }
    
    public int getStatusCode() { return statusCode; }
    public String getContent() { return content; }
    public String getContentType() { return contentType; }
    
    @Override
    public String toString() {
        return "HttpResponse{status=" + statusCode + ", contentType='" + contentType + "'}";
    }
}