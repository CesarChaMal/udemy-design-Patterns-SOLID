package com.balazsholczer.observer;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class StreamWeatherStation {
    
    public record WeatherData(int pressure, int temperature, int humidity) {}
    
    private int pressure, temperature, humidity;
    
    public CompletableFuture<WeatherData> setPressureAsync(int pressure) {
        return CompletableFuture.supplyAsync(() -> {
            this.pressure = pressure;
            return new WeatherData(this.pressure, temperature, humidity);
        });
    }
    
    public CompletableFuture<WeatherData> setTemperatureAsync(int temperature) {
        return CompletableFuture.supplyAsync(() -> {
            this.temperature = temperature;
            return new WeatherData(pressure, this.temperature, humidity);
        });
    }
    
    public CompletableFuture<WeatherData> setHumidityAsync(int humidity) {
        return CompletableFuture.supplyAsync(() -> {
            this.humidity = humidity;
            return new WeatherData(pressure, temperature, this.humidity);
        });
    }
    
    public void observeChanges(Consumer<WeatherData> observer) {
        setPressureAsync(100).thenAccept(observer);
        setTemperatureAsync(200).thenAccept(observer);
        setHumidityAsync(300).thenAccept(observer);
    }
}