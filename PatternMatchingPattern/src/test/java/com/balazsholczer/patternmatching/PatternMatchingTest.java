package com.balazsholczer.patternmatching;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.Arrays;

/**
 * Comprehensive test class for Pattern Matching - Traditional vs Modern approaches
 */
class PatternMatchingTest {
    
    private List<PatternMatchingDemo.Shape> testShapes;
    private List<Object> testObjects;
    
    @BeforeEach
    void setUp() {
        testShapes = List.of(
            new PatternMatchingDemo.Circle(5.0),
            new PatternMatchingDemo.Rectangle(4.0, 6.0),
            new PatternMatchingDemo.Triangle(3.0, 4.0),
            new PatternMatchingDemo.Circle(12.0),
            new PatternMatchingDemo.Rectangle(5.0, 5.0)
        );
        
        testObjects = Arrays.asList(
            "Hello",
            42,
            -10,
            0,
            List.of(1, 2, 3),
            new PatternMatchingDemo.Point(5, 5),
            null
        );
    }
    
    @Test
    void testInstanceofPatternMatching() {
        // Test traditional instanceof pattern matching
        PatternMatchingDemo.Shape circle = new PatternMatchingDemo.Circle(5.0);
        PatternMatchingDemo.Shape rectangle = new PatternMatchingDemo.Rectangle(4.0, 6.0);
        
        String circleDesc = PatternMatchingDemo.describeShapeTraditional(circle);
        String rectDesc = PatternMatchingDemo.describeShapeTraditional(rectangle);
        
        assertTrue(circleDesc.contains("Circle"));
        assertTrue(circleDesc.contains("5.0"));
        assertTrue(rectDesc.contains("Rectangle"));
        assertTrue(rectDesc.contains("4.0"));
        assertTrue(rectDesc.contains("6.0"));
    }
    
    @Test
    void testSwitchExpressionPatternMatching() {
        // Test modern switch expression pattern matching
        PatternMatchingDemo.Shape circle = new PatternMatchingDemo.Circle(7.0);
        PatternMatchingDemo.Shape triangle = new PatternMatchingDemo.Triangle(3.0, 4.0);
        
        String circleDesc = PatternMatchingDemo.describeShapeModern(circle);
        String triangleDesc = PatternMatchingDemo.describeShapeModern(triangle);
        
        assertTrue(circleDesc.contains("Circle"));
        assertTrue(circleDesc.contains("7.0"));
        assertTrue(triangleDesc.contains("Triangle"));
        assertTrue(triangleDesc.contains("3.0"));
        assertTrue(triangleDesc.contains("4.0"));
    }
    
    @Test
    void testPatternMatchingWithGuards() {
        // Test pattern matching with guard conditions
        PatternMatchingDemo.Shape largeCircle = new PatternMatchingDemo.Circle(15.0);
        PatternMatchingDemo.Shape mediumCircle = new PatternMatchingDemo.Circle(7.0);
        PatternMatchingDemo.Shape smallCircle = new PatternMatchingDemo.Circle(3.0);
        PatternMatchingDemo.Shape square = new PatternMatchingDemo.Rectangle(5.0, 5.0);
        
        assertEquals("Large circle", PatternMatchingDemo.categorizeShape(largeCircle));
        assertEquals("Medium circle", PatternMatchingDemo.categorizeShape(mediumCircle));
        assertEquals("Small circle", PatternMatchingDemo.categorizeShape(smallCircle));
        assertTrue(PatternMatchingDemo.categorizeShape(square).contains("Square"));
    }
    
    @Test
    void testCollectionPatternMatching() {
        // Test collection pattern matching
        List<String> emptyList = List.of();
        List<String> singleItem = List.of("item");
        List<String> pair = List.of("first", "second");
        List<String> multiple = List.of("a", "b", "c", "d");
        
        assertEquals("Empty list", PatternMatchingDemo.analyzeList(emptyList));
        assertTrue(PatternMatchingDemo.analyzeList(singleItem).contains("Single item"));
        assertTrue(PatternMatchingDemo.analyzeList(pair).contains("Pair"));
        assertTrue(PatternMatchingDemo.analyzeList(multiple).contains("List with 4 items"));
    }
    
