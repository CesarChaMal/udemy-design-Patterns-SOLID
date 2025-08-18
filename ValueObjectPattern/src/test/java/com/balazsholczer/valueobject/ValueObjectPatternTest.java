package com.balazsholczer.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.math.BigDecimal;

/**
 * Comprehensive test class for Value Object Pattern - Traditional vs Modern approaches
 */
class ValueObjectPatternTest {
    
    private Money testMoney;
    private Address testAddress;
    
    @BeforeEach
    void setUp() {
        testMoney = new Money(100.0, "USD");
        testAddress = new Address("123 Main St", "New York", "NY", "10001", "USA");
    }
    
    @Test
    void testTraditionalValueObjects() {
        // Traditional value object pattern
        Money money1 = new Money(50.0, "USD");
        Money money2 = new Money(50.0, "USD");
        Money money3 = new Money(75.0, "USD");
        
        // Value objects with same values should be equal
        assertEquals(money1, money2);
        assertEquals(money1.hashCode(), money2.hashCode());
        
        // Value objects with different values should not be equal
        assertNotEquals(money1, money3);
        assertNotEquals(money1.hashCode(), money3.hashCode());
        
        // Value objects should be immutable
        Money originalMoney = new Money(100.0, "USD");
        Money addedMoney = originalMoney.add(new Money(50.0, "USD"));
        
        assertEquals(BigDecimal.valueOf(100.0), originalMoney.getAmount()); // Original unchanged
        assertEquals(BigDecimal.valueOf(150.0), addedMoney.getAmount()); // New object created
    }
    
    @Test
    void testRecordValueObjects() {
        // Modern record-based value objects
        RecordValueObjects.MoneyRecord money1 = new RecordValueObjects.MoneyRecord(BigDecimal.valueOf(100.0), "USD");
        RecordValueObjects.MoneyRecord money2 = new RecordValueObjects.MoneyRecord(BigDecimal.valueOf(100.0), "USD");
        RecordValueObjects.MoneyRecord money3 = new RecordValueObjects.MoneyRecord(BigDecimal.valueOf(200.0), "USD");
        
        // Records automatically provide equals/hashCode
        assertEquals(money1, money2);
        assertEquals(money1.hashCode(), money2.hashCode());
        assertNotEquals(money1, money3);
        
        // Records are immutable by default
        assertEquals(BigDecimal.valueOf(100.0), money1.amount());
        assertEquals("USD", money1.currency());
        
        // Test record operations
        RecordValueObjects.MoneyRecord doubled = money1.multiply(BigDecimal.valueOf(2.0));
        assertEquals(0, BigDecimal.valueOf(200.0).compareTo(doubled.amount()));
        assertEquals(0, BigDecimal.valueOf(100.0).compareTo(money1.amount())); // Original unchanged
    }
    
    @Test
    void testValueObjectEquality() {
        // Test equality based on values, not identity
        Address address1 = new Address("123 Main St", "New York", "NY", "10001", "USA");
        Address address2 = new Address("123 Main St", "New York", "NY", "10001", "USA");
        Address address3 = new Address("456 Oak Ave", "Boston", "MA", "02101", "USA");
        
        // Same values = equal
        assertEquals(address1, address2);
        assertTrue(address1.equals(address2));
        assertEquals(address1.hashCode(), address2.hashCode());
        
        // Different values = not equal
        assertNotEquals(address1, address3);
        assertFalse(address1.equals(address3));
        
        // Not equal to null or different types
        assertNotEquals(address1, null);
        assertNotEquals(address1, "not an address");
    }
    
    @Test
    void testImmutability() {
        // Test that value objects are immutable
        Money originalMoney = new Money(100.0, "USD");
        
        // Operations should return new objects
        Money addedMoney = originalMoney.add(new Money(25.0, "USD"));
        Money subtractedMoney = originalMoney.subtract(new Money(25.0, "USD"));
        Money multipliedMoney = originalMoney.multiply(BigDecimal.valueOf(2.0));
        
        // Original should be unchanged
        assertEquals(BigDecimal.valueOf(100.0), originalMoney.getAmount());
        assertEquals("USD", originalMoney.getCurrency().getCurrencyCode());
        
        // New objects should have correct values
        assertEquals(0, BigDecimal.valueOf(125.0).compareTo(addedMoney.getAmount()));
        assertEquals(0, BigDecimal.valueOf(75.0).compareTo(subtractedMoney.getAmount()));
        assertEquals(0, BigDecimal.valueOf(200.0).compareTo(multipliedMoney.getAmount()));
    }
    
