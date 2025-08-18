package com.balazsholczer.circuitbreaker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircuitBreakerTest {

    @Test
    void testCircuitBreakerClosed() {
        CircuitBreaker breaker = new CircuitBreaker(3, 5000);
        assertEquals(CircuitBreaker.State.CLOSED, breaker.getState());
    }

    @Test
    void testCircuitBreakerOpen() {
        CircuitBreaker breaker = new CircuitBreaker(1, 5000);
        breaker.recordFailure();
        breaker.recordFailure();
        assertEquals(CircuitBreaker.State.OPEN, breaker.getState());
    }

    @Test
    void testCircuitBreakerHalfOpen() {
        CircuitBreaker breaker = new CircuitBreaker(1, 100);
        breaker.recordFailure();
        breaker.recordFailure();
        
        try { Thread.sleep(150); } catch (InterruptedException e) {}
        
        assertEquals(CircuitBreaker.State.HALF_OPEN, breaker.getState());
    }
}