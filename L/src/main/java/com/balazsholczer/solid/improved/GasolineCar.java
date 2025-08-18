package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Modern gasoline car implementation
 */
public class GasolineCar implements FuelVehicle {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicReference<Double> speed = new AtomicReference<>(0.0);
    private final AtomicReference<Double> fuelLevel = new AtomicReference<>(50.0);
    private final double fuelCapacity = 60.0;
    
    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> {
            if (fuelLevel.get() > 0) {
                running.set(true);
                System.out.println("GasolineCar: Engine started");
            } else {
                throw new IllegalStateException("Cannot start: No fuel");
            }
        });
    }
    
    @Override
    public CompletableFuture<Void> stop() {
        return CompletableFuture.runAsync(() -> {
            running.set(false);
            speed.set(0.0);
            System.out.println("GasolineCar: Engine stopped");
        });
    }
    
    @Override
    public CompletableFuture<Double> getSpeed() {
        return CompletableFuture.completedFuture(speed.get());
    }
    
    @Override
    public CompletableFuture<Boolean> isRunning() {
        return CompletableFuture.completedFuture(running.get());
    }
    
    @Override
    public String getVehicleType() {
        return "Gasoline Car";
    }
    
    @Override
    public CompletableFuture<Void> refuel(double liters) {
        return CompletableFuture.runAsync(() -> {
            double newLevel = Math.min(fuelLevel.get() + liters, fuelCapacity);
            fuelLevel.set(newLevel);
            System.out.println("GasolineCar: Refueled " + liters + "L, current: " + newLevel + "L");
        });
    }
    
    @Override
    public CompletableFuture<Double> getFuelLevel() {
        return CompletableFuture.completedFuture(fuelLevel.get());
    }
    
    @Override
    public CompletableFuture<Double> getFuelCapacity() {
        return CompletableFuture.completedFuture(fuelCapacity);
    }
}