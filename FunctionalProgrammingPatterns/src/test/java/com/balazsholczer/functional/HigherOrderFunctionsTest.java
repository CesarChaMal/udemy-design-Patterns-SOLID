package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.function.Predicate;

class HigherOrderFunctionsTest {

    @Test
    void testCompose() {
        Function<String, Integer> length = String::length;
        Function<Integer, Integer> square = x -> x * x;
        Function<String, Integer> composed = HigherOrderFunctions.compose(square, length);
        
        assertEquals(25, composed.apply("Hello"));
    }

    @Test
    void testCurry() {
        HigherOrderFunctions.Function2<Integer, Integer, Integer> add = Integer::sum;
        Function<Integer, Function<Integer, Integer>> curried = HigherOrderFunctions.curry(add);
        Function<Integer, Integer> add5 = curried.apply(5);
        
        assertEquals(8, add5.apply(3));
    }

    @Test
    void testPartialApplication() {
        HigherOrderFunctions.Function2<String, String, String> concat = (a, b) -> a + b;
        Function<String, String> addHello = HigherOrderFunctions.partial(concat, "Hello ");
        
        assertEquals("Hello World", addHello.apply("World"));
    }

    @Test
    void testMemoization() {
        Function<Integer, Integer> fibonacci = HigherOrderFunctions.memoize(this::fib);
        
        long start = System.nanoTime();
        int result1 = fibonacci.apply(35);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        int result2 = fibonacci.apply(35);
        long time2 = System.nanoTime() - start;
        
        assertEquals(result1, result2);
        assertTrue(time2 < time1 / 10); // Memoized should be much faster
    }

    private int fib(int n) {
        if (n <= 1) return n;
        return fib(n - 1) + fib(n - 2);
    }

    @Test
    void testUncurry() {
        Function<Integer, Function<Integer, Integer>> curried = a -> b -> a + b;
        HigherOrderFunctions.Function2<Integer, Integer, Integer> uncurried = 
            HigherOrderFunctions.uncurry(curried);
        
        assertEquals(8, uncurried.apply(3, 5));
    }

    @Test
    void testFlip() {
        HigherOrderFunctions.Function2<String, Integer, String> repeat = (s, n) -> s.repeat(n);
        HigherOrderFunctions.Function2<Integer, String, String> flipped = 
            HigherOrderFunctions.flip(repeat);
        
        assertEquals("aaaa", flipped.apply(4, "a"));
    }

    @Test
    void testIdentity() {
        Function<String, String> id = HigherOrderFunctions.identity();
        assertEquals("test", id.apply("test"));
    }

    @Test
    void testConstant() {
        Function<String, Integer> constant42 = HigherOrderFunctions.constant(42);
        assertEquals(42, constant42.apply("anything"));
        assertEquals(42, constant42.apply("something else"));
    }

    @Test
    void testPipe() {
        Function<Integer, Integer> addOne = x -> x + 1;
        Function<Integer, Integer> square = x -> x * x;
        Function<Integer, Integer> subtractTwo = x -> x - 2;
        
        Function<Integer, Integer> pipeline = HigherOrderFunctions.pipe(
            addOne,
            square,
            subtractTwo
        );
        
        assertEquals(34, pipeline.apply(5)); // 5 -> 6 -> 36 -> 34
    }

    @Test
    void testFunctionComposition() {
        Function<String, String> upper = String::toUpperCase;
        Function<String, String> exclaim = s -> s + "!";
        Function<String, Integer> length = String::length;
        
        Function<String, Integer> composed = HigherOrderFunctions.compose(
            HigherOrderFunctions.compose(length, exclaim),
            upper
        );
        
        assertEquals(6, composed.apply("hello")); // "hello" -> "HELLO" -> "HELLO!" -> 6
    }

    @Test
    void testMemoizationWithDifferentInputs() {
        int[] callCount = {0};
        Function<Integer, Integer> expensive = HigherOrderFunctions.memoize(n -> {
            callCount[0]++;
            return n * n;
        });
        
        assertEquals(25, expensive.apply(5));
        assertEquals(1, callCount[0]);
        
        assertEquals(25, expensive.apply(5));
        assertEquals(1, callCount[0]);
        
        assertEquals(36, expensive.apply(6));
        assertEquals(2, callCount[0]);
        
        assertEquals(36, expensive.apply(6));
        assertEquals(2, callCount[0]);
    }

    @Test
    void testCreateMultiplier() {
        UnaryOperator<Integer> double_ = HigherOrderFunctions.createMultiplier(2);
        UnaryOperator<Integer> triple = HigherOrderFunctions.createMultiplier(3);
        
        assertEquals(10, double_.apply(5));
        assertEquals(15, triple.apply(5));
    }

    @Test
    void testPredicateCombinators() {
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        
        Predicate<Integer> isEvenAndPositive = HigherOrderFunctions.and(isEven, isPositive);
        Predicate<Integer> isEvenOrPositive = HigherOrderFunctions.or(isEven, isPositive);
        Predicate<Integer> isOdd = HigherOrderFunctions.not(isEven);
        
        assertTrue(isEvenAndPositive.test(4));
        assertFalse(isEvenAndPositive.test(-2));
        assertFalse(isEvenAndPositive.test(3));
        
        assertTrue(isEvenOrPositive.test(4));
        assertTrue(isEvenOrPositive.test(-2));
        assertTrue(isEvenOrPositive.test(3));
        assertFalse(isEvenOrPositive.test(-3));
        
        assertTrue(isOdd.test(3));
        assertFalse(isOdd.test(4));
    }

    @Test
    void testPartialApplicationChaining() {
        HigherOrderFunctions.Function2<Integer, Integer, Integer> add = Integer::sum;
        HigherOrderFunctions.Function2<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        
        Function<Integer, Integer> add10 = HigherOrderFunctions.partial(add, 10);
        Function<Integer, Integer> multiplyBy3 = HigherOrderFunctions.partial(multiply, 3);
        
        Function<Integer, Integer> addThenMultiply = HigherOrderFunctions.compose(multiplyBy3, add10);
        
        assertEquals(45, addThenMultiply.apply(5));
    }
}