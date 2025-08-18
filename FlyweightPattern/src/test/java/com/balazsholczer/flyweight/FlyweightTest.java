package com.balazsholczer.flyweight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Flyweight Pattern
 * Tests Traditional, Functional, and Record approaches
 */
class FlyweightTest {
    
    @Test
    void testTraditionalFlyweight() {
        Flyweight flyweight1 = FlyweightFactory.getFlyweight("Circle");
        Flyweight flyweight2 = FlyweightFactory.getFlyweight("Circle");
        Flyweight flyweight3 = FlyweightFactory.getFlyweight("Square");
        
        // Same intrinsic state should return same instance
        assertSame(flyweight1, flyweight2);
        assertNotSame(flyweight1, flyweight3);
        
        assertEquals(2, FlyweightFactory.getFlyweightCount());
        
        assertDoesNotThrow(() -> new Context("Circle", "Red").operation());
        assertDoesNotThrow(() -> new Context("Square", "Blue").operation());
    }
    
    @Test
    void testFunctionalFlyweight() {
        var intrinsic1 = new FunctionalFlyweight.IntrinsicState("Circle", "Solid");
        var intrinsic2 = new FunctionalFlyweight.IntrinsicState("Circle", "Solid");
        var intrinsic3 = new FunctionalFlyweight.IntrinsicState("Square", "Dashed");
        
        var flyweight1 = FunctionalFlyweight.getFlyweight(intrinsic1);
        var flyweight2 = FunctionalFlyweight.getFlyweight(intrinsic2);
        var flyweight3 = FunctionalFlyweight.getFlyweight(intrinsic3);
        
        // Same intrinsic state should return same function
        assertSame(flyweight1, flyweight2);
        assertNotSame(flyweight1, flyweight3);
        
        var extrinsic = new FunctionalFlyweight.ExtrinsicState(10, 20, "Red");
        String result1 = flyweight1.apply(extrinsic);
        String result2 = FunctionalFlyweight.render(intrinsic1, extrinsic);
        
        assertNotNull(result1);
        assertNotNull(result2);
        assertTrue(result1.contains("Circle"));
        assertTrue(result1.contains("Red"));
    }
    
    @Test
    void testRecordFlyweight() {
        var flyweight1 = RecordFlyweight.getFlyweight("Circle", "Red");
        var flyweight2 = RecordFlyweight.getFlyweight("Circle", "Red");
        var flyweight3 = RecordFlyweight.getFlyweight("Square", "Blue");
        
        // Same parameters should return same instance
        assertSame(flyweight1, flyweight2);
        assertNotSame(flyweight1, flyweight3);
        
        var context1 = RecordFlyweight.createContext("Circle", "Red", 10, 20);
        var context2 = RecordFlyweight.createContext("Square", "Blue", 30, 40);
        
        assertDoesNotThrow(() -> context1.render());
        assertDoesNotThrow(() -> context2.render());
        
        assertEquals("Circle", context1.flyweight().type());
        assertEquals("Red", context1.flyweight().color());
    }
    
    @Test
    void testFlyweightMemoryEfficiency() {
        // Create many objects with same intrinsic state
        for (int i = 0; i < 100; i++) {
            FlyweightFactory.getFlyweight("Circle");
            FlyweightFactory.getFlyweight("Square");
        }
        
        // Should only create 2 flyweights despite 200 requests
        assertEquals(2, FlyweightFactory.getFlyweightCount());
        
        // Test functional flyweight efficiency
        var intrinsic = new FunctionalFlyweight.IntrinsicState("Circle", "Solid");
        for (int i = 0; i < 100; i++) {
            FunctionalFlyweight.getFlyweight(intrinsic);
        }
        
        // Should reuse the same function instance
        var func1 = FunctionalFlyweight.getFlyweight(intrinsic);
        var func2 = FunctionalFlyweight.getFlyweight(intrinsic);
        assertSame(func1, func2);
    }
    
    @Test
    void testEquivalence() {
        // All flyweight implementations should share intrinsic state efficiently
        
        // Traditional
        Flyweight traditional1 = FlyweightFactory.getFlyweight("Circle");
        Flyweight traditional2 = FlyweightFactory.getFlyweight("Circle");
        assertSame(traditional1, traditional2);
        
        // Functional
        var intrinsic = new FunctionalFlyweight.IntrinsicState("Circle", "Solid");
        var functional1 = FunctionalFlyweight.getFlyweight(intrinsic);
        var functional2 = FunctionalFlyweight.getFlyweight(intrinsic);
        assertSame(functional1, functional2);
        
        // Record
        var record1 = RecordFlyweight.getFlyweight("Circle", "Red");
        var record2 = RecordFlyweight.getFlyweight("Circle", "Red");
        assertSame(record1, record2);
        
        // All should handle rendering operations
        assertDoesNotThrow(() -> new Context("Circle", "Red").operation());
        
        var extrinsic = new FunctionalFlyweight.ExtrinsicState(0, 0, "Red");
        assertDoesNotThrow(() -> functional1.apply(extrinsic));
        
        assertDoesNotThrow(() -> record1.render(0, 0));
    }
}