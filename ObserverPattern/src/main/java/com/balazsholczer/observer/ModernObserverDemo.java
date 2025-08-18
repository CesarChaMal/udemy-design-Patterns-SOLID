package com.balazsholczer.observer;

import com.balazsholczer.observer.EventWeatherStation.WeatherSubscriber;

public class ModernObserverDemo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Traditional Observer Pattern ===");
        WeatherStation traditional = new WeatherStation();
        WeatherObserver observer = new WeatherObserver(traditional);
        traditional.setHumidity(100);
        traditional.setPressure(200);
        traditional.setTemperature(300);
        
        System.out.println("\n=== Functional Observer Pattern ===");
        FunctionalWeatherStation functional = new FunctionalWeatherStation();
        functional.addObserver(data -> 
            System.out.println(data.pressure() + "-" + data.temperature() + "-" + data.humidity()));
        functional.setHumidity(100);
        functional.setPressure(200);
        functional.setTemperature(300);
        
        System.out.println("\n=== Event-Driven Observer Pattern ===");
        EventWeatherStation eventStation = new EventWeatherStation();
        WeatherSubscriber subscriber = new WeatherSubscriber();
        eventStation.subscribe(subscriber);
        eventStation.setHumidity(100);
        eventStation.setPressure(200);
        eventStation.setTemperature(300);
        Thread.sleep(100);
        eventStation.close();
        
        System.out.println("\n=== Stream Observer Pattern ===");
        StreamWeatherStation stream = new StreamWeatherStation();
        stream.observeChanges(data -> 
            System.out.println(data.pressure() + "-" + data.temperature() + "-" + data.humidity()));
        Thread.sleep(100);
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - Multiple Observers:");
        functional.addObserver(data -> System.out.println("Alert: Temperature = " + data.temperature()));
        functional.setTemperature(350);
    }
}