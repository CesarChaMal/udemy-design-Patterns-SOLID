package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StreamTest {

    @Test
    void testInfiniteStream() {
        Stream<Integer> naturals = Stream.iterate(1, n -> n + 1);
        
        assertEquals(1, naturals.head());
        assertEquals(2, naturals.tail().head());
        assertEquals(3, naturals.tail().tail().head());
    }

    @Test
    void testStreamMap() {
        Stream<Integer> naturals = Stream.iterate(1, n -> n + 1);
        Stream<Integer> doubled = naturals.map(x -> x * 2);
        
        assertEquals(2, doubled.head());
        assertEquals(4, doubled.tail().head());
        assertEquals(6, doubled.tail().tail().head());
    }

    @Test
    void testStreamFilter() {
        Stream<Integer> naturals = Stream.iterate(1, n -> n + 1);
        Stream<Integer> evens = naturals.filter(x -> x % 2 == 0);
        
        assertEquals(2, evens.head());
        assertEquals(4, evens.tail().head());
        assertEquals(6, evens.tail().tail().head());
    }

    @Test
    void testStreamTake() {
        Stream<Integer> naturals = Stream.iterate(1, n -> n + 1);
        ImmutableList<Integer> first5 = naturals.take(5);
        
        assertEquals(5, first5.size());
        assertEquals(1, first5.head());
    }

    @Test
    void testFibonacciStream() {
        Stream<Integer> fibs = Stream.fibonacci();
        
        assertEquals(0, fibs.head());
        assertEquals(1, fibs.tail().head());
        assertEquals(1, fibs.tail().tail().head());
        assertEquals(2, fibs.tail().tail().tail().head());
        assertEquals(3, fibs.tail().tail().tail().tail().head());
    }

    @Test
    void testStreamLimit() {
        Stream<Integer> naturals = Stream.iterate(1, n -> n + 1);
        Stream<Integer> limited = naturals.limit(3);
        
        assertEquals(1, limited.head());
        assertEquals(2, limited.tail().head());
        assertEquals(3, limited.tail().tail().head());
        assertTrue(limited.tail().tail().tail().isEmpty());
    }

    @Test
    void testStreamFrom() {
        Stream<Integer> from10 = Stream.from(10);
        
        assertEquals(10, from10.head());
        assertEquals(11, from10.tail().head());
        assertEquals(12, from10.tail().tail().head());
    }

    @Test
    void testStreamEmpty() {
        Stream<Integer> empty = Stream.empty();
        
        assertTrue(empty.isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> empty.head());
        assertThrows(UnsupportedOperationException.class, () -> empty.tail());
    }

    @Test
    void testStreamLazyEvaluation() {
        int[] counter = {0};
        Stream<Integer> lazy = Stream.iterate(1, n -> {
            counter[0]++;
            return n + 1;
        });
        
        assertEquals(0, counter[0]);
        assertEquals(1, lazy.head());
        assertEquals(0, counter[0]);
        assertEquals(2, lazy.tail().head());
        assertEquals(1, counter[0]);
    }

    @Test
    void testStreamToImmutableList() {
        Stream<Integer> naturals = Stream.iterate(1, n -> n + 1);
        ImmutableList<Integer> first5 = naturals.toImmutableList(5);
        
        assertEquals(5, first5.size());
        assertEquals(1, first5.head());
        assertEquals(5, first5.reverse().head());
    }
}