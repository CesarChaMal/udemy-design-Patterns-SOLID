package com.balazsholczer.solid.improved;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service that works with any vehicle type (LSP compliance)
 */
public class VehicleService {
    
    public CompletableFuture<Void> startAllVehicles(List<Vehicle> vehicles) {
        System.out.println("VehicleService: Starting all vehicles");
        
        CompletableFuture<?>[] futures = vehicles.stream()
            .map(this::startVehicle)
            .toArray(CompletableFuture[]::new);
        
        return CompletableFuture.allOf(futures);
    }
    
    public CompletableFuture<Void> stopAllVehicles(List<Vehicle> vehicles) {
        System.out.println("VehicleService: Stopping all vehicles");
        
        CompletableFuture<?>[] futures = vehicles.stream()
            .map(this::stopVehicle)
            .toArray(CompletableFuture[]::new);
        
        return CompletableFuture.allOf(futures);
    }
    
    private CompletableFuture<Void> startVehicle(Vehicle vehicle) {
        return vehicle.start()
            .thenRun(() -> System.out.println("Started: " + vehicle.getVehicleType()));
    }
    
    private CompletableFuture<Void> stopVehicle(Vehicle vehicle) {
        return vehicle.stop()
            .thenRun(() -> System.out.println("Stopped: " + vehicle.getVehicleType()));
    }
    
    public CompletableFuture<Void> displayVehicleStatus(List<Vehicle> vehicles) {
        System.out.println("VehicleService: Displaying vehicle status");
        
        CompletableFuture<?>[] futures = vehicles.stream()
            .map(this::displayStatus)
            .toArray(CompletableFuture[]::new);
        
        return CompletableFuture.allOf(futures);
    }
    
    private CompletableFuture<Void> displayStatus(Vehicle vehicle) {
        return CompletableFuture.allOf(
            vehicle.isRunning(),
            vehicle.getSpeed()
        ).thenRun(() -> {
            try {
                boolean running = vehicle.isRunning().join();
                double speed = vehicle.getSpeed().join();
                System.out.println(vehicle.getVehicleType() + " - Running: " + running + ", Speed: " + speed);
            } catch (Exception e) {
                System.err.println("Error getting status for " + vehicle.getVehicleType());
            }
        });
    }
}