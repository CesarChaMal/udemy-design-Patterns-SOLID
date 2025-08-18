package com.balazsholczer.solid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.balazsholczer.solid.improved.*;

/**
 * Comprehensive test for Interface Segregation Principle
 * Tests Traditional vs Improved approaches
 */
class SolidITest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional approach violates ISP with fat interface
        Tree bst = new BinarySearchTree();
        
        // BST implements all methods from Tree interface
        assertDoesNotThrow(() -> bst.findMax());
        assertDoesNotThrow(() -> bst.findMin());
        assertDoesNotThrow(() -> bst.traverse());
    }
    
    @Test
    void testImprovedApproach() {
        // Improved approach follows ISP with segregated interfaces
        ModernBinarySearchTree<Integer, String> bst = new ModernBinarySearchTree<>();
        ModernAVLTree<Integer, String> avl = new ModernAVLTree<>();
        
        // BST only implements interfaces it needs
        assertDoesNotThrow(() -> bst.find(5));
        assertDoesNotThrow(() -> bst.insert(5, "value"));
        assertDoesNotThrow(() -> bst.delete(5));
        assertDoesNotThrow(() -> bst.inOrderTraversal());
        
        // AVL tree implements additional balancing interface
        assertDoesNotThrow(() -> avl.find(10));
        assertDoesNotThrow(() -> avl.insert(10, "value"));
        assertDoesNotThrow(() -> avl.balance());
        assertDoesNotThrow(() -> avl.delete(10));
    }
    
    @Test
    void testInterfaceSegregationViolation() {
        // Traditional approach forces all implementations to have same interface
        Tree bst = new BinarySearchTree();
        
        // BST must implement all Tree methods even if not needed
        assertDoesNotThrow(() -> bst.findMax());
        assertDoesNotThrow(() -> bst.findMin());
        assertDoesNotThrow(() -> bst.traverse());
        
        // This violates ISP - clients forced to depend on methods they don't use
        assertTrue(bst instanceof Tree);
    }
    
    @Test
    void testInterfaceSegregationCompliance() {
        // Improved approach uses specific interfaces
        com.balazsholczer.solid.improved.Readable<Integer, String> readable = new ModernBinarySearchTree<>();
        com.balazsholczer.solid.improved.Writable<Integer, String> writable = new ModernBinarySearchTree<>();
        com.balazsholczer.solid.improved.Deletable<Integer> deletable = new ModernBinarySearchTree<>();
        com.balazsholczer.solid.improved.Traversable<String> traversable = new ModernBinarySearchTree<>();
        com.balazsholczer.solid.improved.Balanceable balanceable = new ModernAVLTree<>();
        
        // Each interface has specific responsibility
        assertDoesNotThrow(() -> readable.find(1));
        assertDoesNotThrow(() -> writable.insert(2, "value"));
        assertDoesNotThrow(() -> deletable.delete(3));
        assertDoesNotThrow(() -> traversable.inOrderTraversal());
        assertDoesNotThrow(() -> balanceable.balance());
    }
    
    @Test
    void testClientSpecificInterfaces() {
        // Test that clients only depend on interfaces they need
        
        // Read-only client
        com.balazsholczer.solid.improved.Readable<Integer, String> readOnlyTree = new ModernBinarySearchTree<>();
        assertDoesNotThrow(() -> readOnlyTree.find(5));
        // Client doesn't have access to write/delete operations
        
        // Write-only client
        com.balazsholczer.solid.improved.Writable<Integer, String> writeOnlyTree = new ModernBinarySearchTree<>();
        assertDoesNotThrow(() -> writeOnlyTree.insert(10, "value"));
        // Client doesn't have access to read/delete operations
        
        // Balancing client (only for self-balancing trees)
        com.balazsholczer.solid.improved.Balanceable balancingTree = new ModernAVLTree<>();
        assertDoesNotThrow(() -> balancingTree.balance());
    }
    
    @Test
    void testEquivalence() {
        // Both approaches should provide functionality
        // but improved approach doesn't force unnecessary dependencies
        
        // Traditional approach
        Tree traditionalBST = new BinarySearchTree();
        
        // Improved approach
        ModernBinarySearchTree<Integer, String> improvedBST = new ModernBinarySearchTree<>();
        ModernAVLTree<Integer, String> improvedAVL = new ModernAVLTree<>();
        
        // Basic operations should work
        assertDoesNotThrow(() -> traditionalBST.traverse());
        assertDoesNotThrow(() -> improvedBST.find(5));
        assertDoesNotThrow(() -> improvedAVL.balance());
        
        // But improved approach provides better interface segregation
        // BST doesn't need to implement balance() unnecessarily
        assertFalse(improvedBST instanceof com.balazsholczer.solid.improved.Balanceable); // No unnecessary dependency
        assertTrue(improvedAVL instanceof com.balazsholczer.solid.improved.Balanceable); // Only AVL needs balancing
    }
}