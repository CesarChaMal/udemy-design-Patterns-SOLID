package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LazyTest {

    @Test
    void testLazyEvaluation() {
        Lazy<String> lazy = Lazy.of(() -> "Computed");
        assertFalse(lazy.isComputed());
        
        String value = lazy.get();
        assertEquals("Computed", value);
        assertTrue(lazy.isComputed());
    }

    @Test
    void testLazyMap() {
        Lazy<String> lazy = Lazy.of(() -> "Hello");
        Lazy<Integer> mapped = lazy.map(String::length);
        
        assertFalse(lazy.isComputed());
        assertFalse(mapped.isComputed());
        
        assertEquals(5, mapped.get());
        assertTrue(lazy.isComputed());
        assertTrue(mapped.isComputed());
    }

    @Test
    void testLazyFlatMap() {
        Lazy<String> lazy = Lazy.of(() -> "Hello");
        Lazy<Integer> flatMapped = lazy.flatMap(s -> Lazy.of(() -> s.length()));
        
        assertFalse(lazy.isComputed());
        assertEquals(5, flatMapped.get());
        assertTrue(lazy.isComputed());
    }

    @Test
    void testLazyMemoization() {
        int[] counter = {0};
        Lazy<String> lazy = Lazy.of(() -> {
            counter[0]++;
            return "Computed";
        });
        
        assertEquals("Computed", lazy.get());
        assertEquals(1, counter[0]);
        
        assertEquals("Computed", lazy.get()); // Should not recompute
        assertEquals(1, counter[0]);
    }

    @Test
    void testLazyChaining() {
        Lazy<String> result = Lazy.of(() -> "hello")
            .map(String::toUpperCase)
            .flatMap(s -> Lazy.of(() -> s + "!"))
            .map(s -> s + s);
        
        assertEquals("HELLO!HELLO!", result.get());
    }

    @Test
    void testLazyException() {
        Lazy<String> errorLazy = Lazy.of(() -> {
            throw new RuntimeException("Lazy error");
        });
        
        assertThrows(RuntimeException.class, () -> errorLazy.get());
    }
}