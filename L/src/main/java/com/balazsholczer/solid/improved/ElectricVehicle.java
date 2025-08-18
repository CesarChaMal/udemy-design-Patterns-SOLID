package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for electric vehicles
 */
public interface ElectricVehicle extends Vehicle {
    CompletableFuture<Void> charge(double kwh);
    CompletableFuture<Double> getBatteryLevel();
    CompletableFuture<Double> getBatteryCapacity();
    CompletableFuture<Double> getRange();
}