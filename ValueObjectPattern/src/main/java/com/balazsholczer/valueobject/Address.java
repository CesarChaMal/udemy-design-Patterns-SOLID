package com.balazsholczer.valueobject;

import java.util.Objects;

public final class Address {
    
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;
    
    public Address(String street, String city, String state, String zipCode, String country) {
        this.street = Objects.requireNonNull(street, "Street cannot be null");
        this.city = Objects.requireNonNull(city, "City cannot be null");
        this.state = Objects.requireNonNull(state, "State cannot be null");
        this.zipCode = Objects.requireNonNull(zipCode, "Zip code cannot be null");
        this.country = Objects.requireNonNull(country, "Country cannot be null");
    }
    
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZipCode() { return zipCode; }
    public String getCountry() { return country; }
    
    public String getFullAddress() {
        return street + ", " + city + ", " + state + " " + zipCode + ", " + country;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Address address = (Address) obj;
        return Objects.equals(street, address.street) &&
               Objects.equals(city, address.city) &&
               Objects.equals(state, address.state) &&
               Objects.equals(zipCode, address.zipCode) &&
               Objects.equals(country, address.country);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, zipCode, country);
    }
    
    @Override
    public String toString() {
        return getFullAddress();
    }
}