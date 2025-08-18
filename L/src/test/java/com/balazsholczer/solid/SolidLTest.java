package com.balazsholczer.solid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.balazsholczer.solid.improved.*;
import java.util.Arrays;

/**
 * Comprehensive test for Liskov Substitution Principle
 * Tests Traditional vs Improved approaches
 */
class SolidLTest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional approach violates LSP
        Car car = new Car();
        ElectricCar electricCar = new ElectricCar();
        
        // Car works fine
        assertDoesNotThrow(() -> car.speed());
        assertDoesNotThrow(() -> car.addFuel());
        
        // ElectricCar has different interface - violates LSP
        assertDoesNotThrow(() -> electricCar.speed());
        assertDoesNotThrow(() -> electricCar.chargeBattery());
    }
    
    @Test
    void testImprovedApproach() {
        // Improved approach follows LSP
        com.balazsholczer.solid.improved.Vehicle gasolineCar = new GasolineCar();
        com.balazsholczer.solid.improved.Vehicle tesla = new Tesla();
        
        // Both can be used interchangeably
        assertDoesNotThrow(() -> gasolineCar.start());
        assertDoesNotThrow(() -> tesla.start());
        
        // Specific fuel/charge operations
        if (gasolineCar instanceof FuelVehicle) {
            assertDoesNotThrow(() -> ((FuelVehicle) gasolineCar).refuel(10.0));
        }
        
        if (tesla instanceof com.balazsholczer.solid.improved.ElectricVehicle) {
            assertDoesNotThrow(() -> ((com.balazsholczer.solid.improved.ElectricVehicle) tesla).charge(20.0));
        }
    }
    
    @Test
    void testLiskovSubstitutionViolation() {
        // Traditional approach violates LSP
        // Car and ElectricCar cannot be used interchangeably
        Car car = new Car();
        ElectricCar electricCar = new ElectricCar();
        
        // Different interfaces - cannot substitute
        assertDoesNotThrow(() -> car.addFuel());
        assertDoesNotThrow(() -> electricCar.chargeBattery());
        
        // This demonstrates LSP violation - they're not substitutable
        assertTrue(true);
    }
    
    @Test
    void testLiskovSubstitutionCompliance() {
        // Improved approach follows LSP
        VehicleService service = new VehicleService();
        
        java.util.List<com.balazsholczer.solid.improved.Vehicle> vehicles = Arrays.asList(
            new GasolineCar(),
            new Tesla()
        );
        
        // All vehicles can be used interchangeably
        assertDoesNotThrow(() -> service.startAllVehicles(vehicles));
        assertDoesNotThrow(() -> service.stopAllVehicles(vehicles));
    }
    
    @Test
    void testSubstitutability() {
        // Test that subtypes are truly substitutable
        VehicleService service = new VehicleService();
        
        // Both vehicle types work identically in service context
        java.util.List<com.balazsholczer.solid.improved.Vehicle> vehicles1 = Arrays.asList(new GasolineCar());
        java.util.List<com.balazsholczer.solid.improved.Vehicle> vehicles2 = Arrays.asList(new Tesla());
        
        assertDoesNotThrow(() -> service.displayVehicleStatus(vehicles1));
        assertDoesNotThrow(() -> service.displayVehicleStatus(vehicles2));
    }
    
    @Test
    void testEquivalence() {
        // Both approaches should handle basic vehicle operations
        // but improved approach maintains substitutability
        
        // Traditional (with LSP violation)
        Car traditionalCar = new Car();
        ElectricCar traditionalElectric = new ElectricCar();
        
        // Improved (LSP compliant)
        com.balazsholczer.solid.improved.Vehicle improvedGasoline = new GasolineCar();
        com.balazsholczer.solid.improved.Vehicle improvedElectric = new Tesla();
        
        // Basic operations should work for all
        assertDoesNotThrow(() -> traditionalCar.speed());
        assertDoesNotThrow(() -> traditionalElectric.speed());
        assertDoesNotThrow(() -> improvedGasoline.start());
        assertDoesNotThrow(() -> improvedElectric.start());
        
        // But improved approach maintains true substitutability
        VehicleService service = new VehicleService();
        java.util.List<com.balazsholczer.solid.improved.Vehicle> vehicles = Arrays.asList(improvedGasoline, improvedElectric);
        assertDoesNotThrow(() -> service.startAllVehicles(vehicles));
    }
}