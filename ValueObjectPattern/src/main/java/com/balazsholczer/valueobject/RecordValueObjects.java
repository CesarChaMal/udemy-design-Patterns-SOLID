package com.balazsholczer.valueobject;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordValueObjects {
    
    public record MoneyRecord(BigDecimal amount, String currency) {
        
        public MoneyRecord {
            if (amount == null) throw new IllegalArgumentException("Amount cannot be null");
            if (currency == null) throw new IllegalArgumentException("Currency cannot be null");
        }
        
        public MoneyRecord add(MoneyRecord other) {
            validateSameCurrency(other);
            return new MoneyRecord(amount.add(other.amount), currency);
        }
        
        public MoneyRecord multiply(BigDecimal factor) {
            return new MoneyRecord(amount.multiply(factor), currency);
        }
        
        private void validateSameCurrency(MoneyRecord other) {
            if (!currency.equals(other.currency)) {
                throw new IllegalArgumentException("Cannot operate on different currencies");
            }
        }
    }
    
    public record PersonName(String firstName, String lastName) {
        
        public PersonName {
            if (firstName == null || firstName.trim().isEmpty()) {
                throw new IllegalArgumentException("First name cannot be null or empty");
            }
            if (lastName == null || lastName.trim().isEmpty()) {
                throw new IllegalArgumentException("Last name cannot be null or empty");
            }
        }
        
        public String getFullName() {
            return firstName + " " + lastName;
        }
        
        public String getInitials() {
            return firstName.charAt(0) + "." + lastName.charAt(0) + ".";
        }
    }
    
    public record DateRange(LocalDate startDate, LocalDate endDate) {
        
        public DateRange {
            if (startDate == null) throw new IllegalArgumentException("Start date cannot be null");
            if (endDate == null) throw new IllegalArgumentException("End date cannot be null");
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date must be before or equal to end date");
            }
        }
        
        public long getDays() {
            return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        }
        
        public boolean contains(LocalDate date) {
            return !date.isBefore(startDate) && !date.isAfter(endDate);
        }
        
        public boolean overlaps(DateRange other) {
            return !endDate.isBefore(other.startDate) && !other.endDate.isBefore(startDate);
        }
    }
}