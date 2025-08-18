package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaybeTest {

    @Test
    void testSomeValue() {
        Maybe<String> some = Maybe.of("Hello");
        assertTrue(some.isPresent());
        assertEquals("Hello", some.orElse("default"));
    }

    @Test
    void testNoneValue() {
        Maybe<String> none = Maybe.none();
        assertFalse(none.isPresent());
        assertEquals("default", none.orElse("default"));
    }

    @Test
    void testMap() {
        Maybe<String> some = Maybe.of("Hello");
        Maybe<Integer> mapped = some.map(String::length);
        assertTrue(mapped.isPresent());
        assertEquals(5, mapped.orElse(0));
    }

    @Test
    void testFlatMap() {
        Maybe<String> some = Maybe.of("Hello");
        Maybe<Integer> flatMapped = some.flatMap(s -> Maybe.of(s.length()));
        assertTrue(flatMapped.isPresent());
        assertEquals(5, flatMapped.orElse(0));
    }

    @Test
    void testFilter() {
        Maybe<String> some = Maybe.of("Hello");
        Maybe<String> filtered = some.filter(s -> s.length() > 3);
        assertTrue(filtered.isPresent());
        
        Maybe<String> filteredOut = some.filter(s -> s.length() > 10);
        assertFalse(filteredOut.isPresent());
    }

    @Test
    void testChaining() {
        Maybe<String> result = Maybe.of("hello")
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .flatMap(s -> Maybe.of(s + "!"));
        
        assertTrue(result.isPresent());
        assertEquals("HELLO!", result.get());
    }

    @Test
    void testNoneChaining() {
        Maybe<String> result = Maybe.<String>none()
            .map(String::toUpperCase)
            .filter(s -> s.length() > 3)
            .flatMap(s -> Maybe.of(s + "!"));
        
        assertFalse(result.isPresent());
    }

    @Test
    void testOrElse() {
        Maybe<String> some = Maybe.of("Hello");
        Maybe<String> none = Maybe.none();
        
        assertEquals("Hello", some.orElse("Default"));
        assertEquals("Default", none.orElse("Default"));
    }
}