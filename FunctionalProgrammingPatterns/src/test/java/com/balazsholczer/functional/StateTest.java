package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void testStateBasic() {
        State<Integer, String> state = s -> new State.Pair<>("Value: " + s, s + 1);
        
        State.Pair<String, Integer> result = state.run(0);
        assertEquals("Value: 0", result.first());
        assertEquals(1, result.second());
    }

    @Test
    void testStateMap() {
        State<Integer, String> state = s -> new State.Pair<>("Hello", s);
        State<Integer, Integer> mapped = state.map(String::length);
        
        State.Pair<Integer, Integer> result = mapped.run(42);
        assertEquals(5, result.first());
        assertEquals(42, result.second());
    }

    @Test
    void testStateFlatMap() {
        State<Integer, Integer> increment = s -> new State.Pair<>(s, s + 1);
        State<Integer, Integer> double_ = s -> new State.Pair<>(s * 2, s);
        
        State<Integer, Integer> combined = increment.flatMap(v -> double_);
        
        State.Pair<Integer, Integer> result = combined.run(5);
        assertEquals(12, result.first()); // 6 * 2 = 12 (increment first: 5->6, then double: 6*2=12)
        assertEquals(6, result.second());
    }

    @Test
    void testStateSequence() {
        State<Integer, Integer> increment = s -> new State.Pair<>(s, s + 1);
        
        State<Integer, Integer> sequence = increment
            .flatMap(v1 -> increment
            .flatMap(v2 -> increment
            .map(v3 -> v1 + v2 + v3)));
        
        State.Pair<Integer, Integer> result = sequence.run(0);
        assertEquals(3, result.first()); // 0 + 1 + 2 = 3
        assertEquals(3, result.second());
    }
    
    @Test
    void testStatePure() {
        State<String, Integer> pureState = State.pure(42);
        
        State.Pair<Integer, String> result = pureState.run("test");
        assertEquals(42, result.first());
        assertEquals("test", result.second());
    }
    
    @Test
    void testStateGet() {
        State<Integer, Integer> getState = State.get();
        
        State.Pair<Integer, Integer> result = getState.run(100);
        assertEquals(100, result.first());
        assertEquals(100, result.second());
    }
    
    @Test
    void testStatePut() {
        State<Integer, Void> putState = State.put(200);
        
        State.Pair<Void, Integer> result = putState.run(100);
        assertNull(result.first());
        assertEquals(200, result.second());
    }
}