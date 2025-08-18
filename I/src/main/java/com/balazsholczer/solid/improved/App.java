package com.balazsholczer.solid.improved;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Modern Interface Segregation Principle Implementation
 * 
 * Improvements:
 * - Functional interfaces where appropriate
 * - Async operations with CompletableFuture
 * - Proper interface segregation - clients depend only on what they need
 * - Stream API integration for traversals
 * - Thread-safe concurrent operations
 * - Generic type safety
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Modern Interface Segregation Principle ===");
        System.out.println("Improved with functional interfaces and async operations");
        System.out.println();
        
        // Create different tree implementations
        ModernBinarySearchTree<Integer, String> bst = new ModernBinarySearchTree<>();
        ModernAVLTree<Integer, String> avl = new ModernAVLTree<>();
        
        try {
            // Test BST operations
            System.out.println("--- Binary Search Tree Operations ---");
            testReadWriteOperations(bst);
            testTraversalOperations(bst);
            
            System.out.println("\n--- AVL Tree Operations ---");
            testReadWriteOperations(avl);
            testTraversalOperations(avl);
            testBalanceOperations(avl);
            
            // Demonstrate interface segregation
            System.out.println("\n--- Interface Segregation Demo ---");
            demonstrateSegregation(bst, avl);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Modern ISP Benefits ===");
        System.out.println("✅ Functional interfaces where appropriate");
        System.out.println("✅ Async operations with CompletableFuture");
        System.out.println("✅ Clients depend only on needed interfaces");
        System.out.println("✅ Stream API integration");
        System.out.println("✅ Thread-safe concurrent operations");
    }
    
    private static void testReadWriteOperations(Readable<Integer, String> readable) {
        if (readable instanceof Writable) {
            @SuppressWarnings("unchecked")
            Writable<Integer, String> writable = (Writable<Integer, String>) readable;
            writable.insert(5, "Five").join();
            writable.insert(3, "Three").join();
            writable.insert(7, "Seven").join();
        }
        
        Optional<String> result = readable.find(5).join();
        result.ifPresent(value -> System.out.println("Found: " + value));
    }
    
    private static void testTraversalOperations(Traversable<String> traversable) {
        Stream<String> inOrder = traversable.inOrderTraversal().join();
        System.out.println("In-order: " + inOrder.toList());
    }
    
    private static void testBalanceOperations(Balanceable balanceable) {
        boolean balanced = balanceable.isBalanced().join();
        int height = balanceable.getHeight().join();
        System.out.println("Balanced: " + balanced + ", Height: " + height);
    }
    
    private static void demonstrateSegregation(
            Readable<Integer, String> bst, 
            ModernAVLTree<Integer, String> avl) {
        
        System.out.println("BST implements: Readable, Writable, Deletable, Traversable");
        System.out.println("AVL implements: Readable, Writable, Deletable, Traversable, Balanceable");
        System.out.println("Clients can depend on only the interfaces they need!");
        
        // Client that only needs reading
        useReadOnlyClient(bst);
        useReadOnlyClient(avl);
        
        // Client that only needs balancing
        useBalanceOnlyClient(avl);
    }
    
    private static void useReadOnlyClient(Readable<Integer, String> readable) {
        System.out.println("Read-only client using: " + readable.getClass().getSimpleName());
        readable.find(5).thenAccept(result -> 
            result.ifPresent(value -> System.out.println("Read: " + value))
        ).join();
    }
    
    private static void useBalanceOnlyClient(Balanceable balanceable) {
        System.out.println("Balance-only client checking balance");
        balanceable.isBalanced().thenAccept(balanced -> 
            System.out.println("Is balanced: " + balanced)
        ).join();
    }
}