package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FreeTest {

    @Test
    void testFreePure() {
        Free<String> free = Free.pure("Hello");
        assertTrue(free.isPure());
        assertEquals("Hello", free.getValue());
    }

    @Test
    void testFreeMap() {
        Free<String> free = Free.pure("Hello");
        Free<Integer> mapped = free.map(String::length);
        assertTrue(mapped.isPure());
        assertEquals(5, mapped.getValue());
    }

    @Test
    void testFreeFlatMap() {
        Free<String> free = Free.pure("Hello");
        Free<String> flatMapped = free.flatMap(s -> Free.pure(s + " World"));
        assertFalse(flatMapped.isPure()); // FlatMapped is not pure
        assertEquals("Hello World", flatMapped.interpret()); // Use interpret() instead of getValue()
    }

    @Test
    void testFreeInterpret() {
        Free<Integer> program = Free.pure(5)
            .flatMap(x -> Free.pure(x * 2))
            .flatMap(x -> Free.pure(x + 1));
        
        Integer result = program.interpret();
        assertEquals(11, result);
    }
}