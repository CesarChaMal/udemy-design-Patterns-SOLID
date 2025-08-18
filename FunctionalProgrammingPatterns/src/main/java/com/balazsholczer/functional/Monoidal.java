package com.balazsholczer.functional;

import java.util.function.BinaryOperator;

/**
 * Monoidal structure: associative binary operation + identity element.
 */
public class Monoidal<T> {

    private final T identity;
    private final BinaryOperator<T> operation;

    /**
     * Primary constructor supplying identity and associative operation.
     */
    public Monoidal(T identity, BinaryOperator<T> operation) {
        this.identity = identity;
        this.operation = operation;
    }

    /**
     * No-arg constructor (needed by tests).
     * Defaults to String concatenation monoid with identity "".
     * Safe to use only with Monoidal\<String\>.
     */
    @SuppressWarnings("unchecked")
    public Monoidal() {
        this.identity = (T) "";
        this.operation = (a, b) -> (T) (((String) a) + ((String) b));
    }

    public T identity() {
        return identity;
    }

    public T combine(T a, T b) {
        return operation.apply(a, b);
    }

    public T combineAll(ImmutableList<T> list) {
        T acc = identity;
        ImmutableList<T> cur = list;
        while (!cur.isEmpty()) {
            acc = combine(acc, cur.head());
            cur = cur.tail();
        }
        return acc;
    }

    /* Factory helpers */

    public static Monoidal<Integer> integerAddition() {
        return new Monoidal<>(0, Integer::sum);
    }

    public static Monoidal<Integer> integerMultiplication() {
        return new Monoidal<>(1, (a, b) -> a * b);
    }

    public static Monoidal<String> stringConcatenation() {
        return new Monoidal<>("", (a, b) -> a + b);
    }

    @Override
    public String toString() {
        return "Monoidal(identity=" + identity + ")";
    }
}