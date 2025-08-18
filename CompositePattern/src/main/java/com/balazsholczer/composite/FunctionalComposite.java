package com.balazsholczer.composite;

import java.util.List;
import java.util.function.Consumer;

public class FunctionalComposite {
    
    public sealed interface Node permits LeafNode, CompositeNode {}
    
    public record LeafNode(String name, Consumer<String> operation) implements Node {
        public void execute() {
            operation.accept(name);
        }
    }
    
    public record CompositeNode(String name, List<Node> children, Consumer<String> operation) implements Node {
        public void execute() {
            operation.accept(name);
            children.forEach(child -> {
                if (child instanceof LeafNode leaf) {
                    leaf.execute();
                } else if (child instanceof CompositeNode composite) {
                    composite.execute();
                }
            });
        }
        
        public CompositeNode addChild(Node child) {
            var newChildren = new java.util.ArrayList<>(children);
            newChildren.add(child);
            return new CompositeNode(name, newChildren, operation);
        }
    }
    
    public static final Consumer<String> DEFAULT_OPERATION = 
        name -> System.out.println("FunctionalComposite: " + name + " operation");
}