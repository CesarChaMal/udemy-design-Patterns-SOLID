package com.balazsholczer.composite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Comprehensive test for Composite Pattern
 * Tests Traditional, Functional, and Tree approaches
 */
class CompositeTest {
    
    @Test
    void testTraditionalComposite() {
        Component leaf1 = new Leaf("Leaf1");
        Component leaf2 = new Leaf("Leaf2");
        Component leaf3 = new Leaf("Leaf3");
        
        Composite composite1 = new Composite("Composite1");
        composite1.add(leaf1);
        composite1.add(leaf2);
        
        Composite composite2 = new Composite("Composite2");
        composite2.add(leaf3);
        composite2.add(composite1);
        
        assertDoesNotThrow(() -> composite2.operation());
        assertNotNull(composite2);
    }
    
    @Test
    void testFunctionalComposite() {
        var leaf1 = new FunctionalComposite.LeafNode("Leaf1", FunctionalComposite.DEFAULT_OPERATION);
        var leaf2 = new FunctionalComposite.LeafNode("Leaf2", FunctionalComposite.DEFAULT_OPERATION);
        var leaf3 = new FunctionalComposite.LeafNode("Leaf3", FunctionalComposite.DEFAULT_OPERATION);
        
        var composite = new FunctionalComposite.CompositeNode("Root", java.util.List.of(leaf1, leaf2, leaf3), FunctionalComposite.DEFAULT_OPERATION);
        
        assertEquals(3, composite.children().size());
        assertEquals("Root", composite.name());
        assertDoesNotThrow(() -> composite.execute());
    }
    
    @Test
    void testTreeComposite() {
        var root = new TreeComposite.TreeNode("Root");
        var branch1 = new TreeComposite.TreeNode("Branch1");
        var branch2 = new TreeComposite.TreeNode("Branch2");
        
        root = root.addChild(branch1).addChild(branch2);
        branch1 = branch1.addChild(new TreeComposite.TreeNode("Leaf1")).addChild(new TreeComposite.TreeNode("Leaf2"));
        branch2 = branch2.addChild(new TreeComposite.TreeNode("Leaf3"));
        
        assertEquals(2, root.children().size());
        assertEquals(2, branch1.children().size());
        assertEquals(1, branch2.children().size());
    }
    
    @Test
    void testGenericComposite() {
        var leaf1 = new GenericComposite.LeafElement<>(1);
        var leaf2 = new GenericComposite.LeafElement<>(2);
        var leaf3 = new GenericComposite.LeafElement<>(3);
        
        var composite = new GenericComposite.BranchElement<>(0, List.of(leaf1, leaf2, leaf3));
        
        assertEquals(4, GenericComposite.count(composite)); // 1 root + 3 leaves
        var sum = GenericComposite.flatten(composite).mapToInt(i -> i).sum();
        assertEquals(6, sum); // 0 + 1 + 2 + 3 = 6
    }
    
    @Test
    void testNestedComposites() {
        // Test deeply nested composite structures
        Composite root = new Composite("Root");
        Composite level1 = new Composite("Level1");
        Composite level2 = new Composite("Level2");
        
        root.add(level1);
        level1.add(level2);
        level2.add(new Leaf("DeepLeaf"));
        
        assertDoesNotThrow(() -> root.operation());
        assertNotNull(root);
        assertNotNull(level1);
        assertNotNull(level2);
    }
    
    @Test
    void testEquivalence() {
        // All composite implementations should handle hierarchical structures
        
        // Traditional
        Composite traditionalRoot = new Composite("Root");
        traditionalRoot.add(new Leaf("Child1"));
        traditionalRoot.add(new Leaf("Child2"));
        
        // Functional
        var functionalChild1 = new FunctionalComposite.LeafNode("Child1", FunctionalComposite.DEFAULT_OPERATION);
        var functionalChild2 = new FunctionalComposite.LeafNode("Child2", FunctionalComposite.DEFAULT_OPERATION);
        var functionalRoot = new FunctionalComposite.CompositeNode("Root", java.util.List.of(functionalChild1, functionalChild2), FunctionalComposite.DEFAULT_OPERATION);
        
        // Tree
        var treeRoot = new TreeComposite.TreeNode("Root")
            .addChild(new TreeComposite.TreeNode("Child1"))
            .addChild(new TreeComposite.TreeNode("Child2"));
        
        // Generic
        var genericChild1 = new GenericComposite.LeafElement<>("Child1");
        var genericChild2 = new GenericComposite.LeafElement<>("Child2");
        var genericRoot = new GenericComposite.BranchElement<>("Root", java.util.List.of(genericChild1, genericChild2));
        
        // All should have same number of children
        assertNotNull(traditionalRoot.getChild(0)); // Has at least one child
        assertNotNull(traditionalRoot.getChild(1)); // Has second child
        assertEquals(2, functionalRoot.children().size());
        assertEquals(2, treeRoot.children().size());
        assertEquals(3, GenericComposite.count(genericRoot)); // 1 root + 2 children
        
        // All should handle operations without errors
        assertDoesNotThrow(() -> traditionalRoot.operation());
        assertDoesNotThrow(() -> functionalRoot.execute());
        assertDoesNotThrow(() -> treeRoot.traverse(System.out::println));
        GenericComposite.traverse(genericRoot, System.out::println);
    }
}