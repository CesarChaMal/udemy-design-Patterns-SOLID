package com.balazsholczer.functional;

import java.util.function.Function;

/**
 * Unfold Pattern - builds an immutable list from a seed value by repeatedly
 * applying a step function that may produce the next element and new seed.
 *
 * Core contract:
 *   unfold(seed, step)
 * where step: S -> Maybe<UnfoldResult<A,S>>
 * Returning None terminates the construction.
 */
public final class Unfold {

    private Unfold() {}

    /**
     * Holder for one produced element plus the next seed.
     * A = output element type
     * S = next seed type
     */
    public static final class UnfoldResult<A, S> {
        private final A value;
        private final S next;

        private UnfoldResult(A value, S next) {
            this.value = value;
            this.next = next;
        }

        public static <A, S> UnfoldResult<A, S> of(A value, S next) {
            return new UnfoldResult<>(value, next);
        }

        public A value() { return value; }
        public S next() { return next; }
    }

    /**
     * Generic unfold. Repeatedly applies step starting from seed until step returns None.
     */
    public static <A, S> ImmutableList<A> unfold(S seed,
                                                 Function<S, Maybe<UnfoldResult<A, S>>> step) {
        ImmutableList<A> acc = ImmutableList.empty();
        S current = seed;
        while (true) {
            Maybe<UnfoldResult<A, S>> m = step.apply(current);
            if (!m.isPresent()) break;
            UnfoldResult<A, S> r = m.get();
            acc = acc.prepend(r.value());
            current = r.next();
        }
        return acc.reverse();
    }

    /**
     * Builds an integer range [start, end) (end exclusive).
     */
    public static ImmutableList<Integer> range(int startInclusive, int endExclusive) {
        if (startInclusive >= endExclusive) return ImmutableList.empty();
        return unfold(startInclusive,
                n -> n < endExclusive
                        ? Maybe.some(UnfoldResult.of(n, n + 1))
                        : Maybe.none());
    }

    /**
     * Generate a finite sequence of length count starting from seed,
     * applying nextFn to obtain each subsequent element.
     * Example: generate(2, x->x*2, 5) => [2,4,8,16,32]
     */
    public static <T> ImmutableList<T> generate(T seed, Function<T, T> nextFn, int count) {
        if (count <= 0) return ImmutableList.empty();
        // Use unfold with (value,countRemaining)
        record State<T>(T value, int remaining) {}
        ImmutableList<T> list = unfold(new State<>(seed, count),
                st -> st.remaining() > 0
                        ? Maybe.some(UnfoldResult.of(st.value(),
                        new State<>(nextFn.apply(st.value()), st.remaining() - 1)))
                        : Maybe.none());
        return list;
    }
}