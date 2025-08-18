package com.balazsholczer.factory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Factory Pattern
 * Tests Traditional, Enum, Functional, and Lambda approaches
 */
class FactoryTest {
    
    @Test
    void testTraditionalFactory() {
        Algorithm shortestPath = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH);
        Algorithm spanningTree = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SPANNING_TREE);
        
        assertNotNull(shortestPath);
        assertNotNull(spanningTree);
        
        assertTrue(shortestPath instanceof ShortestPath);
        assertTrue(spanningTree instanceof SpanningTree);
        
        assertDoesNotThrow(() -> shortestPath.solve());
        assertDoesNotThrow(() -> spanningTree.solve());
    }
    
    @Test
    void testEnumFactory() {
        Algorithm shortestPath = EnumAlgorithmFactory.SHORTEST_PATH.create();
        Algorithm spanningTree = EnumAlgorithmFactory.SPANNING_TREE.create();
        
        assertNotNull(shortestPath);
        assertNotNull(spanningTree);
        
        assertTrue(shortestPath instanceof ShortestPath);
        assertTrue(spanningTree instanceof SpanningTree);
        
        assertDoesNotThrow(() -> shortestPath.solve());
        assertDoesNotThrow(() -> spanningTree.solve());
    }
    
    @Test
    void testFunctionalFactory() {
        Algorithm shortestPath = FunctionalAlgorithmFactory.create("shortest");
        Algorithm spanningTree = FunctionalAlgorithmFactory.create("spanning");
        
        assertNotNull(shortestPath);
        assertNotNull(spanningTree);
        
        assertTrue(shortestPath instanceof ShortestPath);
        assertTrue(spanningTree instanceof SpanningTree);
        
        assertDoesNotThrow(() -> shortestPath.solve());
        assertDoesNotThrow(() -> spanningTree.solve());
    }
    
    @Test
    void testLambdaFactory() {
        Algorithm shortestPath = LambdaAlgorithmFactory.create("shortest");
        Algorithm spanningTree = LambdaAlgorithmFactory.create("spanning");
        
        assertNotNull(shortestPath);
        assertNotNull(spanningTree);
        
        assertDoesNotThrow(() -> shortestPath.solve());
        assertDoesNotThrow(() -> spanningTree.solve());
    }
    
    @Test
    void testFactoryConstants() {
        assertEquals(0, AlgorithmFactory.SHORTEST_PATH);
        assertEquals(1, AlgorithmFactory.SPANNING_TREE);
    }
    
    @Test
    void testInvalidAlgorithmType() {
        Algorithm invalid = AlgorithmFactory.createAlgorithm(999);
        assertNull(invalid);
    }
    
    @Test
    void testAlgorithmBehavior() {
        Algorithm shortestPath = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH);
        Algorithm spanningTree = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SPANNING_TREE);
        
        // Test that algorithms can be executed
        assertDoesNotThrow(() -> {
            shortestPath.solve();
            spanningTree.solve();
        });
    }
    
    @Test
    void testEquivalence() {
        // All factory approaches should create equivalent algorithms
        
        // Traditional
        Algorithm traditional1 = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH);
        Algorithm traditional2 = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SPANNING_TREE);
        
        // Enum
        Algorithm enum1 = EnumAlgorithmFactory.SHORTEST_PATH.create();
        Algorithm enum2 = EnumAlgorithmFactory.SPANNING_TREE.create();
        
        // Functional
        Algorithm functional1 = FunctionalAlgorithmFactory.create("shortest");
        Algorithm functional2 = FunctionalAlgorithmFactory.create("spanning");
        
        // Lambda
        Algorithm lambda1 = LambdaAlgorithmFactory.create("shortest");
        Algorithm lambda2 = LambdaAlgorithmFactory.create("spanning");
        
        // Traditional, Enum, and Functional should create same types
        assertEquals(traditional1.getClass(), enum1.getClass());
        assertEquals(traditional1.getClass(), functional1.getClass());
        
        assertEquals(traditional2.getClass(), enum2.getClass());
        assertEquals(traditional2.getClass(), functional2.getClass());
        
        // Lambda creates lambda functions, not concrete classes
        assertNotNull(lambda1);
        assertNotNull(lambda2);
        
        // All should be executable
        assertDoesNotThrow(() -> {
            traditional1.solve();
            enum1.solve();
            functional1.solve();
            lambda1.solve();
            
            traditional2.solve();
            enum2.solve();
            functional2.solve();
            lambda2.solve();
        });
    }
    
    @Test
    void testFactoryPattern() {
        // Test that factory pattern encapsulates object creation
        
        // Client doesn't need to know concrete classes
        Algorithm[] algorithms = {
            AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH),
            AlgorithmFactory.createAlgorithm(AlgorithmFactory.SPANNING_TREE)
        };
        
        // Can work with algorithms polymorphically
        for (Algorithm algorithm : algorithms) {
            assertNotNull(algorithm);
            assertDoesNotThrow(() -> algorithm.solve());
        }
    }
    
    @Test
    void testEnumFactoryValues() {
        // Test that enum factory has correct values
        EnumAlgorithmFactory[] values = EnumAlgorithmFactory.values();
        assertEquals(2, values.length);
        
        assertEquals(EnumAlgorithmFactory.SHORTEST_PATH, values[0]);
        assertEquals(EnumAlgorithmFactory.SPANNING_TREE, values[1]);
    }
    
    @Test
    void testFunctionalFactoryInvalidType() {
        Algorithm invalid = FunctionalAlgorithmFactory.create("INVALID_TYPE");
        assertNotNull(invalid); // Returns default algorithm, not null
    }
    
    @Test
    void testLambdaFactoryInvalidType() {
        Algorithm invalid = LambdaAlgorithmFactory.create("INVALID_TYPE");
        assertNotNull(invalid); // Returns default algorithm lambda, not null
    }
    
    @Test
    void testMultipleCreations() {
        // Test that factory can create multiple instances
        Algorithm algo1 = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH);
        Algorithm algo2 = AlgorithmFactory.createAlgorithm(AlgorithmFactory.SHORTEST_PATH);
        
        assertNotNull(algo1);
        assertNotNull(algo2);
        assertEquals(algo1.getClass(), algo2.getClass());
        
        // Should be different instances (not singleton)
        assertNotSame(algo1, algo2);
    }
}