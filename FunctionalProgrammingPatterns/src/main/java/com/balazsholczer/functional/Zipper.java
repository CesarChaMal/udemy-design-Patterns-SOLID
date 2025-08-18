package com.balazsholczer.functional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Zipper pattern for immutable focused navigation of a linear structure.
 * left: elements before focus (stored reversed; index 0 = immediate left)
 * focus: current element (sentinel null only when empty)
 * right: elements after focus (natural order; index 0 = immediate right)
 *
 * Note: `null` cannot be a valid element value (used to denote empty).
 */
public final class Zipper<T> {

    private final List<T> left;   // reversed
    private final T focus;
    private final List<T> right;  // natural

    private Zipper(List<T> left, T focus, List<T> right) {
        this.left = left;
        this.focus = focus;
        this.right = right;
    }

    /* ---------- Factory ---------- */

    public static <T> Zipper<T> empty() {
        return new Zipper<>(List.of(), null, List.of());
    }

    public static <T> Zipper<T> fromList(List<T> list) {
        if (list.isEmpty()) return empty();
        List<T> right = new ArrayList<>(list.subList(1, list.size()));
        return new Zipper<>(List.of(), list.get(0), Collections.unmodifiableList(right));
    }

    public static <T> Zipper<T> fromList(ImmutableList<T> list) {
        if (list == null || list.isEmpty()) {
            return empty();
        }
        ArrayList<T> tmp = new ArrayList<>(list.size());
        ImmutableList<T> cur = list;
        while (!cur.isEmpty()) {
            T v = cur.head();
            if (v == null) {
                throw new IllegalArgumentException("Zipper cannot contain null elements");
            }
            tmp.add(v);
            cur = cur.tail();
        }
        return fromList((List<T>) tmp);
    }

    public static <T> Zipper<T> fromImmutableList(ImmutableList<T> list) {
        List<T> tmp = new ArrayList<>();
        ImmutableList<T> cur = list;
        while (!cur.isEmpty()) {
            tmp.add(cur.head());
            cur = cur.tail();
        }
        return fromList(tmp);
    }

    public static <T> Zipper<T> fromListAtIndex(List<T> list, int index) {
        if (list.isEmpty()) return empty();
        if (index < 0 || index >= list.size()) throw new IndexOutOfBoundsException(index);
        if (index == 0) return fromList(list);
        List<T> left = new ArrayList<>();
        for (int i = index - 1; i >= 0; i--) {
            left.add(list.get(i)); // reversed
        }
        T focus = list.get(index);
        List<T> right = new ArrayList<>(list.subList(index + 1, list.size()));
        return new Zipper<>(Collections.unmodifiableList(left), focus, Collections.unmodifiableList(right));
    }

    /* ---------- Queries ---------- */

    public boolean isEmpty() {
        return focus == null && left.isEmpty() && right.isEmpty();
    }

    public T focus() {
        if (isEmpty()) throw new IllegalStateException("Empty zipper has no focus");
        return focus;
    }

    public boolean hasLeft() { return !left.isEmpty(); }
    public boolean hasRight() { return !right.isEmpty(); }
    public boolean isAtStart() { return left.isEmpty(); }
    public boolean isAtEnd() { return right.isEmpty(); }
    public int size() { return isEmpty() ? 0 : left.size() + 1 + right.size(); }

    /** Zero-based index of focus. */
    public int index() {
        if (isEmpty()) throw new IllegalStateException("Empty zipper");
        return left.size();
    }

    /* ---------- Navigation ---------- */

    public Zipper<T> moveLeft() {
        if (!hasLeft()) return this;
        List<T> newLeft = new ArrayList<>(left);
        T newFocus = newLeft.remove(0);
        List<T> newRight = new ArrayList<>(right.size() + 1);
        newRight.add(focus);
        newRight.addAll(right);
        return new Zipper<>(Collections.unmodifiableList(newLeft), newFocus, Collections.unmodifiableList(newRight));
    }

    public Zipper<T> moveRight() {
        if (!hasRight()) return this;
        List<T> newLeft = new ArrayList<>(left.size() + 1);
        newLeft.add(focus);
        newLeft.addAll(left);
        T newFocus = right.get(0);
        List<T> newRight = new ArrayList<>(right.subList(1, right.size()));
        return new Zipper<>(Collections.unmodifiableList(newLeft), newFocus, Collections.unmodifiableList(newRight));
    }

