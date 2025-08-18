package com.balazsholczer.composite;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TreeComposite {
    
    public record TreeNode(String name, List<TreeNode> children) {
        
        public TreeNode(String name) {
            this(name, List.of());
        }
        
        public TreeNode addChild(TreeNode child) {
            var newChildren = new java.util.ArrayList<>(children);
            newChildren.add(child);
            return new TreeNode(name, newChildren);
        }
        
        public void traverse(Consumer<String> operation) {
            operation.accept(name);
            children.forEach(child -> child.traverse(operation));
        }
        
        public void traverseWithDepth(Consumer<String> operation, int depth) {
            String indent = "  ".repeat(depth);
            operation.accept(indent + name);
            children.forEach(child -> child.traverseWithDepth(operation, depth + 1));
        }
        
        public Stream<String> flatten() {
            return Stream.concat(
                Stream.of(name),
                children.stream().flatMap(TreeNode::flatten)
            );
        }
        
        public boolean isLeaf() {
            return children.isEmpty();
        }
        
        public int size() {
            return 1 + children.stream().mapToInt(TreeNode::size).sum();
        }
    }
}