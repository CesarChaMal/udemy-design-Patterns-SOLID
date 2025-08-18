package com.balazsholczer.factory;

public class ModernFactoryDemo {
    
    public static void main(String[] args) {
        
        System.out.println("=== Traditional Factory Pattern ===");
        Algorithm traditional1 = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH);
        Algorithm traditional2 = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SPANNING_TREE);
        traditional1.solve();
        traditional2.solve();
        
        System.out.println("\n=== Functional Factory Pattern ===");
        Algorithm functional1 = FunctionalAlgorithmFactory.create("shortest");
        Algorithm functional2 = FunctionalAlgorithmFactory.create("spanning");
        functional1.solve();
        functional2.solve();
        
        System.out.println("\n=== Enum Factory Pattern ===");
        Algorithm enum1 = EnumAlgorithmFactory.SHORTEST_PATH.create();
        Algorithm enum2 = EnumAlgorithmFactory.create("spanning_tree");
        enum1.solve();
        enum2.solve();
        
        System.out.println("\n=== Lambda Factory Pattern ===");
        Algorithm lambda1 = LambdaAlgorithmFactory.create("shortest");
        Algorithm lambda2 = LambdaAlgorithmFactory.create("spanning");
        lambda1.solve();
        lambda2.solve();
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Composite Algorithm:");
        Algorithm composite = LambdaAlgorithmFactory.createComposite("shortest", "spanning");
        composite.solve();
        
        System.out.println("Error Handling:");
        Algorithm unknown = FunctionalAlgorithmFactory.create("unknown");
        unknown.solve();
    }
}