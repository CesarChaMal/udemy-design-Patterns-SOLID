package com.balazsholczer.valueobject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Value Object Pattern: immutable objects representing values without identity
 * 
 * Key Concepts:
 * - Immutable objects that represent values, not entities
 * - Equality based on value, not identity
 * - No identity - two value objects with same values are considered equal
 * - Thread-safe due to immutability
 * 
 * Structure:
 * - ValueObject: immutable class with value-based equality
 * - All fields are final and private
 * - No setters, only getters and operations that return new instances
 * - Proper equals(), hashCode(), and toString() implementations
 * 
 * Benefits:
 * - Thread-safe due to immutability
 * - Can be safely shared and cached
 * - Prevents accidental modification
 * - Clear value semantics
 * 
 * Use Cases:
 * - Money, Currency, Date ranges
 * - Coordinates, Colors, Dimensions
 * - Email addresses, Phone numbers
 * - Domain-specific values
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Value Object Pattern ===");
        System.out.println("Immutable objects representing values without identity");
        System.out.println();
        
        System.out.println("=== Traditional Value Objects ===");
        
        // Money value object
        Money price1 = new Money(100.50, "USD");
        Money price2 = new Money(50.25, "USD");
        Money total = price1.add(price2);
        
        System.out.println("Price 1: " + price1);
        System.out.println("Price 2: " + price2);
        System.out.println("Total: " + total);
        
        // Value equality demonstration
        Money sameMoney1 = new Money(100.50, "USD");
        Money sameMoney2 = new Money(100.50, "USD");
        System.out.println("Same values equal: " + sameMoney1.equals(sameMoney2));
        System.out.println("Same hash codes: " + (sameMoney1.hashCode() == sameMoney2.hashCode()));
        
        // Address value object
        Address address1 = new Address("123 Main St", "New York", "NY", "10001", "USA");
        Address address2 = new Address("123 Main St", "New York", "NY", "10001", "USA");
        
        System.out.println("\nAddress 1: " + address1);
        System.out.println("Address 2: " + address2);
        System.out.println("Addresses equal: " + address1.equals(address2));
        
        System.out.println("\n=== Record Value Objects ===");
        
        // Record-based value objects (modern approach)
        var money1 = new RecordValueObjects.MoneyRecord(BigDecimal.valueOf(200), "EUR");
        var money2 = new RecordValueObjects.MoneyRecord(BigDecimal.valueOf(100), "EUR");
        var moneySum = money1.add(money2);
        
        System.out.println("Record Money 1: " + money1);
        System.out.println("Record Money 2: " + money2);
        System.out.println("Record Sum: " + moneySum);
        
        // Person name value object
        var name = new RecordValueObjects.PersonName("John", "Doe");
        System.out.println("Full name: " + name.getFullName());
        System.out.println("Initials: " + name.getInitials());
        
        // Date range value object
        var dateRange = new RecordValueObjects.DateRange(
            LocalDate.of(2024, 1, 1), 
            LocalDate.of(2024, 12, 31)
        );
        System.out.println("Date range: " + dateRange);
        System.out.println("Days in range: " + dateRange.getDays());
        System.out.println("Contains today: " + dateRange.contains(LocalDate.now()));
        
        System.out.println("\n=== Value Object Benefits ===");
        System.out.println("✅ Immutable - cannot be modified after creation");
        System.out.println("✅ Thread-safe - can be shared between threads");
        System.out.println("✅ Value equality - equal if values are equal");
        System.out.println("✅ Cacheable - safe to cache and reuse");
        System.out.println("✅ Domain modeling - represents business concepts clearly");
        
        System.out.println("\n=== Immutability Demonstration ===");
        Money originalMoney = new Money(100, "USD");
        Money modifiedMoney = originalMoney.multiply(BigDecimal.valueOf(2));
        
        System.out.println("Original money: " + originalMoney + " (unchanged)");
        System.out.println("Modified money: " + modifiedMoney + " (new instance)");
        System.out.println("Different instances: " + (originalMoney != modifiedMoney));
    }
}