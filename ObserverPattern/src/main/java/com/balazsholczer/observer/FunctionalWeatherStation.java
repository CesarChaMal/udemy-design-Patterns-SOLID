package com.balazsholczer.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FunctionalWeatherStation {
    
    private int pressure, temperature, humidity;
    private final List<Consumer<WeatherData>> observers = new ArrayList<>();
    
    public record WeatherData(int pressure, int temperature, int humidity) {}
    
    public void addObserver(Consumer<WeatherData> observer) {
        observers.add(observer);
    }
    
    public void removeObserver(Consumer<WeatherData> observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers() {
        WeatherData data = new WeatherData(pressure, temperature, humidity);
        observers.forEach(observer -> observer.accept(data));
    }
    
    public void setPressure(int pressure) {
        this.pressure = pressure;
        notifyObservers();
    }
    
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        notifyObservers();
    }
    
    public void setHumidity(int humidity) {
        this.humidity = humidity;
        notifyObservers();
    }
}