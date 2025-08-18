package com.balazsholczer.apigateway;

public interface MicroService {
    Response handleRequest(Request request);
    String getServiceName();
}