    @Test
    void testNestedPatternMatching() {
        // Test nested pattern matching with records
        PatternMatchingDemo.Point origin = new PatternMatchingDemo.Point(0, 0);
        PatternMatchingDemo.Point diagonal = new PatternMatchingDemo.Point(5, 5);
        PatternMatchingDemo.Point regular = new PatternMatchingDemo.Point(3, 7);
        
        PatternMatchingDemo.Line vertical = new PatternMatchingDemo.Line(
            new PatternMatchingDemo.Point(2, 0), 
            new PatternMatchingDemo.Point(2, 5)
        );
        
        PatternMatchingDemo.Line horizontal = new PatternMatchingDemo.Line(
            new PatternMatchingDemo.Point(0, 3), 
            new PatternMatchingDemo.Point(5, 3)
        );
        
        assertEquals("Origin point", PatternMatchingDemo.analyzeGeometry(origin));
        assertTrue(PatternMatchingDemo.analyzeGeometry(diagonal).contains("Diagonal point"));
        assertTrue(PatternMatchingDemo.analyzeGeometry(regular).contains("Point at"));
        assertTrue(PatternMatchingDemo.analyzeGeometry(vertical).contains("Vertical line"));
        assertTrue(PatternMatchingDemo.analyzeGeometry(horizontal).contains("Horizontal line"));
    }
    
    @Test
    void testNullSafePatternMatching() {
        // Test null-safe pattern matching
        assertEquals("Null object", PatternMatchingDemo.safeDescribe(null));
        assertEquals("Empty string", PatternMatchingDemo.safeDescribe(""));
        assertTrue(PatternMatchingDemo.safeDescribe("Hello").contains("String: Hello"));
        assertTrue(PatternMatchingDemo.safeDescribe(42).contains("Positive integer"));
        assertTrue(PatternMatchingDemo.safeDescribe(-5).contains("Negative integer"));
        assertEquals("Zero", PatternMatchingDemo.safeDescribe(0));
        assertTrue(PatternMatchingDemo.safeDescribe(List.of(1, 2)).contains("List with 2 elements"));
    }
    
    @Test
    void testSealedClassExhaustiveness() {
        // Test that sealed classes provide exhaustive pattern matching
        for (PatternMatchingDemo.Shape shape : testShapes) {
            // Both traditional and modern approaches should handle all cases
            String traditional = PatternMatchingDemo.describeShapeTraditional(shape);
            String modern = PatternMatchingDemo.describeShapeModern(shape);
            
            assertNotNull(traditional);
            assertNotNull(modern);
            assertNotEquals("Unknown shape", traditional);
            
            // Both should identify the shape type correctly
            if (shape instanceof PatternMatchingDemo.Circle) {
                assertTrue(traditional.contains("Circle"));
                assertTrue(modern.contains("Circle"));
            } else if (shape instanceof PatternMatchingDemo.Rectangle) {
                assertTrue(traditional.contains("Rectangle"));
                assertTrue(modern.contains("Rectangle"));
            } else if (shape instanceof PatternMatchingDemo.Triangle) {
                assertTrue(traditional.contains("Triangle"));
                assertTrue(modern.contains("Triangle"));
            }
        }
    }
    
    @Test
    void testRecordPatternDeconstruction() {
        // Test record pattern deconstruction
        PatternMatchingDemo.Circle circle = new PatternMatchingDemo.Circle(10.0);
        PatternMatchingDemo.Rectangle rectangle = new PatternMatchingDemo.Rectangle(3.0, 4.0);
        
        String circleDesc = PatternMatchingDemo.describeShapeModern(circle);
        String rectDesc = PatternMatchingDemo.describeShapeModern(rectangle);
        
        // Should extract values from records
        assertTrue(circleDesc.contains("10.0"));
        assertTrue(rectDesc.contains("3.0"));
        assertTrue(rectDesc.contains("4.0"));
    }
    
