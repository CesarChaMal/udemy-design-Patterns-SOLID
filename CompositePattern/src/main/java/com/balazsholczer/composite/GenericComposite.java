package com.balazsholczer.composite;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class GenericComposite<T> {
    
    public sealed interface CompositeElement<T> permits LeafElement, BranchElement {}
    
    public record LeafElement<T>(T value) implements CompositeElement<T> {}
    
    public record BranchElement<T>(T value, List<CompositeElement<T>> children) implements CompositeElement<T> {
        
        public BranchElement<T> addChild(CompositeElement<T> child) {
            var newChildren = new java.util.ArrayList<>(children);
            newChildren.add(child);
            return new BranchElement<>(value, newChildren);
        }
    }
    
    public static <T> void traverse(CompositeElement<T> element, Consumer<T> operation) {
        switch (element) {
            case LeafElement<T> leaf -> operation.accept(leaf.value());
            case BranchElement<T> branch -> {
                operation.accept(branch.value());
                branch.children().forEach(child -> traverse(child, operation));
            }
        }
    }
    
    public static <T, R> Stream<R> map(CompositeElement<T> element, Function<T, R> mapper) {
        return switch (element) {
            case LeafElement<T> leaf -> Stream.of(mapper.apply(leaf.value()));
            case BranchElement<T> branch -> Stream.concat(
                Stream.of(mapper.apply(branch.value())),
                branch.children().stream().flatMap(child -> map(child, mapper))
            );
        };
    }
    
    public static <T> Stream<T> flatten(CompositeElement<T> element) {
        return map(element, Function.identity());
    }
    
    public static <T> int count(CompositeElement<T> element) {
        return switch (element) {
            case LeafElement<T> leaf -> 1;
            case BranchElement<T> branch -> 1 + branch.children().stream()
                                                  .mapToInt(GenericComposite::count)
                                                  .sum();
        };
    }
}