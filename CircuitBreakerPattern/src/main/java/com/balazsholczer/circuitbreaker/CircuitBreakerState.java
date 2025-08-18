package com.balazsholczer.circuitbreaker;

public enum CircuitBreakerState {
    CLOSED,    // Normal operation
    OPEN,      // Circuit is open, calls fail fast
    HALF_OPEN  // Testing if service is back
}