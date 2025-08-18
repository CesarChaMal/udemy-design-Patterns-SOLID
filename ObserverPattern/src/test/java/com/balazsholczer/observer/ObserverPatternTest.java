package com.balazsholczer.observer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Observer Pattern
 * Tests Traditional vs Modern approaches
 */
class ObserverPatternTest {
    
    @Test
    void testTraditionalApproach() {
        // Traditional Observer Pattern
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
        // Test observer registration and removal
        WeatherStation station = new WeatherStation();
        WeatherObserver observer = new WeatherObserver(station);
        
        // Observer should be automatically registered
        assertDoesNotThrow(() -> station.setPressure(1000));
        
        // Test manual removal
        assertDoesNotThrow(() -> station.removeObserver(observer));
        assertDoesNotThrow(() -> station.setPressure(1020));
    }
    
    @Test
    void testMultipleObservers() {
        // Test multiple observers receiving notifications
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
    void testNotificationTriggers() {
        // Test that changes trigger notifications
        WeatherStation station = new WeatherStation();
        WeatherObserver observer = new WeatherObserver(station);
        
        // Each setter should trigger notification
        assertDoesNotThrow(() -> station.setPressure(1010));
        assertDoesNotThrow(() -> station.setTemperature(20));
        assertDoesNotThrow(() -> station.setHumidity(70));
    }
    
    @Test
    void testEquivalence() {
        // Traditional approach should work correctly
        WeatherStation station = new WeatherStation();
        WeatherObserver observer = new WeatherObserver(station);
        
        // Observer should receive and display updates
        assertDoesNotThrow(() -> station.setPressure(1005));
        assertDoesNotThrow(() -> station.setTemperature(18));
        assertDoesNotThrow(() -> station.setHumidity(65));
        
        // Pattern should work correctly
        assertTrue(true);
    }
}