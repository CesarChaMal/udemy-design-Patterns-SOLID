import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

class CircuitBreakerTest {

    private CircuitBreaker circuitBreaker;
    private AtomicInteger callCount;

    @BeforeEach
    void setUp() {
        circuitBreaker = new CircuitBreaker(3, 1000); // 3 failures, 1 second timeout
        callCount = new AtomicInteger(0);
    }

    @Test
    void testSuccessfulCall() {
        String result = circuitBreaker.call(() -> {
            callCount.incrementAndGet();
            return "Success";
        });
        
        assertEquals("Success", result);
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
        assertEquals(1, callCount.get());
    }

    @Test
    void testFailureIncrementsCount() {
        assertThrows(RuntimeException.class, () -> {
            circuitBreaker.call(() -> {
                callCount.incrementAndGet();
                throw new RuntimeException("Service failure");
            });
        });
        
        assertEquals(1, circuitBreaker.getFailureCount());
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }

    @Test
    void testCircuitOpensAfterThreshold() {
        // Cause 3 failures to open the circuit
        for (int i = 0; i < 3; i++) {
            assertThrows(RuntimeException.class, () -> {
                circuitBreaker.call(() -> {
                    throw new RuntimeException("Service failure");
                });
            });
        }
        
        assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
    }

    @Test
    void testOpenCircuitRejectsCalls() {
        // Open the circuit
        for (int i = 0; i < 3; i++) {
            assertThrows(RuntimeException.class, () -> {
                circuitBreaker.call(() -> {
                    throw new RuntimeException("Service failure");
                });
            });
        }
        
        // Next call should be rejected immediately
        assertThrows(CircuitBreaker.CircuitOpenException.class, () -> {
            circuitBreaker.call(() -> {
                callCount.incrementAndGet();
                return "Should not be called";
            });
        });
        
        assertEquals(0, callCount.get()); // Should not have been called
    }

    @Test
    void testHalfOpenState() throws InterruptedException {
        // Open the circuit
        for (int i = 0; i < 3; i++) {
            assertThrows(RuntimeException.class, () -> {
                circuitBreaker.call(() -> {
                    throw new RuntimeException("Service failure");
                });
            });
        }
        
        // Wait for timeout
        Thread.sleep(1100);
        
        // Next call should transition to HALF_OPEN
        String result = circuitBreaker.call(() -> "Recovery");
        
        assertEquals("Recovery", result);
        assertEquals(CircuitBreaker.State.CLOSED, circuitBreaker.getState());
        assertEquals(0, circuitBreaker.getFailureCount());
    }

    @Test
    void testAsyncCall() {
        CompletableFuture<String> future = circuitBreaker.callAsync(() -> 
            CompletableFuture.completedFuture("Async Success")
        );
        
        assertEquals("Async Success", future.join());
    }

    @Test
    void testMetrics() {
        // Successful calls
        circuitBreaker.call(() -> "Success 1");
        circuitBreaker.call(() -> "Success 2");
        
        // Failed call
        assertThrows(RuntimeException.class, () -> {
            circuitBreaker.call(() -> {
                throw new RuntimeException("Failure");
            });
        });
        
        assertEquals(3, circuitBreaker.getTotalCalls());
        assertEquals(2, circuitBreaker.getSuccessfulCalls());
        assertEquals(1, circuitBreaker.getFailureCount());
    }

    // Mock Circuit Breaker implementation for testing
    static class CircuitBreaker {
        enum State { CLOSED, OPEN, HALF_OPEN }
        
        private State state = State.CLOSED;
        private int failureCount = 0;
        private int totalCalls = 0;
        private int successfulCalls = 0;
        private final int failureThreshold;
        private final long timeout;
        private long lastFailureTime = 0;

        public CircuitBreaker(int failureThreshold, long timeout) {
            this.failureThreshold = failureThreshold;
            this.timeout = timeout;
        }

        public <T> T call(java.util.function.Supplier<T> supplier) {
            totalCalls++;
            
            if (state == State.OPEN) {
                if (System.currentTimeMillis() - lastFailureTime > timeout) {
                    state = State.HALF_OPEN;
                } else {
                    throw new CircuitOpenException("Circuit breaker is OPEN");
                }
            }

            try {
                T result = supplier.get();
                onSuccess();
                return result;
            } catch (Exception e) {
                onFailure();
                throw e;
            }
        }

        public <T> CompletableFuture<T> callAsync(java.util.function.Supplier<CompletableFuture<T>> supplier) {
            return CompletableFuture.supplyAsync(() -> call(supplier::get).join());
        }

        private void onSuccess() {
            successfulCalls++;
            failureCount = 0;
            state = State.CLOSED;
        }

        private void onFailure() {
            failureCount++;
            lastFailureTime = System.currentTimeMillis();
            if (failureCount >= failureThreshold) {
                state = State.OPEN;
            }
        }

        public State getState() { return state; }
        public int getFailureCount() { return failureCount; }
        public int getTotalCalls() { return totalCalls; }
        public int getSuccessfulCalls() { return successfulCalls; }

        static class CircuitOpenException extends RuntimeException {
            public CircuitOpenException(String message) {
                super(message);
            }
        }
    }
}