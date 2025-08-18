package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Stream<T> {

    public abstract boolean isEmpty();
    public abstract T head();
    public abstract Stream<T> tail();

    /* ----- Construction ----- */

    public static <T> Stream<T> empty() {
        return new Empty<>();
    }

    public static <T> Stream<T> cons(Supplier<T> head, Supplier<Stream<T>> tail) {
        return new Cons<>(head, tail);
    }

    public static Stream<Integer> fibonacci() {
        return fib(0, 1);
    }

    public static Stream<Integer> fib(int a, int b) {
        return cons(() -> a, () -> fib(b, a + b));
    }

    public static Stream<Integer> from(int startInclusive) {
        return cons(() -> startInclusive, () -> from(startInclusive + 1));
    }

    public static <T> Stream<T> iterate(T seed, Function<T, T> f) {
        return cons(() -> seed, () -> iterate(f.apply(seed), f));
    }

    /* ----- Functor / Filter ----- */

    public <U> Stream<U> map(Function<T, U> mapper) {
        if (isEmpty()) return empty();
        return cons(() -> mapper.apply(head()), () -> tail().map(mapper));
    }

    public Stream<T> filter(Predicate<T> predicate) {
        if (isEmpty()) return this;
        return predicate.test(head())
                ? cons(() -> head(), () -> tail().filter(predicate))
                : tail().filter(predicate);
    }

    /* ----- Materialization / Taking ----- */

    // REQUIRED by tests: produce ImmutableList of first n (or fewer if stream shorter/empty)
    public ImmutableList<T> take(int n) {
        if (n <= 0 || isEmpty()) return ImmutableList.empty();
        // Build reversed then reverse once (iterative for stack safety)
        ImmutableList<T> acc = ImmutableList.empty();
        Stream<T> cur = this;
        int k = n;
        while (k > 0 && !cur.isEmpty()) {
            acc = acc.prepend(cur.head());
            cur = cur.tail();
            k--;
        }
        return acc.reverse();
    }

    // Original behavior (truncate stream lazily) renamed to keep demo code working
    public Stream<T> limit(int n) {
        if (n <= 0 || isEmpty()) return empty();
        return cons(() -> head(), () -> tail().limit(n - 1));
    }

    public ImmutableList<T> toImmutableList(int n) {
        return take(n);
    }

    /* ----- Helpers ----- */

    public ImmutableList<T> toImmutableListForce(int max) {
        return take(max);
    }

    /* ----- Internal Variants ----- */

    private static final class Empty<T> extends Stream<T> {
        @Override public boolean isEmpty() { return true; }
        @Override public T head() { throw new UnsupportedOperationException("Empty stream"); }
        @Override public Stream<T> tail() { throw new UnsupportedOperationException("Empty stream"); }
    }

    private static final class Cons<T> extends Stream<T> {
        private Supplier<T> headSupplier;
        private Supplier<Stream<T>> tailSupplier;
        private T head;
        private Stream<T> tail;
        private boolean headComputed = false;
        private boolean tailComputed = false;

        Cons(Supplier<T> headSupplier, Supplier<Stream<T>> tailSupplier) {
            this.headSupplier = headSupplier;
            this.tailSupplier = tailSupplier;
        }

        @Override
        public boolean isEmpty() { return false; }

        @Override
        public T head() {
            if (!headComputed) {
                head = headSupplier.get();
                headSupplier = null;
                headComputed = true;
            }
            return head;
        }

        @Override
        public Stream<T> tail() {
            if (!tailComputed) {
                tail = tailSupplier.get();
                tailSupplier = null;
                tailComputed = true;
            }
            return tail;
        }
    }
}