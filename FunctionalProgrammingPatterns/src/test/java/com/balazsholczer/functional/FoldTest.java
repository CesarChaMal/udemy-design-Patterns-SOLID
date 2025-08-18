package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoldTest {

    @Test
    void testFoldSum() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4, 5);
        Integer sum = Fold.foldLeft(list, 0, Integer::sum);
        assertEquals(15, sum);
    }

    @Test
    void testFoldProduct() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4);
        Integer product = Fold.foldLeft(list, 1, (a, b) -> a * b);
        assertEquals(24, product);
    }

    @Test
    void testFoldConcat() {
        ImmutableList<String> list = ImmutableList.of("Hello", " ", "World");
        String result = Fold.foldLeft(list, "", String::concat);
        assertEquals("Hello World", result);
    }

    @Test
    void testFoldRight() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        String result = Fold.foldRight(list, "", (a, b) -> a + b);
        assertEquals("123", result);
    }

    @Test
    void testFoldEmpty() {
        ImmutableList<Integer> empty = ImmutableList.empty();
        Integer sum = Fold.foldLeft(empty, 0, Integer::sum);
        assertEquals(0, sum);
    }

    @Test
    void testReduce() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4);
        Integer result = Fold.reduce(list, Integer::sum);
        assertEquals(10, result);
    }

    @Test
    void testReduceEmpty() {
        ImmutableList<Integer> empty = ImmutableList.empty();
        assertThrows(IllegalArgumentException.class, () -> {
            Fold.reduce(empty, Integer::sum);
        });
    }

    @Test
    void testFoldAssociativity() {
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3, 4);
        
        Integer leftSum = Fold.foldLeft(list, 0, Integer::sum);
        Integer rightSum = Fold.foldRight(list, 0, (a, b) -> a + b);
        
        assertEquals(leftSum, rightSum);
        assertEquals(10, leftSum);
    }
}