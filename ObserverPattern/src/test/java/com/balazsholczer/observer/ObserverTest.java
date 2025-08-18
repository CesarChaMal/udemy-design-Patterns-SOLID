package com.balazsholczer.observer;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Observer Pattern
 * Tests Traditional, Event-driven, Functional, and Stream approaches
 */
class ObserverTest {
    
    @Test
    void testTraditionalObserver() {
        WeatherStation station = new WeatherStation();
        WeatherObserver observer1 = new WeatherObserver(station);
        WeatherObserver observer2 = new WeatherObserver(station);
        
        // Test notifications
        assertDoesNotThrow(() -> station.setPressure(1013));
        assertDoesNotThrow(() -> station.setTemperature(25));
        assertDoesNotThrow(() -> station.setHumidity(60));
    }
    
    @Test
    void testObserverRegistration() {
        WeatherStation station = new WeatherStation();
        WeatherObserver observer = new WeatherObserver(station);
        
        // Observer should be automatically registered
        assertDoesNotThrow(() -> station.setPressure(1000));
        
        // Test manual removal
        assertDoesNotThrow(() -> station.removeObserver(observer));
        assertDoesNotThrow(() -> station.setPressure(1020));
    }
    
    @Test
    void testEventWeatherStation() {
        EventWeatherStation station = new EventWeatherStation();
        EventWeatherStation.WeatherSubscriber subscriber = new EventWeatherStation.WeatherSubscriber();
        
        station.subscribe(subscriber);
        
        assertDoesNotThrow(() -> station.setPressure(1013));
        assertDoesNotThrow(() -> station.setTemperature(25));
        assertDoesNotThrow(() -> station.setHumidity(60));
        
        station.close();
    }
    
    @Test
    void testFunctionalWeatherStation() {
        FunctionalWeatherStation station = new FunctionalWeatherStation();
        AtomicInteger updateCount = new AtomicInteger(0);
        
        station.addObserver(data -> {
            updateCount.incrementAndGet();
            assertTrue(data.pressure() >= 0);
            assertTrue(data.temperature() >= 0);
            assertTrue(data.humidity() >= 0);
        });
        
        station.setPressure(1013);
        station.setTemperature(25);
        station.setHumidity(60);
        
        assertEquals(3, updateCount.get());
    }
    
    @Test
    void testStreamWeatherStation() {
        StreamWeatherStation station = new StreamWeatherStation();
        
        CompletableFuture<StreamWeatherStation.WeatherData> pressureFuture = 
            station.setPressureAsync(1013);
        CompletableFuture<StreamWeatherStation.WeatherData> tempFuture = 
            station.setTemperatureAsync(25);
        CompletableFuture<StreamWeatherStation.WeatherData> humidityFuture = 
            station.setHumidityAsync(60);
        
        assertDoesNotThrow(() -> {
            StreamWeatherStation.WeatherData pressureData = pressureFuture.get();
            assertEquals(1013, pressureData.pressure());
            
            StreamWeatherStation.WeatherData tempData = tempFuture.get();
            assertEquals(25, tempData.temperature());
            
            StreamWeatherStation.WeatherData humidityData = humidityFuture.get();
            assertEquals(60, humidityData.humidity());
        });
        
        assertDoesNotThrow(() -> station.observeChanges(data -> 
            System.out.println("Observed: " + data)));
    }
    
    @Test
    void testMultipleObservers() {
        WeatherStation station = new WeatherStation();
        WeatherObserver observer1 = new WeatherObserver(station);
        WeatherObserver observer2 = new WeatherObserver(station);
        WeatherObserver observer3 = new WeatherObserver(station);
        
        // All observers should receive updates
        assertDoesNotThrow(() -> station.setPressure(1015));
        assertDoesNotThrow(() -> station.setTemperature(22));
        assertDoesNotThrow(() -> station.setHumidity(55));
    }
    
    @Test
    void testFunctionalObserverManagement() {
        FunctionalWeatherStation station = new FunctionalWeatherStation();
        AtomicInteger count1 = new AtomicInteger(0);
        AtomicInteger count2 = new AtomicInteger(0);
        
        Consumer<FunctionalWeatherStation.WeatherData> observer1 = data -> count1.incrementAndGet();
        Consumer<FunctionalWeatherStation.WeatherData> observer2 = data -> count2.incrementAndGet();
        
        station.addObserver(observer1);
        station.addObserver(observer2);
        
        station.setPressure(1000);
        assertEquals(1, count1.get());
        assertEquals(1, count2.get());
        
        station.removeObserver(observer1);
        station.setTemperature(20);
        assertEquals(1, count1.get());
        assertEquals(2, count2.get());
    }
    
    @Test
    void testEquivalence() {
        // All approaches should handle weather updates correctly
        WeatherStation traditional = new WeatherStation();
        WeatherObserver traditionalObserver = new WeatherObserver(traditional);
        
        FunctionalWeatherStation functional = new FunctionalWeatherStation();
        AtomicInteger functionalUpdates = new AtomicInteger(0);
        functional.addObserver(data -> functionalUpdates.incrementAndGet());
        
        EventWeatherStation eventStation = new EventWeatherStation();
        EventWeatherStation.WeatherSubscriber subscriber = new EventWeatherStation.WeatherSubscriber();
        eventStation.subscribe(subscriber);
        
        // All should handle updates without throwing
        assertDoesNotThrow(() -> {
            traditional.setPressure(1005);
            functional.setPressure(1005);
            eventStation.setPressure(1005);
        });
        
        assertEquals(1, functionalUpdates.get());
        
        eventStation.close();
    }
}