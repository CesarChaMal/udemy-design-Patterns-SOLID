package com.balazsholczer.functional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnfoldTest {

    @Test
    void testUnfoldRange() {
        ImmutableList<Integer> range = Unfold.unfold(1, n -> n <= 5 ? 
            Maybe.of(Unfold.UnfoldResult.of(n, n + 1)) : Maybe.none());
        assertEquals(5, range.size());
        assertEquals(1, range.head());
    }

    @Test
    void testUnfoldFibonacci() {
        ImmutableList<Integer> fibs = Unfold.unfold(
            new int[]{0, 1}, 
            pair -> pair[0] <= 100 ? 
                Maybe.of(Unfold.UnfoldResult.of(pair[0], new int[]{pair[1], pair[0] + pair[1]})) : 
                Maybe.none()
        );
        assertTrue(fibs.size() > 5);
        assertEquals(0, fibs.head());
    }

    @Test
    void testUnfoldPowers() {
        ImmutableList<Integer> powers = Unfold.unfold(1, n -> n <= 64 ? 
            Maybe.of(Unfold.UnfoldResult.of(n, n * 2)) : Maybe.none());
        assertEquals(7, powers.size()); // 1, 2, 4, 8, 16, 32, 64
        assertEquals(1, powers.head());
    }

    @Test
    void testUnfoldEmpty() {
        ImmutableList<Integer> empty = Unfold.unfold(0, n -> Maybe.none());
        assertTrue(empty.isEmpty());
    }

    @Test
    void testUnfoldFactorials() {
        record State(int n, int factorial) {}
        
        ImmutableList<Integer> factorials = Unfold.unfold(
            new State(1, 1),
            state -> state.n <= 5 ?
                Maybe.of(Unfold.UnfoldResult.of(
                    state.factorial,
                    new State(state.n + 1, state.factorial * (state.n + 1))
                )) :
                Maybe.none()
        );
        
        assertEquals(5, factorials.size());
        assertEquals(1, factorials.head()); // 1!
        assertEquals(2, factorials.tail().head()); // 2!
        assertEquals(6, factorials.tail().tail().head()); // 3!
        assertEquals(24, factorials.tail().tail().tail().head()); // 4!
        assertEquals(120, factorials.tail().tail().tail().tail().head()); // 5!
    }

    @Test
    void testUnfoldString() {
        ImmutableList<Character> chars = Unfold.unfold(
            "hello",
            s -> s.isEmpty() ?
                Maybe.none() :
                Maybe.of(Unfold.UnfoldResult.of(s.charAt(0), s.substring(1)))
        );
        
        assertEquals(5, chars.size());
        assertEquals('h', chars.head());
        assertEquals('e', chars.tail().head());
        assertEquals('o', chars.reverse().head());
    }

    @Test
    void testUnfoldInfiniteToFinite() {
        // Generate squares until we exceed 50
        ImmutableList<Integer> squares = Unfold.unfold(
            1,
            n -> {
                int square = n * n;
                return square <= 50 ?
                    Maybe.of(Unfold.UnfoldResult.of(square, n + 1)) :
                    Maybe.none();
            }
        );
        
        assertEquals(7, squares.size()); // 1, 4, 9, 16, 25, 36, 49
        assertEquals(1, squares.head());
        assertEquals(49, squares.reverse().head());
    }
}