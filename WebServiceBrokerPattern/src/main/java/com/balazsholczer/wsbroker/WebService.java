package com.balazsholczer.wsbroker;

public interface WebService {
    String invoke(String request);
    String getServiceName();
}