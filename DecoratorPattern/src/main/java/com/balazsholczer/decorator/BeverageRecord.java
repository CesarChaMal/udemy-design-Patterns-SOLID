package com.balazsholczer.decorator;

public record BeverageRecord(int cost, String description) {
    
    public static BeverageRecord plain() {
        return new BeverageRecord(5, "");
    }
    
    public BeverageRecord withMilk() {
        return new BeverageRecord(cost + 3, description + "milk ");
    }
    
    public BeverageRecord withSugar() {
        return new BeverageRecord(cost + 1, description + "sugar ");
    }
}