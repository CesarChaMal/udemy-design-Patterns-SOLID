package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrampolineTest {

    @Test
    void testTrampolineBasic() {
        Trampoline<Integer> result = factorial(5, 1);
        assertEquals(120, result.run());
    }

    @Test
    void testTrampolineLargeNumber() {
        // Test with smaller number to avoid overflow
        Trampoline<Long> result = factorialLong(10L, 1L);
        Long value = result.run();
        assertNotNull(value);
        assertEquals(3628800L, value); // 10! = 3628800
    }

    @Test
    void testTrampolineEven() {
        Trampoline<Boolean> result = isEven(100000);
        assertTrue(result.run());
        
        Trampoline<Boolean> result2 = isEven(100001);
        assertFalse(result2.run());
    }

    @Test
    void testTrampolineSum() {
        Trampoline<Integer> result = sum(1000, 0);
        assertEquals(500500, result.run()); // Sum of 1 to 1000
    }

    private Trampoline<Integer> factorial(int n, int acc) {
        if (n <= 1) {
            return Trampoline.done(acc);
        }
        return Trampoline.more(() -> factorial(n - 1, n * acc));
    }

    private Trampoline<Long> factorialLong(long n, long acc) {
        if (n <= 1) {
            return Trampoline.done(acc);
        }
        return Trampoline.more(() -> factorialLong(n - 1, n * acc));
    }

    private Trampoline<Boolean> isEven(int n) {
        if (n == 0) {
            return Trampoline.done(true);
        }
        if (n == 1) {
            return Trampoline.done(false);
        }
        return Trampoline.more(() -> isEven(n - 2));
    }

    private Trampoline<Integer> sum(int n, int acc) {
        if (n <= 0) {
            return Trampoline.done(acc);
        }
        return Trampoline.more(() -> sum(n - 1, acc + n));
    }
}