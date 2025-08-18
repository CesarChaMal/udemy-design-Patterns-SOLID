package com.balazsholczer.frontcontroller;

public class Response {
    
    private String content;
    private int statusCode = 200;
    private String contentType = "text/html";
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    @Override
    public String toString() {
        return "Response{status=" + statusCode + ", type=" + contentType + ", content='" + content + "'}";
    }
}