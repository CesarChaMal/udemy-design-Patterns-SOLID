package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonoidTest {

    @Test
    void testStringMonoid() {
        Monoidal<String> stringMonoid = new Monoidal<String>() {
            @Override
            public String identity() {
                return "";
            }
            
            @Override
            public String combine(String a, String b) {
                return a + b;
            }
        };
        
        assertEquals("", stringMonoid.identity());
        assertEquals("HelloWorld", stringMonoid.combine("Hello", "World"));
    }

    @Test
    void testIntegerSumMonoid() {
        Monoidal<Integer> sumMonoid = new Monoidal<Integer>() {
            @Override
            public Integer identity() {
                return 0;
            }
            
            @Override
            public Integer combine(Integer a, Integer b) {
                return a + b;
            }
        };
        
        assertEquals(0, sumMonoid.identity());
        assertEquals(7, sumMonoid.combine(3, 4));
    }

    @Test
    void testMonoidLaws() {
        Monoidal<String> stringMonoid = new Monoidal<String>() {
            @Override
            public String identity() {
                return "";
            }
            
            @Override
            public String combine(String a, String b) {
                return a + b;
            }
        };
        
        String a = "Hello";
        String b = "World";
        String c = "!";
        
        // Left identity: identity + a = a
        assertEquals(a, stringMonoid.combine(stringMonoid.identity(), a));
        
        // Right identity: a + identity = a
        assertEquals(a, stringMonoid.combine(a, stringMonoid.identity()));
        
        // Associativity: (a + b) + c = a + (b + c)
        String left = stringMonoid.combine(stringMonoid.combine(a, b), c);
        String right = stringMonoid.combine(a, stringMonoid.combine(b, c));
        assertEquals(left, right);
    }
}