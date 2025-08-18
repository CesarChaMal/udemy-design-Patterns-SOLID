package com.balazsholczer.prototype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Prototype Pattern
 * Tests Traditional, Functional, Record, and Serializable approaches
 */
class PrototypeTest {
    
    @Test
    void testTraditionalPrototype() {
        Book original = new Book("Design Patterns", "GoF", 400, "Technical");
        Book cloned = (Book) original.clone();
        
        assertNotSame(original, cloned);
        assertEquals(original.getTitle(), cloned.getTitle());
        assertEquals(original.getAuthor(), cloned.getAuthor());
        assertEquals(original.getPages(), cloned.getPages());
        assertEquals(original.getGenre(), cloned.getGenre());
    }
    
    @Test
    void testFunctionalPrototype() {
        var manager = new FunctionalPrototype.PrototypeManager<String>();
        manager.registerPrototype("test", "Test Data", FunctionalPrototype.STRING_CLONER);
        
        String original = manager.create("test");
        String cloned = manager.clone("test");
        
        assertEquals(original, cloned);
        assertNotNull(original);
        assertNotNull(cloned);
    }
    
    @Test
    void testRecordPrototype() {
        var registry = new RecordPrototype.RecordRegistry<RecordPrototype.BookRecord>();
        var original = new RecordPrototype.BookRecord("Test Book", "Author", 300, "Fiction");
        registry.register("book", original);
        
        var retrieved = registry.get("book");
        
        assertEquals(original.title(), retrieved.title());
        assertEquals(original.author(), retrieved.author());
        assertEquals(original.pages(), retrieved.pages());
        assertEquals(original.genre(), retrieved.genre());
    }
    
    @Test
    void testSerializablePrototype() {
        var original = new SerializablePrototype.SerializableBook("Test Book", "Author", 250, java.util.List.of("tag1", "tag2"));
        var cloned = original.deepClone();
        
        assertNotSame(original, cloned);
        assertEquals(original.getTitle(), cloned.getTitle());
        assertEquals(original.getAuthor(), cloned.getAuthor());
        assertEquals(original.getPages(), cloned.getPages());
        assertEquals(original.getTags(), cloned.getTags());
    }
    
    @Test
    void testPrototypeRegistry() {
        PrototypeRegistry registry = new PrototypeRegistry();
        Book bookPrototype = new Book("Template Book", "Unknown", 200, "Template");
        registry.registerPrototype("book", bookPrototype);
        
        Prototype cloned = registry.getPrototype("book");
        
        assertNotSame(bookPrototype, cloned);
        assertTrue(cloned instanceof Book);
        assertEquals("Template Book", ((Book) cloned).getTitle());
    }
    
    @Test
    void testDeepCloning() {
        // Test serializable deep cloning
        var registry = new SerializablePrototype.DeepCloneRegistry();
        var original = new SerializablePrototype.SerializableBook("Complex Book", "Author", 300, java.util.List.of("complex", "deep"));
        registry.register("complex", original);
        
        var shallow = registry.getShallowClone("complex");
        var deep = registry.getDeepClone("complex");
        
        assertNotSame(original, shallow);
        assertNotSame(original, deep);
        assertEquals(original.getTitle(), shallow.getTitle());
        assertEquals(original.getTitle(), deep.getTitle());
    }
    
    @Test
    void testEquivalence() {
        String testData = "Test Data";
        int testValue = 123;
        
        // Traditional
        Book traditional = new Book(testData, "Author", testValue, "Genre");
        Book traditionalClone = (Book) traditional.clone();
        
        // Functional
        var functionalManager = new FunctionalPrototype.PrototypeManager<String>();
        functionalManager.registerPrototype("test", testData, FunctionalPrototype.STRING_CLONER);
        String functional = functionalManager.create("test");
        String functionalClone = functionalManager.clone("test");
        
        // Record
        var record = new RecordPrototype.BookRecord(testData, "Author", testValue, "Genre");
        var recordClone = record.withTitle(testData); // Records are immutable, this creates a copy
        
        // Serializable
        var serializable = new SerializablePrototype.SerializableBook(testData, "Author", testValue, java.util.List.of("tag"));
        var serializableClone = serializable.deepClone();
        
        // All clones should be different objects but with same data
        assertNotSame(traditional, traditionalClone);
        assertEquals(functional, functionalClone); // Strings are immutable
        assertNotSame(serializable, serializableClone);
        
        assertEquals(traditional.getTitle(), traditionalClone.getTitle());
        assertEquals(functional, functionalClone);
        assertEquals(record.title(), recordClone.title());
        assertEquals(serializable.getTitle(), serializableClone.getTitle());
    }
}