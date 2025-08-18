package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Modern Tesla electric car implementation
 */
public class Tesla implements ElectricVehicle {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicReference<Double> speed = new AtomicReference<>(0.0);
    private final AtomicReference<Double> batteryLevel = new AtomicReference<>(80.0);
    private final double batteryCapacity = 100.0;
    private final double rangePerKwh = 4.0; // km per kWh
    
    @Override
    public CompletableFuture<Void> start() {
        return CompletableFuture.runAsync(() -> {
            if (batteryLevel.get() > 5.0) {
                running.set(true);
                System.out.println("Tesla: Electric motor started silently");
            } else {
                throw new IllegalStateException("Cannot start: Battery too low");
            }
        });
    }
    
    @Override
    public CompletableFuture<Void> stop() {
        return CompletableFuture.runAsync(() -> {
            running.set(false);
            speed.set(0.0);
            System.out.println("Tesla: Electric motor stopped");
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
        return "Tesla Electric Car";
    }
    
    @Override
    public CompletableFuture<Void> charge(double kwh) {
        return CompletableFuture.runAsync(() -> {
            double newLevel = Math.min(batteryLevel.get() + kwh, batteryCapacity);
            batteryLevel.set(newLevel);
            System.out.println("Tesla: Charged " + kwh + " kWh, current: " + newLevel + " kWh");
        });
    }
    
    @Override
    public CompletableFuture<Double> getBatteryLevel() {
        return CompletableFuture.completedFuture(batteryLevel.get());
    }
    
    @Override
    public CompletableFuture<Double> getBatteryCapacity() {
        return CompletableFuture.completedFuture(batteryCapacity);
    }
    
    @Override
    public CompletableFuture<Double> getRange() {
        return CompletableFuture.completedFuture(batteryLevel.get() * rangePerKwh);
    }
}