    public Zipper<T> moveLeft(int n) {
        Zipper<T> z = this;
        for (int i = 0; i < n && z.hasLeft(); i++) z = z.moveLeft();
        return z;
    }

    public Zipper<T> moveRight(int n) {
        Zipper<T> z = this;
        for (int i = 0; i < n && z.hasRight(); i++) z = z.moveRight();
        return z;
    }

    public Zipper<T> toStart() {
        Zipper<T> z = this;
        while (z.hasLeft()) z = z.moveLeft();
        return z;
    }

    public Zipper<T> toEnd() {
        Zipper<T> z = this;
        while (z.hasRight()) z = z.moveRight();
        return z;
    }

    /* ---------- Modification ---------- */

    public Zipper<T> set(T newValue) {
        if (isEmpty()) throw new IllegalStateException("Cannot set on empty zipper");
        return new Zipper<>(left, newValue, right);
    }

    public Zipper<T> update(Function<T, T> mapper) {
        return set(mapper.apply(focus()));
    }

    public Zipper<T> insertLeft(T value) {
        if (isEmpty()) return fromList(List.of(value));
        List<T> newLeft = new ArrayList<>(left.size() + 1);
        newLeft.add(value);
        newLeft.addAll(left);
        return new Zipper<>(Collections.unmodifiableList(newLeft), focus, right);
    }

    public Zipper<T> insertRight(T value) {
        if (isEmpty()) return fromList(List.of(value));
        List<T> newRight = new ArrayList<>(right.size() + 1);
        newRight.add(value);
        newRight.addAll(right);
        return new Zipper<>(left, focus, Collections.unmodifiableList(newRight));
    }

    /** Removes focus; selects right neighbor if present, else left neighbor, else empty. */
    public Zipper<T> removeFocus() {
        if (isEmpty()) return this;
        if (hasRight()) {
            T newFocus = right.get(0);
            List<T> newRight = new ArrayList<>(right.subList(1, right.size()));
            return new Zipper<>(left, newFocus, Collections.unmodifiableList(newRight));
        }
        if (hasLeft()) {
            List<T> newLeft = new ArrayList<>(left);
            T newFocus = newLeft.remove(0);
            return new Zipper<>(Collections.unmodifiableList(newLeft), newFocus, right);
        }
        return empty();
    }

    /** Maps every element (focus, left, right) preserving relative order. */
    public <U> Zipper<U> map(Function<T, U> f) {
        if (isEmpty()) return Zipper.empty();
        List<U> newLeft = new ArrayList<>(left.size());
        for (T t : left) newLeft.add(f.apply(t)); // still reversed
        U newFocus = f.apply(focus);
        List<U> newRight = new ArrayList<>(right.size());
        for (T t : right) newRight.add(f.apply(t));
        return new Zipper<>(Collections.unmodifiableList(newLeft), newFocus, Collections.unmodifiableList(newRight));
    }

    /* ---------- Extraction ---------- */

    public ImmutableList<T> toList() {
        if (isEmpty()) return ImmutableList.empty();
        List<T> all = new ArrayList<>(left.size() + 1 + right.size());
        for (int i = left.size() - 1; i >= 0; i--) all.add(left.get(i));
        all.add(focus);
        all.addAll(right);
        ImmutableList<T> result = ImmutableList.empty();
        for (int i = all.size() - 1; i >= 0; i--) result = result.prepend(all.get(i));
        return result;
    }

    public List<T> toJavaList() {
        List<T> out = new ArrayList<>(size());
        ImmutableList<T> imm = toList();
        ImmutableList<T> cur = imm;
        while (!cur.isEmpty()) {
            out.add(cur.head());
            cur = cur.tail();
        }
        return out;
    }

    public ImmutableList<T> toImmutableList() {
        return toList();
    }

    /* ---------- Object overrides ---------- */

    @Override
    public String toString() {
        return "Zipper(left=" + leftReconstructed() +
                ", focus=" + (isEmpty() ? "∅" : focus) +
                ", right=" + right + ")";
    }

    private List<T> leftReconstructed() {
        List<T> l = new ArrayList<>(left.size());
        for (int i = left.size() - 1; i >= 0; i--) l.add(left.get(i));
        return l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zipper<?> that)) return false;
        return Objects.equals(toList(), that.toList()) &&
                left.size() == that.left.size();
    }

    @Override
    public int hashCode() {
        return Objects.hash(toList(), left.size());
    }
}