package com.balazsholczer.iterator;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Iterator Pattern
 * Tests Traditional, Functional, Iterable, and Stream approaches
 */
class IteratorTest {
    
    @Test
    void testTraditionalIterator() {
        NameRepository repository = new NameRepository();
        Iterator iterator = repository.getIterator();
        
        AtomicInteger count = new AtomicInteger(0);
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            assertNotNull(name);
            count.incrementAndGet();
        }
        
        assertEquals(4, count.get());
    }
    
    @Test
    void testIterableRepository() {
        IterableNameRepository repository = new IterableNameRepository();
        
        AtomicInteger count = new AtomicInteger(0);
        for (String name : repository) {
            assertNotNull(name);
            count.incrementAndGet();
        }
        
        assertEquals(4, count.get());
        assertEquals(4, repository.getNames().size());
    }
    
    @Test
    void testFunctionalRepository() {
        FunctionalNameRepository repository = new FunctionalNameRepository();
        
        AtomicInteger count = new AtomicInteger(0);
        repository.iterate(name -> {
            assertNotNull(name);
            count.incrementAndGet();
        });
        
        assertEquals(4, count.get());
        
        // Test conditional iteration
        AtomicInteger filteredCount = new AtomicInteger(0);
        repository.iterateIf(
            name -> name.startsWith("J"), 
            name -> filteredCount.incrementAndGet()
        );
        
        assertEquals(2, filteredCount.get()); // "Joe", "John"
        
        // Test getNames method
        String[] names = repository.getNames();
        assertEquals(4, names.length);
    }
    
    @Test
    void testStreamRepository() {
        StreamNameRepository repository = new StreamNameRepository();
        
        // Test stream method
        Stream<String> stream = repository.stream();
        long count = stream.count();
        assertEquals(4, count);
        
        // Test forEach (prints to console)
        assertDoesNotThrow(() -> repository.forEach());
        
        // Test filtered forEach
        assertDoesNotThrow(() -> repository.forEachFiltered("J"));
    }
    
    @Test
    void testIteratorBehavior() {
        NameRepository repository = new NameRepository();
        Iterator iterator = repository.getIterator();
        
        // Test hasNext and next behavior
        assertTrue(iterator.hasNext());
        Object first = iterator.next();
        assertNotNull(first);
        
        // Continue iterating
        while (iterator.hasNext()) {
            Object name = iterator.next();
            assertNotNull(name);
        }
        
        // Should be at end
        assertFalse(iterator.hasNext());
    }
    
    @Test
    void testFunctionalFiltering() {
        FunctionalNameRepository repository = new FunctionalNameRepository();
        
        // Test filtering with different conditions
        AtomicInteger shortNames = new AtomicInteger(0);
        repository.iterateIf(
            name -> name.length() <= 3,
            name -> shortNames.incrementAndGet()
        );
        
        assertEquals(1, shortNames.get()); // "Joe" (3 chars)
        
        // Test filtering with uppercase
        AtomicInteger upperCaseCount = new AtomicInteger(0);
        repository.iterateIf(
            name -> name.equals(name.toUpperCase()),
            name -> upperCaseCount.incrementAndGet()
        );
        
        assertEquals(0, upperCaseCount.get()); // No uppercase names
    }
    
    @Test
    void testStreamOperations() {
        StreamNameRepository repository = new StreamNameRepository();
        
        // Test stream filtering
        long jNames = repository.stream()
            .filter(name -> name.startsWith("J"))
            .count();
        assertEquals(2, jNames);
        
        // Test stream mapping
        long upperCaseNames = repository.stream()
            .map(String::toUpperCase)
            .count();
        assertEquals(4, upperCaseNames);
        
        // Test stream finding
        boolean hasAdam = repository.stream()
            .anyMatch(name -> name.equals("Adam"));
        assertTrue(hasAdam);
    }
    
    @Test
    void testEquivalence() {
        // All approaches should iterate over same data
        NameRepository traditional = new NameRepository();
        IterableNameRepository iterable = new IterableNameRepository();
        FunctionalNameRepository functional = new FunctionalNameRepository();
        StreamNameRepository stream = new StreamNameRepository();
        
        // Count elements using each approach
        AtomicInteger traditionalCount = new AtomicInteger(0);
        Iterator iterator = traditional.getIterator();
        while (iterator.hasNext()) {
            iterator.next();
            traditionalCount.incrementAndGet();
        }
        
        AtomicInteger iterableCount = new AtomicInteger(0);
        for (String name : iterable) {
            iterableCount.incrementAndGet();
        }
        
        AtomicInteger functionalCount = new AtomicInteger(0);
        functional.iterate(name -> functionalCount.incrementAndGet());
        
        long streamCount = stream.stream().count();
        
        // All should have same count
        assertEquals(4, traditionalCount.get());
        assertEquals(4, iterableCount.get());
        assertEquals(4, functionalCount.get());
        assertEquals(4, streamCount);
    }
    
    @Test
    void testIteratorSafety() {
        NameRepository repository = new NameRepository();
        Iterator iterator = repository.getIterator();
        
        // Test multiple iterations
        while (iterator.hasNext()) {
            Object name = iterator.next();
            assertTrue(name instanceof String);
        }
        
        // Test behavior after exhaustion
        assertFalse(iterator.hasNext());
    }
    
    @Test
    void testModernIteratorFeatures() {
        // Test Java's built-in Iterable interface
        IterableNameRepository repository = new IterableNameRepository();
        
        // Can use enhanced for loop
        for (String name : repository) {
            assertNotNull(name);
            assertTrue(name.length() > 0);
        }
        
        // Can use stream from Iterable
        long count = repository.getNames().stream().count();
        assertEquals(4, count);
        
        // Test functional operations
        FunctionalNameRepository functional = new FunctionalNameRepository();
        String[] names = functional.getNames();
        assertEquals(4, names.length);
    }
}