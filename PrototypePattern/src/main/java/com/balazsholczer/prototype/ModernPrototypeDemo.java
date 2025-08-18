package com.balazsholczer.prototype;

import java.util.List;

public class ModernPrototypeDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Prototype Pattern ===");
        
        // Create original prototypes
        Book originalBook = new Book("Design Patterns", "Gang of Four", 395, "Programming");
        
        // Register prototypes
        PrototypeRegistry registry = new PrototypeRegistry();
        registry.registerPrototype("programming-book", originalBook);
        
        // Clone prototypes
        Book clonedBook1 = (Book) registry.getPrototype("programming-book");
        Book clonedBook2 = (Book) registry.getPrototype("programming-book");
        
        // Modify clones
        clonedBook1.setTitle("Clean Code");
        clonedBook1.setAuthor("Robert Martin");
        
        clonedBook2.setTitle("Effective Java");
        clonedBook2.setAuthor("Joshua Bloch");
        
        System.out.println("Original: " + originalBook);
        System.out.println("Clone 1: " + clonedBook1);
        System.out.println("Clone 2: " + clonedBook2);
        
        registry.listPrototypes();
        
        System.out.println("\n=== Record Prototype Pattern ===");
        
        // Create record prototypes
        var bookTemplate = new RecordPrototype.BookRecord("Template Book", "Unknown Author", 0, "General");
        var personTemplate = new RecordPrototype.PersonRecord("John Doe", 30, "john@example.com", List.of("reading"));
        
        // Register record prototypes
        RecordPrototype.RecordRegistry<RecordPrototype.BookRecord> bookRegistry = new RecordPrototype.RecordRegistry<>();
        RecordPrototype.RecordRegistry<RecordPrototype.PersonRecord> personRegistry = new RecordPrototype.RecordRegistry<>();
        
        bookRegistry.register("book-template", bookTemplate);
        personRegistry.register("person-template", personTemplate);
        
        // Create variations using immutable copying
        var book1 = bookRegistry.get("book-template")
                               .withTitle("Java Concurrency")
                               .withAuthor("Brian Goetz")
                               .withPages(384);
        
        var book2 = bookRegistry.get("book-template")
                               .modify("Spring in Action", "Craig Walls")
                               .withPages(520);
        
        var person1 = personRegistry.get("person-template")
                                   .withName("Alice Smith")
                                   .withAge(25)
                                   .addHobby("programming");
        
        System.out.println("Book Template: " + bookTemplate);
        System.out.println("Book 1: " + book1);
        System.out.println("Book 2: " + book2);
        System.out.println("Person Template: " + personTemplate);
        System.out.println("Person 1: " + person1);
        
        System.out.println("\n=== Functional Prototype Pattern ===");
        
        FunctionalPrototype.PrototypeManager<Book> funcManager = new FunctionalPrototype.PrototypeManager<>();
        
        // Register functional prototypes
        Book funcBook = new Book("Functional Programming", "Author", 300, "Programming");
        funcManager.registerPrototype("func-book", funcBook, FunctionalPrototype.BOOK_CLONER);
        
        // Create and clone using functional approach
        Book funcClone1 = funcManager.clone("func-book");
        Book funcClone2 = funcManager.clone("func-book");
        
        funcClone1.setTitle("Reactive Programming");
        funcClone2.setTitle("Lambda Expressions");
        
        System.out.println("Functional Original: " + funcBook);
        System.out.println("Functional Clone 1: " + funcClone1);
        System.out.println("Functional Clone 2: " + funcClone2);
        
        System.out.println("\n=== Serializable Prototype Pattern ===");
        
        // Create serializable prototype
        SerializablePrototype.SerializableBook serializableBook = 
            new SerializablePrototype.SerializableBook("Serialization Guide", "Tech Author", 250, 
                                                      List.of("java", "serialization", "io"));
        
        SerializablePrototype.DeepCloneRegistry deepRegistry = new SerializablePrototype.DeepCloneRegistry();
        deepRegistry.register("serializable-book", serializableBook);
        
        // Demonstrate shallow vs deep cloning
        SerializablePrototype.SerializableBook shallowClone = deepRegistry.getShallowClone("serializable-book");
        SerializablePrototype.SerializableBook deepClone = deepRegistry.getDeepClone("serializable-book");
        
        // Modify original to show cloning differences
        serializableBook.setTitle("Modified Original");
        
        System.out.println("After modifying original:");
        System.out.println("Original: " + serializableBook);
        System.out.println("Shallow Clone: " + shallowClone);
        System.out.println("Deep Clone: " + deepClone);
        
        deepRegistry.demonstrateCloneDifference("serializable-book");
        
        System.out.println("\n=== Advanced Features ===");
        
        System.out.println("Record - Immutable chain modifications:");
        var chainedBook = bookTemplate
            .withTitle("Chained Book")
            .withAuthor("Chain Author")
            .withPages(400)
            .withGenre("Technical");
        System.out.println("Chained: " + chainedBook);
        
        System.out.println("Functional - Custom cloner:");
        FunctionalPrototype.PrototypeManager<String> stringManager = new FunctionalPrototype.PrototypeManager<>();
        stringManager.registerPrototype("greeting", "Hello", String::new);
        String clonedGreeting = stringManager.clone("greeting");
        System.out.println("String clone: " + clonedGreeting);
        
        System.out.println("Traditional - Registry management:");
        System.out.println("Has prototype 'programming-book': " + registry.hasPrototype("programming-book"));
        System.out.println("Has prototype 'missing': " + registry.hasPrototype("missing"));
        
        System.out.println("\n=== Pattern Comparison ===");
        System.out.println("Traditional: Interface-based with manual clone implementation");
        System.out.println("Record: Immutable with builder-style modifications");
        System.out.println("Functional: Function-based cloning with flexible strategies");
        System.out.println("Serializable: Deep cloning via serialization mechanism");
    }
}