package com.balazsholczer.functional;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Immutable List Pattern - persistent data structure
 */
public abstract class ImmutableList<T> {
    
    public abstract T head();
    public abstract ImmutableList<T> tail();
    public abstract boolean isEmpty();
    public abstract int size();
    public abstract <U> ImmutableList<U> map(Function<T, U> mapper);
    public abstract ImmutableList<T> filter(Predicate<T> predicate);
    public abstract ImmutableList<T> prepend(T element);
    
    public T get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        ImmutableList<T> current = this;
        for (int i = 0; i < index; i++) {
            current = current.tail();
        }
        return current.head();
    }
    
    public ImmutableList<T> reverse() {
        ImmutableList<T> result = empty();
        ImmutableList<T> current = this;
        while (!current.isEmpty()) {
            result = result.prepend(current.head());
            current = current.tail();
        }
        return result;
    }
    
    public <U> U foldLeft(U identity, java.util.function.BinaryOperator<U> accumulator) {
        U result = identity;
        ImmutableList<T> current = this;
        while (!current.isEmpty()) {
            result = accumulator.apply(result, (U) current.head());
            current = current.tail();
        }
        return result;
    }
    
    public ImmutableList<T> take(int n) {
        if (n <= 0 || isEmpty()) return empty();
        return tail().take(n - 1).prepend(head());
    }
    
    public static <T> ImmutableList<T> empty() {
        return new Empty<>();
    }
    
    public static <T> ImmutableList<T> of(T... elements) {
        ImmutableList<T> result = empty();
        for (int i = elements.length - 1; i >= 0; i--) {
            result = result.prepend(elements[i]);
        }
        return result;
    }
    
    private static class Empty<T> extends ImmutableList<T> {
        @Override
        public T head() {
            throw new UnsupportedOperationException("Empty list has no head");
        }
        
        @Override
        public ImmutableList<T> tail() {
            throw new UnsupportedOperationException("Empty list has no tail");
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public int size() {
            return 0;
        }
        
        @Override
        public <U> ImmutableList<U> map(Function<T, U> mapper) {
            return empty();
        }
        
        @Override
        public ImmutableList<T> filter(Predicate<T> predicate) {
            return this;
        }
        
        @Override
        public ImmutableList<T> prepend(T element) {
            return new Cons<>(element, this);
        }
        
        @Override
        public String toString() {
            return "[]";
        }
        
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Empty;
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
    }
    
    private static class Cons<T> extends ImmutableList<T> {
        private final T head;
        private final ImmutableList<T> tail;
        private final int size;
        
        Cons(T head, ImmutableList<T> tail) {
            this.head = head;
            this.tail = tail;
            this.size = tail.size() + 1;
        }
        
        @Override
        public T head() {
            return head;
        }
        
        @Override
        public ImmutableList<T> tail() {
            return tail;
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public int size() {
            return size;
        }
        
        @Override
        public <U> ImmutableList<U> map(Function<T, U> mapper) {
            return tail.map(mapper).prepend(mapper.apply(head));
        }
        
        @Override
        public ImmutableList<T> filter(Predicate<T> predicate) {
            ImmutableList<T> filteredTail = tail.filter(predicate);
            return predicate.test(head) ? filteredTail.prepend(head) : filteredTail;
        }
        
        @Override
        public ImmutableList<T> prepend(T element) {
            return new Cons<>(element, this);
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            ImmutableList<T> current = this;
            while (!current.isEmpty()) {
                sb.append(current.head());
                current = current.tail();
                if (!current.isEmpty()) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof ImmutableList<?> other)) return false;
            if (other.isEmpty()) return false;
            if (size() != other.size()) return false;
            
            ImmutableList<T> thisCur = this;
            ImmutableList<?> otherCur = other;
            while (!thisCur.isEmpty() && !otherCur.isEmpty()) {
                if (!java.util.Objects.equals(thisCur.head(), otherCur.head())) {
                    return false;
                }
                thisCur = thisCur.tail();
                otherCur = otherCur.tail();
            }
            return thisCur.isEmpty() && otherCur.isEmpty();
        }
        
        @Override
        public int hashCode() {
            int hash = 1;
            ImmutableList<T> current = this;
            while (!current.isEmpty()) {
                hash = 31 * hash + java.util.Objects.hashCode(current.head());
                current = current.tail();
            }
            return hash;
        }
    }
}