package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;

class ProfunctorTest {

    @Test
    void testProfunctorBasic() {
        Profunctor<String, Integer> prof = Profunctor.of(String::length);
        assertEquals(5, prof.run("Hello"));
    }

    @Test
    void testProfunctorDimap() {
        Profunctor<Integer, Integer> square = Profunctor.of(n -> n * n);
        
        // Contramap on input (String -> Integer), map on output (Integer -> String)
        Profunctor<String, String> dimapped = square.dimap(
            String::length,  // String -> Integer (contravariant)
            Object::toString // Integer -> String (covariant)
        );
        
        assertEquals("25", dimapped.run("Hello")); // "Hello".length() = 5, 5^2 = 25, "25"
    }

    @Test
    void testProfunctorLmap() {
        Profunctor<Integer, Integer> square = Profunctor.of(n -> n * n);
        Profunctor<String, Integer> lmapped = square.lmap(String::length);
        
        assertEquals(25, lmapped.run("Hello"));
    }

    @Test
    void testProfunctorRmap() {
        Profunctor<String, Integer> length = Profunctor.of(String::length);
        Profunctor<String, String> rmapped = length.rmap(Object::toString);
        
        assertEquals("5", rmapped.run("Hello"));
    }

    @Test
    void testProfunctorLaws() {
        Profunctor<String, Integer> prof = Profunctor.of(String::length);
        
        // Identity law: dimap(id, id) = id
        Profunctor<String, Integer> identity = prof.dimap(
            Function.<String>identity(),
            Function.<Integer>identity()
        );
        assertEquals(prof.run("test"), identity.run("test"));
    }

    @Test
    void testProfunctorChaining() {
        Profunctor<String, Integer> base = Profunctor.of(String::length);
        
        Profunctor<Object, Integer> step1 = base.lmap(Object::toString);
        Profunctor<Object, String> result = step1.rmap(len -> "Length: " + len);
        
        assertEquals("Length: 3", result.run(123));
    }

    @Test
    void testProfunctorContravariance() {
        Profunctor<String, Integer> stringLength = Profunctor.of(String::length);
        Profunctor<Object, Integer> objectLength = stringLength.lmap(Object::toString);
        
        assertEquals(3, objectLength.run(123));
        assertEquals(4, objectLength.run(true));
        assertEquals(5, objectLength.run("hello"));
    }

    @Test
    void testProfunctorCovariance() {
        // Profunctor is covariant in second argument
        Profunctor<String, Integer> stringLength = Profunctor.of(String::length);
        
        // We can transform the output
        Profunctor<String, Boolean> isLongString = stringLength.rmap(len -> len > 5);
        
        assertFalse(isLongString.run("short"));
        assertTrue(isLongString.run("this is a long string"));
    }
}