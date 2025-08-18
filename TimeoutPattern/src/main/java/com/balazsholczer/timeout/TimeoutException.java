package com.balazsholczer.timeout;

public class TimeoutException extends Exception {
    public TimeoutException(String message) {
        super(message);
    }
    
    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}