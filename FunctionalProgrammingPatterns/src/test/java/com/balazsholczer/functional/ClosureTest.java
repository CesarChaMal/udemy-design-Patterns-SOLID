package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;
import java.util.function.Supplier;

class ClosureTest {

    @Test
    void testCounterClosure() {
        Supplier<Integer> counter = Closure.createCounter(0);
        
        assertEquals(1, counter.get());
        assertEquals(2, counter.get());
        assertEquals(3, counter.get());
        
        // Each counter maintains its own state
        Supplier<Integer> counter2 = Closure.createCounter(10);
        assertEquals(11, counter2.get());
        assertEquals(4, counter.get()); // Original counter unaffected
    }

    @Test
    void testMultiplierClosure() {
        Function<Integer, Integer> double_ = Closure.createMultiplier(2);
        Function<Integer, Integer> triple = Closure.createMultiplier(3);
        
        assertEquals(10, double_.apply(5));
        assertEquals(15, triple.apply(5));
    }

    @Test
    void testAccumulatorClosure() {
        Function<Integer, Integer> accumulator = Closure.createAccumulator(0);
        
        assertEquals(5, accumulator.apply(5));
        assertEquals(8, accumulator.apply(3));
        assertEquals(10, accumulator.apply(2));
    }

    @Test
    void testProcessorClosure() {
        Function<String, String> processor = Closure.createProcessor("[", "]", true);
        
        assertEquals("[HELLO]", processor.apply("hello"));
        assertEquals("[WORLD]", processor.apply("world"));
        
        Function<String, String> simpleProcessor = Closure.createProcessor("", "!", false);
        assertEquals("test!", simpleProcessor.apply("test"));
    }

    @Test
    void testMemoizedClosure() {
        int[] callCount = {0};
        Function<Integer, Integer> expensive = x -> {
            callCount[0]++;
            return x * x;
        };
        
        Function<Integer, Integer> memoized = Closure.createMemoized(expensive);
        
        assertEquals(25, memoized.apply(5));
        assertEquals(1, callCount[0]);
        
        assertEquals(25, memoized.apply(5)); // Should use cache
        assertEquals(1, callCount[0]);
        
        assertEquals(36, memoized.apply(6)); // New computation
        assertEquals(2, callCount[0]);
    }

    @Test
    void testRateLimitedClosure() {
        Function<String, String> identity = Function.identity();
        Function<String, String> rateLimited = Closure.createRateLimited(identity, 100);
        
        assertEquals("test", rateLimited.apply("test"));
        
        assertThrows(RuntimeException.class, () -> {
            rateLimited.apply("test2"); // Should be rate limited
        });
    }

    @Test
    void testRetryableClosure() {
        int[] attempts = {0};
        Function<String, String> flaky = input -> {
            attempts[0]++;
            if (attempts[0] < 3) {
                throw new RuntimeException("Temporary failure");
            }
            return "Success: " + input;
        };
        
        Function<String, String> retryable = Closure.createRetryable(flaky, 3);
        
        assertEquals("Success: test", retryable.apply("test"));
        assertEquals(3, attempts[0]);
    }

    @Test
    void testComposeClosure() {
        Function<String, Integer> length = String::length;
        Function<Integer, Integer> square = x -> x * x;
        Function<String, Integer> lengthSquared = Closure.compose(square, length);
        
        assertEquals(25, lengthSquared.apply("hello"));
    }

    @Test
    void testCurryClosure() {
        java.util.function.BiFunction<Integer, Integer, Integer> add = Integer::sum;
        Function<Integer, Function<Integer, Integer>> curriedAdd = Closure.curry(add);
        
        Function<Integer, Integer> add5 = curriedAdd.apply(5);
        assertEquals(8, add5.apply(3));
    }

    @Test
    void testPartialClosure() {
        java.util.function.BiFunction<String, String, String> concat = (a, b) -> a + b;
        Function<String, String> addHello = Closure.partial(concat, "Hello ");
        
        assertEquals("Hello World", addHello.apply("World"));
    }

    @Test
    void testClosureDemo() {
        Closure.ClosureDemo demo = new Closure.ClosureDemo("Alice");
        
        Function<String, String> greeter = demo.createGreeter();
        assertEquals("Hello Bob, I'm Alice", greeter.apply("Bob"));
        
        Supplier<String> nameSupplier = demo.createNameSupplier();
        assertEquals("Alice", nameSupplier.get());
    }

    @Test
    void testClosureLexicalScoping() {
        // Demonstrate that closures capture variables from their lexical scope
        String prefix = "Prefix: ";
        Function<String, String> addPrefix = input -> prefix + input;
        
        assertEquals("Prefix: test", addPrefix.apply("test"));
        
        // Even if we change the local variable, the closure retains the original value
        // (Note: In Java, effectively final variables are captured by value)
    }

    @Test
    void testClosureStateIsolation() {
        // Each closure instance maintains its own state
        Supplier<Integer> counter1 = Closure.createCounter(0);
        Supplier<Integer> counter2 = Closure.createCounter(100);
        
        assertEquals(1, counter1.get());
        assertEquals(101, counter2.get());
        assertEquals(2, counter1.get());
        assertEquals(102, counter2.get());
        
        // Counters are completely isolated
        assertNotEquals(counter1.get(), counter2.get());
    }
}