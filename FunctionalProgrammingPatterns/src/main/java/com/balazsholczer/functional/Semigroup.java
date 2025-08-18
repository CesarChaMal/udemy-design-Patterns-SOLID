package com.balazsholczer.functional;

import java.util.function.BinaryOperator;

public class Semigroup<T> {
    private final BinaryOperator<T> operation;

    public Semigroup(BinaryOperator<T> operation) {
        this.operation = operation;
    }

    public T combine(T a, T b) {
        return operation.apply(a, b);
    }

    // Reduce a non-empty ImmutableList using the semigroup operation
    public T combineAll(ImmutableList<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Semigroup.combineAll requires a non-empty list");
        }
        T acc = list.head();
        ImmutableList<T> cur = list.tail();
        while (!cur.isEmpty()) {
            acc = combine(acc, cur.head());
            cur = cur.tail();
        }
        return acc;
    }

    // Integer max semigroup
    public static Semigroup<Integer> integerMax() {
        return new Semigroup<>(Integer::max);
    }

    // Integer min semigroup (missing one causing the compile error)
    public static Semigroup<Integer> integerMin() {
        return new Semigroup<>(Integer::min);
    }

    // String concatenation semigroup (optional utility)
    public static Semigroup<String> stringConcat() {
        return new Semigroup<>((a, b) -> a + b);
    }
}