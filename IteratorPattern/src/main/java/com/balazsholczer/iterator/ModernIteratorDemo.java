package com.balazsholczer.iterator;

public class ModernIteratorDemo {
    
    public static void main(String[] args) {
        
        System.out.println("=== Traditional Iterator Pattern ===");
        NameRepository traditional = new NameRepository();
        for (Iterator iter = traditional.getIterator(); iter.hasNext();) {
            String name = (String) iter.next();
            System.out.println(name);
        }
        
        System.out.println("\n=== Iterable Pattern (Enhanced For-Loop) ===");
        IterableNameRepository iterable = new IterableNameRepository();
        for (String name : iterable) {
            System.out.println(name);
        }
        
        System.out.println("\n=== Stream Pattern ===");
        StreamNameRepository stream = new StreamNameRepository();
        stream.forEach();
        
        System.out.println("\n=== Functional Pattern ===");
        FunctionalNameRepository functional = new FunctionalNameRepository();
        functional.iterate(System.out::println);
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Stream - Filtered (starts with 'J'):");
        stream.forEachFiltered("J");
        
        System.out.println("Functional - Conditional (length > 3):");
        functional.iterateIf(name -> name.length() > 3, System.out::println);
        
        System.out.println("Stream - Transformed (uppercase):");
        stream.stream()
            .map(String::toUpperCase)
            .forEach(System.out::println);
    }
}