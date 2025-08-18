package com.balazsholczer.composite;

import java.util.List;

public class ModernCompositeDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Composite Pattern ===");
        Component root = new Composite("Root");
        Component branch1 = new Composite("Branch1");
        Component branch2 = new Composite("Branch2");
        
        Component leaf1 = new Leaf("Leaf1");
        Component leaf2 = new Leaf("Leaf2");
        Component leaf3 = new Leaf("Leaf3");
        
        root.add(branch1);
        root.add(branch2);
        branch1.add(leaf1);
        branch1.add(leaf2);
        branch2.add(leaf3);
        
        root.operation();
        
        System.out.println("\n=== Functional Composite Pattern ===");
        var funcLeaf1 = new FunctionalComposite.LeafNode("Leaf1", FunctionalComposite.DEFAULT_OPERATION);
        var funcLeaf2 = new FunctionalComposite.LeafNode("Leaf2", FunctionalComposite.DEFAULT_OPERATION);
        var funcLeaf3 = new FunctionalComposite.LeafNode("Leaf3", FunctionalComposite.DEFAULT_OPERATION);
        
        var funcBranch1 = new FunctionalComposite.CompositeNode("Branch1", List.of(funcLeaf1, funcLeaf2), FunctionalComposite.DEFAULT_OPERATION);
        var funcBranch2 = new FunctionalComposite.CompositeNode("Branch2", List.of(funcLeaf3), FunctionalComposite.DEFAULT_OPERATION);
        var funcRoot = new FunctionalComposite.CompositeNode("Root", List.of(funcBranch1, funcBranch2), FunctionalComposite.DEFAULT_OPERATION);
        
        funcRoot.execute();
        
        System.out.println("\n=== Tree Composite Pattern ===");
        var treeLeaf1 = new TreeComposite.TreeNode("Leaf1");
        var treeLeaf2 = new TreeComposite.TreeNode("Leaf2");
        var treeLeaf3 = new TreeComposite.TreeNode("Leaf3");
        
        var treeBranch1 = new TreeComposite.TreeNode("Branch1").addChild(treeLeaf1).addChild(treeLeaf2);
        var treeBranch2 = new TreeComposite.TreeNode("Branch2").addChild(treeLeaf3);
        var treeRoot = new TreeComposite.TreeNode("Root").addChild(treeBranch1).addChild(treeBranch2);
        
        treeRoot.traverse(name -> System.out.println("TreeComposite: " + name + " operation"));
        
        System.out.println("\n=== Generic Composite Pattern ===");
        var genericLeaf1 = new GenericComposite.LeafElement<>("Leaf1");
        var genericLeaf2 = new GenericComposite.LeafElement<>("Leaf2");
        var genericLeaf3 = new GenericComposite.LeafElement<>("Leaf3");
        
        var genericBranch1 = new GenericComposite.BranchElement<>("Branch1", List.of(genericLeaf1, genericLeaf2));
        var genericBranch2 = new GenericComposite.BranchElement<>("Branch2", List.of(genericLeaf3));
        var genericRoot = new GenericComposite.BranchElement<>("Root", List.of(genericBranch1, genericBranch2));
        
        GenericComposite.traverse(genericRoot, name -> System.out.println("GenericComposite: " + name + " operation"));
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Tree - Hierarchical display:");
        treeRoot.traverseWithDepth(System.out::println, 0);
        
        System.out.println("Tree - Flatten to stream:");
        treeRoot.flatten().forEach(name -> System.out.print(name + " "));
        System.out.println();
        
        System.out.println("Tree - Size: " + treeRoot.size());
        
        System.out.println("Generic - Count elements: " + GenericComposite.count(genericRoot));
        
        System.out.println("Generic - Map to uppercase:");
        GenericComposite.map(genericRoot, String::toUpperCase)
                       .forEach(name -> System.out.print(name + " "));
        System.out.println();
        
        System.out.println("Functional - Custom operation:");
        var customLeaf = new FunctionalComposite.LeafNode("CustomLeaf", 
            name -> System.out.println("Custom operation on: " + name));
        customLeaf.execute();
    }
}