    @Test
    void testPatternMatchingPerformance() {
        // Test that pattern matching performs well
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 10000; i++) {
            for (PatternMatchingDemo.Shape shape : testShapes) {
                PatternMatchingDemo.describeShapeModern(shape);
                PatternMatchingDemo.categorizeShape(shape);
            }
        }
        
        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 1000, "Pattern matching should be performant");
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and modern approaches produce equivalent results
        for (PatternMatchingDemo.Shape shape : testShapes) {
            String traditional = PatternMatchingDemo.describeShapeTraditional(shape);
            String modern = PatternMatchingDemo.describeShapeModern(shape);
            
            // Both should identify the same shape type
            if (traditional.contains("Circle")) {
                assertTrue(modern.contains("Circle"));
            } else if (traditional.contains("Rectangle")) {
                assertTrue(modern.contains("Rectangle"));
            } else if (traditional.contains("Triangle")) {
                assertTrue(modern.contains("Triangle"));
            }
            
            // Both should extract the same values
            assertNotNull(traditional);
            assertNotNull(modern);
        }
    }
    
    @Test
    void testComplexPatternMatching() {
        // Test complex nested pattern matching scenarios
        PatternMatchingDemo.Line complexLine = new PatternMatchingDemo.Line(
            new PatternMatchingDemo.Point(0, 0),
            new PatternMatchingDemo.Point(5, 5)
        );
        
        String result = PatternMatchingDemo.analyzeGeometry(complexLine);
        
        assertNotNull(result);
        assertTrue(result.contains("Line"));
        
        // Test with various geometric objects
        List<Object> geometries = List.of(
            new PatternMatchingDemo.Point(0, 0),
            new PatternMatchingDemo.Point(3, 3),
            complexLine,
            "not a geometry object"
        );
        
        for (Object geo : geometries) {
            String analysis = PatternMatchingDemo.analyzeGeometry(geo);
            assertNotNull(analysis);
            assertFalse(analysis.isEmpty());
        }
    }
    
    @Test
    void testPatternMatchingWithDifferentTypes() {
        // Test pattern matching across different types
        List<Object> mixedObjects = List.of(
            "string",
            123,
            -456,
            0,
            List.of("a", "b"),
            new PatternMatchingDemo.Circle(5.0),
            new Date()
        );
        
        for (Object obj : mixedObjects) {
            String description = PatternMatchingDemo.safeDescribe(obj);
            assertNotNull(description);
            assertFalse(description.isEmpty());
            
            // Should handle each type appropriately
            if (obj instanceof String) {
                assertTrue(description.contains("String"));
            } else if (obj instanceof Integer) {
                assertTrue(description.contains("integer") || description.contains("Zero"));
            } else if (obj instanceof List) {
                assertTrue(description.contains("List"));
            }
        }
    }
    
    @Test
    void testPatternMatchingEdgeCases() {
        // Test edge cases in pattern matching
        
        // Empty collections
        assertEquals("Empty list", PatternMatchingDemo.analyzeList(List.of()));
        
        // Boundary values
        PatternMatchingDemo.Shape tinyCircle = new PatternMatchingDemo.Circle(0.1);
        PatternMatchingDemo.Shape hugeCircle = new PatternMatchingDemo.Circle(1000.0);
        
        assertNotNull(PatternMatchingDemo.categorizeShape(tinyCircle));
        assertNotNull(PatternMatchingDemo.categorizeShape(hugeCircle));
        
        // Zero dimensions
        PatternMatchingDemo.Shape zeroRectangle = new PatternMatchingDemo.Rectangle(0, 5);
        assertNotNull(PatternMatchingDemo.describeShapeModern(zeroRectangle));
        
        // Same start and end points
        PatternMatchingDemo.Line pointLine = new PatternMatchingDemo.Line(
            new PatternMatchingDemo.Point(3, 3),
            new PatternMatchingDemo.Point(3, 3)
        );
        assertNotNull(PatternMatchingDemo.analyzeGeometry(pointLine));
    }
}