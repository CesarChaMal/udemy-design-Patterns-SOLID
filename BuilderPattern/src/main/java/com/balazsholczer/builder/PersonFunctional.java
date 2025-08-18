package com.balazsholczer.builder;

import java.util.function.Consumer;

public class PersonFunctional {
    
    private final String name;
    private final String email;
    private final String address;
    private final int age;
    private final String nameOfMother;
    
    private PersonFunctional(String name, String email, String address, int age, String nameOfMother) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.nameOfMother = nameOfMother;
    }
    
    public static PersonFunctional build(String name, String email, Consumer<Builder> config) {
        var builder = new Builder(name, email);
        config.accept(builder);
        return builder.build();
    }
    
    public static class Builder {
        private final String name;
        private final String email;
        private String address;
        private int age;
        private String nameOfMother;
        
        Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }
        
        public Builder address(String address) {
            this.address = address;
            return this;
        }
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public Builder mother(String nameOfMother) {
            this.nameOfMother = nameOfMother;
            return this;
        }
        
        PersonFunctional build() {
            return new PersonFunctional(name, email, address, age, nameOfMother);
        }
    }
    
    @Override
    public String toString() {
        return name + "-" + address + "-" + email + "-" + age;
    }
}