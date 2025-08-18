package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;

/**
 * Modern vehicle interface with proper LSP compliance
 */
public interface Vehicle {
    CompletableFuture<Void> start();
    CompletableFuture<Void> stop();
    CompletableFuture<Double> getSpeed();
    CompletableFuture<Boolean> isRunning();
    String getVehicleType();
}