package com.balazsholczer.recordbuilder;

public class App {
    public record Person(String name, int age, String email, String address) {
        public static Builder builder() {
            return new Builder();
        }
        
        public static class Builder {
            private String name;
            private int age;
            private String email;
            private String address;
            
            public Builder name(String name) { this.name = name; return this; }
            public Builder age(int age) { this.age = age; return this; }
            public Builder email(String email) { this.email = email; return this; }
            public Builder address(String address) { this.address = address; return this; }
            
            public Person build() {
                return new Person(name, age, email, address);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Record Builder Pattern Demo ===");
        
        Person person = Person.builder()
            .name("John Doe")
            .age(30)
            .email("john@example.com")
            .address("123 Main St")
            .build();
            
        System.out.println("Built person: " + person);
    }
}