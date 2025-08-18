package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;

class KleisliTest {

    @Test
    void testKleisliBasic() {
        Kleisli<String, Integer> kleisli = Kleisli.of(s -> Maybe.of(s.length()));
        Maybe<Integer> result = kleisli.run("Hello");
        assertTrue(result.isPresent());
        assertEquals(5, result.get());
    }

    @Test
    void testKleisliComposition() {
        Kleisli<String, Integer> length = Kleisli.of(s -> Maybe.of(s.length()));
        Kleisli<Integer, Integer> square = Kleisli.of(n -> Maybe.of(n * n));
        
        Kleisli<String, Integer> composed = length.andThen(square);
        Maybe<Integer> result = composed.run("Hello");
        
        assertTrue(result.isPresent());
        assertEquals(25, result.get()); // "Hello".length() = 5, 5^2 = 25
    }

    @Test
    void testKleisliMap() {
        Kleisli<String, Integer> kleisli = Kleisli.of(s -> Maybe.of(s.length()));
        Kleisli<String, Integer> mapped = kleisli.map(n -> n * 2);
        
        Maybe<Integer> result = mapped.run("Hello");
        assertTrue(result.isPresent());
        assertEquals(10, result.get());
    }

    @Test
    void testKleisliChain() {
        Kleisli<Integer, String> toString = Kleisli.of(n -> Maybe.of(n.toString()));
        Kleisli<String, Integer> length = Kleisli.of(s -> Maybe.of(s.length()));
        Kleisli<Integer, Integer> square = Kleisli.of(n -> Maybe.of(n * n));
        
        Kleisli<Integer, Integer> chain = toString.andThen(length).andThen(square);
        Maybe<Integer> result = chain.run(123);
        
        assertTrue(result.isPresent());
        assertEquals(9, result.get()); // 123 -> "123" -> 3 -> 9
    }

    @Test
    void testKleisliFailure() {
        Kleisli<String, Integer> parseInt = Kleisli.of(s -> {
            try {
                return Maybe.of(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                return Maybe.none();
            }
        });
        
        Maybe<Integer> success = parseInt.run("123");
        Maybe<Integer> failure = parseInt.run("abc");
        
        assertTrue(success.isPresent());
        assertEquals(123, success.get());
        assertFalse(failure.isPresent());
    }

    @Test
    void testKleisliIdentity() {
        Kleisli<String, String> identity = Kleisli.of(Maybe::of);
        Maybe<String> result = identity.run("test");
        
        assertTrue(result.isPresent());
        assertEquals("test", result.get());
    }

    @Test
    void testKleisliAssociativity() {
        Kleisli<Integer, Integer> f = Kleisli.of(x -> Maybe.of(x + 1));
        Kleisli<Integer, Integer> g = Kleisli.of(x -> Maybe.of(x * 2));
        Kleisli<Integer, Integer> h = Kleisli.of(x -> Maybe.of(x - 1));
        
        // (f >=> g) >=> h should equal f >=> (g >=> h)
        Maybe<Integer> left = f.andThen(g).andThen(h).run(5);
        Maybe<Integer> right = f.andThen(g.andThen(h)).run(5);
        
        assertEquals(left.get(), right.get());
        assertEquals(11, left.get()); // ((5+1)*2)-1 = 11
    }
}