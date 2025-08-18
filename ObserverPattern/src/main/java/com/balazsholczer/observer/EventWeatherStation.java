package com.balazsholczer.observer;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class EventWeatherStation extends SubmissionPublisher<EventWeatherStation.WeatherEvent> {
    
    public record WeatherEvent(String type, int value, int pressure, int temperature, int humidity) {}
    
    private int pressure, temperature, humidity;
    
    public void setPressure(int pressure) {
        this.pressure = pressure;
        submit(new WeatherEvent("PRESSURE", pressure, this.pressure, temperature, humidity));
    }
    
    public void setTemperature(int temperature) {
        this.temperature = temperature;
        submit(new WeatherEvent("TEMPERATURE", temperature, pressure, this.temperature, humidity));
    }
    
    public void setHumidity(int humidity) {
        this.humidity = humidity;
        submit(new WeatherEvent("HUMIDITY", humidity, pressure, temperature, this.humidity));
    }
    
    public static class WeatherSubscriber implements Flow.Subscriber<WeatherEvent> {
        private Flow.Subscription subscription;
        
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            subscription.request(1);
        }
        
        @Override
        public void onNext(WeatherEvent event) {
            System.out.println(event.type() + " changed: " + event.pressure() + "-" + 
                             event.temperature() + "-" + event.humidity());
            subscription.request(1);
        }
        
        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }
        
        @Override
        public void onComplete() {
            System.out.println("Weather updates completed");
        }
    }
}