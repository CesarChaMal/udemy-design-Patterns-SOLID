package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ComonadTest {

    @Test
    void testComonadExtract() {
        Comonad<String> comonad = Comonad.identity("Hello");
        assertEquals("Hello", comonad.extract());
    }

    @Test
    void testComonadExtend() {
        Comonad<Integer> comonad = Comonad.identity(5);
        Comonad<Integer> extended = comonad.extend(c -> c.extract() * 2);
        assertEquals(10, extended.extract());
    }

    @Test
    void testComonadMap() {
        Comonad<String> comonad = Comonad.identity("Hello");
        Comonad<Integer> mapped = comonad.map(String::length);
        assertEquals(5, mapped.extract());
    }

    @Test
    void testComonadChaining() {
        Comonad<Integer> comonad = Comonad.identity(10);
        
        Comonad<String> result = comonad
            .map(x -> x * 2)  // 20
            .extend(c -> "Value: " + c.extract());
        
        assertEquals("Value: 20", result.extract());
    }
    
    @Test
    void testComonadExtendWithContext() {
        Comonad<String> comonad = Comonad.identity("World");
        
        // Extend uses the entire comonad context, not just the value
        Comonad<String> extended = comonad.extend(c -> "Hello, " + c.extract() + "!");
        
        assertEquals("Hello, World!", extended.extract());
    }
    
    @Test
    void testComonadLaws() {
        Comonad<Integer> comonad = Comonad.identity(42);
        
        // Left identity: extend(extract) = id
        Comonad<Integer> leftIdentity = comonad.extend(Comonad::extract);
        assertEquals(comonad.extract(), leftIdentity.extract());
        
        // Right identity: extract(extend(f)) = f(comonad)

        Function<Comonad<Integer>, String> f = c -> "Value: " + c.extract();
        Comonad<String> extended = comonad.extend(f);
        assertEquals(f.apply(comonad), extended.extract());
    }
}