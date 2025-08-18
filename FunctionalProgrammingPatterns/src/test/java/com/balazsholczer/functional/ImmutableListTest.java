package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ImmutableListTest {

    @Test
    void testEmptyList() {
        ImmutableList<Integer> empty = ImmutableList.empty();
        assertTrue(empty.isEmpty());
        assertEquals(0, empty.size());
    }

    @Test
    void testOfElements() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertFalse(list.isEmpty());
        assertEquals(3, list.size());
        assertEquals(1, list.head());
    }

    @Test
    void testPrepend() {
        ImmutableList<Integer> list = ImmutableList.of(2, 3);
        ImmutableList<Integer> newList = list.prepend(1);
        
        assertEquals(2, list.size());
        assertEquals(3, newList.size());
        assertEquals(1, newList.head());
    }

    @Test
    void testMap() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        ImmutableList<Integer> doubled = list.map(x -> x * 2);
        
        assertEquals(3, doubled.size());
        assertEquals(2, doubled.head());
    }

    @Test
    void testFilter() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        ImmutableList<Integer> evens = list.filter(x -> x % 2 == 0);
        
        assertEquals(2, evens.size());
        assertEquals(2, evens.head());
    }

    @Test
    void testFoldLeft() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4);
        Integer sum = list.foldLeft(0, Integer::sum);
        assertEquals(10, sum);
    }

    @Test
    void testGet() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
    }

    @Test
    void testGetOutOfBounds() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
    }

    @Test
    void testReverse() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        ImmutableList<Integer> reversed = list.reverse();
        assertEquals(3, reversed.head());
        assertEquals(2, reversed.tail().head());
        assertEquals(1, reversed.tail().tail().head());
    }

    @Test
    void testTake() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        ImmutableList<Integer> taken = list.take(3);
        assertEquals(3, taken.size());
        assertEquals(1, taken.head());
    }

    @Test
    void testEquality() {
        ImmutableList<Integer> list1 = ImmutableList.of(1, 2, 3);
        ImmutableList<Integer> list2 = ImmutableList.of(1, 2, 3);
        ImmutableList<Integer> list3 = ImmutableList.of(1, 2, 4);
        
        assertEquals(list1, list2);
        assertNotEquals(list1, list3);
        assertEquals(list1.hashCode(), list2.hashCode());
    }
}