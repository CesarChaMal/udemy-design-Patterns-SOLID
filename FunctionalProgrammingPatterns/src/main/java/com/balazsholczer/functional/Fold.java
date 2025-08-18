package com.balazsholczer.functional;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Fold {

    // Left fold: (((identity op x1) op x2) op x3) ...
    public static <T, R> R foldLeft(ImmutableList<T> list, R identity, BiFunction<R, T, R> accumulator) {
        R result = identity;
        ImmutableList<T> current = list;
        while (!current.isEmpty()) {
            result = accumulator.apply(result, current.head());
            current = current.tail();
        }
        return result;
    }

    // Right fold: x1 op (x2 op (x3 op identity) ...)
    public static <T, R> R foldRight(ImmutableList<T> list, R identity, BiFunction<T, R, R> f) {
        if (list.isEmpty()) return identity;
        return f.apply(list.head(), foldRight(list.tail(), identity, f));
    }

    // Reduce (non-empty list) using a binary operator
    public static <T> T reduce(ImmutableList<T> list, BinaryOperator<T> op) {
        if (list.isEmpty()) throw new IllegalArgumentException("Cannot reduce empty list");
        T acc = list.head();
        ImmutableList<T> cur = list.tail();
        while (!cur.isEmpty()) {
            acc = op.apply(acc, cur.head());
            cur = cur.tail();
        }
        return acc;
    }

    // Convenience sum when T is e.g. Integer, Long etc. and add provided
    public static <T> T sum(ImmutableList<T> list, T identity, BinaryOperator<T> add) {
        return foldLeft(list, identity, add::apply);
    }
}