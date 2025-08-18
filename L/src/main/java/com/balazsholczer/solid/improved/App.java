package com.balazsholczer.solid.improved;

import java.util.Arrays;
import java.util.List;

/**
 * Modern Liskov Substitution Principle Implementation
 * 
 * Improvements:
 * - Proper interface segregation for different vehicle types
 * - Async operations with CompletableFuture
 * - Thread-safe atomic operations
 * - Proper LSP compliance - subtypes are fully substitutable
 * - Modern exception handling
 * - Functional programming with streams
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("=== Modern Liskov Substitution Principle ===");
        System.out.println("Improved with proper interface design and async operations");
        System.out.println();
        
        // Create different vehicle types
        Vehicle gasCar = new GasolineCar();
        Vehicle tesla = new Tesla();
        
        List<Vehicle> vehicles = Arrays.asList(gasCar, tesla);
        VehicleService service = new VehicleService();
        
        try {
            // Demonstrate LSP - service works with any Vehicle implementation
            System.out.println("--- Starting All Vehicles ---");
            service.startAllVehicles(vehicles).join();
            
            System.out.println("\n--- Vehicle Status ---");
            service.displayVehicleStatus(vehicles).join();
            
            System.out.println("\n--- Stopping All Vehicles ---");
            service.stopAllVehicles(vehicles).join();
            
            // Demonstrate specific vehicle capabilities
            System.out.println("\n--- Specific Vehicle Operations ---");
            
            if (gasCar instanceof FuelVehicle fuelVehicle) {
                System.out.println("Refueling gasoline car...");
                fuelVehicle.refuel(20.0).join();
                double fuelLevel = fuelVehicle.getFuelLevel().join();
                System.out.println("Fuel level: " + fuelLevel + "L");
            }
            
            if (tesla instanceof ElectricVehicle electricVehicle) {
                System.out.println("Charging Tesla...");
                electricVehicle.charge(15.0).join();
                double batteryLevel = electricVehicle.getBatteryLevel().join();
                double range = electricVehicle.getRange().join();
                System.out.println("Battery: " + batteryLevel + " kWh, Range: " + range + " km");
            }
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Modern LSP Benefits ===");
        System.out.println("✅ Proper interface segregation");
        System.out.println("✅ Async operations with CompletableFuture");
        System.out.println("✅ Thread-safe atomic operations");
        System.out.println("✅ Full substitutability compliance");
        System.out.println("✅ Type-safe pattern matching");
    }
}