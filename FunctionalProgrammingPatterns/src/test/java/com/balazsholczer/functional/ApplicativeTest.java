package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.function.Function;

class ApplicativeTest {

    @Test
    void testApplicativeBasic() {
        Applicative<String> app1 = Applicative.pure("Hello");
        Applicative<String> app2 = Applicative.pure("World");
        
        Function<String, String> concat = s -> s + " World";
        Applicative<Function<String, String>> appFunc = Applicative.pure(concat);
        
        Applicative<String> result = app1.apply(appFunc);
        assertEquals("Hello World", result.getValue());
    }

    @Test
    void testApplicativeWithMaybe() {
        Maybe<Integer> some1 = Maybe.of(5);
        Maybe<Integer> some2 = Maybe.of(3);
        Maybe<Integer> none = Maybe.none();
        
        Function<Integer, Function<Integer, Integer>> add = a -> b -> a + b;
        
        // Both present
        Maybe<Integer> result1 = some1.flatMap(a -> some2.map(b -> add.apply(a).apply(b)));
        assertTrue(result1.isPresent());
        assertEquals(8, result1.get());
        
        // One absent
        Maybe<Integer> result2 = some1.flatMap(a -> none.map(b -> add.apply(a).apply(b)));
        assertFalse(result2.isPresent());
    }

    @Test
    void testApplicativeLaws() {
        Applicative<String> app = Applicative.pure("Hello");
        
        // Identity law
        Function<String, String> identity = Function.identity();
        Applicative<Function<String, String>> idApp = Applicative.pure(identity);
        Applicative<String> result = app.apply(idApp);
        
        assertEquals(app.getValue(), result.getValue());
    }

    @Test
    void testApplicativeMap() {
        Applicative<Integer> app = Applicative.pure(5);
        
        Function<Integer, Integer> multiply = x -> x * 3;
        Applicative<Integer> result = app.map(multiply);
        
        assertEquals(15, result.getValue());
    }
    
    @Test
    void testApplicativeApply() {
        Applicative<Integer> app = Applicative.pure(10);
        
        Function<Integer, String> toString = x -> "Value: " + x;
        Applicative<Function<Integer, String>> funcApp = Applicative.pure(toString);
        
        Applicative<String> result = app.apply(funcApp);
        assertEquals("Value: 10", result.getValue());
    }
}