    @Test
    void testValueObjectValidation() {
        // Test validation in value objects - Money doesn't validate negative amounts
        assertDoesNotThrow(() -> {
            new Money(-100.0, "USD"); // Money allows negative amounts
        });
        
        assertThrows(NullPointerException.class, () -> {
            new Money(100.0, null); // Null currency
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(100.0, "INVALID"); // Invalid currency code
        });
        
        assertThrows(NullPointerException.class, () -> {
            new Address(null, "City", "State", "12345", "USA"); // Null street
        });
    }
    
    @Test
    void testMoneyOperations() {
        // Test money-specific operations
        Money money1 = new Money(100.0, "USD");
        Money money2 = new Money(50.0, "USD");
        Money money3 = new Money(75.0, "EUR");
        
        // Same currency operations
        Money sum = money1.add(money2);
        assertEquals(BigDecimal.valueOf(150.0), sum.getAmount());
        assertEquals("USD", sum.getCurrency().getCurrencyCode());
        
        Money difference = money1.subtract(money2);
        assertEquals(0, BigDecimal.valueOf(50.0).compareTo(difference.getAmount()));
        
        // Different currency should throw exception
        assertThrows(IllegalArgumentException.class, () -> {
            money1.add(money3); // USD + EUR
        });
        
        // Multiplication
        Money doubled = money1.multiply(BigDecimal.valueOf(2.0));
        assertEquals(0, BigDecimal.valueOf(200.0).compareTo(doubled.getAmount()));
    }
    
    @Test
    void testAddressOperations() {
        // Test address-specific operations
        Address address = new Address("123 Main St", "New York", "NY", "10001", "USA");
        
        assertEquals("123 Main St", address.getStreet());
        assertEquals("New York", address.getCity());
        assertEquals("NY", address.getState());
        assertEquals("10001", address.getZipCode());
        assertEquals("USA", address.getCountry());
        
        // Test formatted address
        String formatted = address.getFullAddress();
        assertTrue(formatted.contains("123 Main St"));
        assertTrue(formatted.contains("New York"));
        assertTrue(formatted.contains("NY"));
        assertTrue(formatted.contains("10001"));
        assertTrue(formatted.contains("USA"));
        
        // Test address with different zip code
        Address sameStreetDifferentZip = new Address("123 Main St", "New York", "NY", "10002", "USA");
        assertNotEquals(address, sameStreetDifferentZip);
    }
    
    @Test
    void testValueObjectInCollections() {
        // Test value objects in collections
        Set<Money> moneySet = new HashSet<>();
        moneySet.add(new Money(100.0, "USD"));
        moneySet.add(new Money(100.0, "USD")); // Duplicate value
        moneySet.add(new Money(200.0, "USD"));
        
        // Set should contain only unique values
        assertEquals(2, moneySet.size());
        
        // Test in maps
        Map<Address, String> addressMap = new HashMap<>();
        Address address1 = new Address("123 Main St", "New York", "NY", "10001", "USA");
        Address address2 = new Address("123 Main St", "New York", "NY", "10001", "USA"); // Same values
        
        addressMap.put(address1, "John Doe");
        addressMap.put(address2, "Jane Doe"); // Should overwrite
        
        assertEquals(1, addressMap.size());
        assertEquals("Jane Doe", addressMap.get(address1)); // Can retrieve with equivalent key
    }
    
    @Test
    void testRecordValueObjectOperations() {
        // Test operations with record value objects
        RecordValueObjects.PersonName name1 = new RecordValueObjects.PersonName("John", "Doe");
        RecordValueObjects.PersonName name2 = new RecordValueObjects.PersonName("John", "Doe");
        
        assertEquals(name1, name2);
        assertEquals(name1.hashCode(), name2.hashCode());
        
        // Test record methods
        assertEquals("John", name1.firstName());
        assertEquals("Doe", name1.lastName());
        assertEquals("John Doe", name1.getFullName());
        assertEquals("J.D.", name1.getInitials());
        
        // Test record in collections
        Set<RecordValueObjects.PersonName> nameSet = new HashSet<>();
        nameSet.add(name1);
        nameSet.add(name2);
        assertEquals(1, nameSet.size()); // Duplicates removed
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify traditional and record approaches produce equivalent behavior
        Money traditionalMoney = new Money(100.0, "USD");
        RecordValueObjects.MoneyRecord recordMoney = new RecordValueObjects.MoneyRecord(BigDecimal.valueOf(100.0), "USD");
        
        // Both should represent same value
        assertEquals(traditionalMoney.getAmount(), recordMoney.amount());
        assertEquals(traditionalMoney.getCurrency().getCurrencyCode(), recordMoney.currency());
    }
}