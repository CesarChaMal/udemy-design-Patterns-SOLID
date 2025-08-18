package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for fuel-powered vehicles
 */
public interface FuelVehicle extends Vehicle {
    CompletableFuture<Void> refuel(double liters);
    CompletableFuture<Double> getFuelLevel();
    CompletableFuture<Double> getFuelCapacity();
}