package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;

class FunctorTest {

    @Test
    void testBoxFunctor() {
        Box<String> box = new Box<>("Hello");
        Box<Integer> mapped = box.map(String::length);
        
        assertEquals("Hello", box.getValue());
        assertEquals(5, mapped.getValue());
    }

    @Test
    void testFunctorLaws() {
        Box<String> box = new Box<>("Hello");
        
        // Identity law: fmap(id) = id
        Box<String> identity = box.map(Function.identity());
        assertEquals(box.getValue(), identity.getValue());
        
        // Composition law: fmap(f . g) = fmap(f) . fmap(g)
        Function<String, Integer> f = String::length;
        Function<Integer, String> g = Object::toString;
        
        Box<String> composed1 = box.map(f).map(g);
        Box<String> composed2 = box.map(f.andThen(g));
        
        assertEquals(composed1.getValue(), composed2.getValue());
    }

    @Test
    void testMaybeFunctor() {
        Maybe<String> some = Maybe.of("Hello");
        Maybe<String> none = Maybe.none();
        
        Maybe<Integer> mappedSome = some.map(String::length);
        Maybe<Integer> mappedNone = none.map(String::length);
        
        assertTrue(mappedSome.isPresent());
        assertEquals(5, mappedSome.get());
        assertFalse(mappedNone.isPresent());
    }
}