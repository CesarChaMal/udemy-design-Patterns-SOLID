package com.balazsholczer.builder;

public class ModernBuilderDemo {
    
    public static void main(String[] args) {
        
        // 1. Traditional Builder (existing)
        Person traditional = new Person.Builder("John", "john@email.com")
            .setAddress("123 Main St")
            .setAge(30)
            .setNameOfMother("Mary")
            .build();
        
        System.out.println("Traditional: " + traditional);
        
        // 2. Records Builder (Java 14+)
        PersonRecord record = PersonRecord.of("Alice", "alice@email.com")
            .withAddress("456 Oak Ave")
            .withAge(25)
            .withMother("Susan");
        
        System.out.println("Records: " + record);
        
        // 3. Functional Builder
        PersonFunctional functional = PersonFunctional.build("Bob", "bob@email.com", b -> b
            .address("789 Pine St")
            .age(35)
            .mother("Linda"));
        
        System.out.println("Functional: " + functional);
    }
}