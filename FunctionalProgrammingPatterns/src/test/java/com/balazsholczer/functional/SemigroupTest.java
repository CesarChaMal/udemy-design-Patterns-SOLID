package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SemigroupTest {

    @Test
    void testStringSemigroup() {
        Semigroup<String> stringSemigroup = new Semigroup<>(String::concat);
        assertEquals("HelloWorld", stringSemigroup.combine("Hello", "World"));
    }

    @Test
    void testStringConcatSemigroup() {
        Semigroup<String> stringSemigroup = Semigroup.stringConcat();
        assertEquals("HelloWorld", stringSemigroup.combine("Hello", "World"));
    }

    @Test
    void testIntegerSumSemigroup() {
        Semigroup<Integer> sumSemigroup = new Semigroup<>(Integer::sum);
        assertEquals(7, sumSemigroup.combine(3, 4));
    }

    @Test
    void testIntegerMaxSemigroup() {
        Semigroup<Integer> maxSemigroup = Semigroup.integerMax();
        assertEquals(5, maxSemigroup.combine(3, 5));
        assertEquals(7, maxSemigroup.combine(7, 2));
    }

    @Test
    void testIntegerMinSemigroup() {
        Semigroup<Integer> minSemigroup = Semigroup.integerMin();
        assertEquals(3, minSemigroup.combine(3, 5));
        assertEquals(2, minSemigroup.combine(7, 2));
    }

    @Test
    void testSemigroupAssociativity() {
        Semigroup<String> semigroup = new Semigroup<>(String::concat);
        String a = "A";
        String b = "B";
        String c = "C";
        
        // Associativity: (a + b) + c = a + (b + c)
        String left = semigroup.combine(semigroup.combine(a, b), c);
        String right = semigroup.combine(a, semigroup.combine(b, c));
        assertEquals(left, right);
        assertEquals("ABC", left);
    }
    
    @Test
    void testSemigroupCombineAll() {
        Semigroup<Integer> maxSemigroup = Semigroup.integerMax();
        ImmutableList<Integer> numbers = ImmutableList.of(3, 1, 4, 1, 5, 9, 2, 6);
        
        Integer max = maxSemigroup.combineAll(numbers);
        assertEquals(9, max);
    }
    
    @Test
    void testSemigroupCombineAllEmpty() {
        Semigroup<Integer> maxSemigroup = Semigroup.integerMax();
        ImmutableList<Integer> empty = ImmutableList.empty();
        
        assertThrows(IllegalArgumentException.class, () -> {
            maxSemigroup.combineAll(empty);
        });
    }
}