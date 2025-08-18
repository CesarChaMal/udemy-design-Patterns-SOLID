package com.balazsholczer.visitor;

import java.util.List;
import java.util.function.Function;

public class FunctionalVisitor {
    
    public record Item(String name, double price, String type) {}
    
    public static final Function<Item, Double> PRICE_CALCULATOR = Item::price;
    
    public static final Function<Item, Double> TAX_CALCULATOR = item -> 
        switch (item.type()) {
            case "table" -> item.price() * 1.1;
            case "chair" -> item.price() * 1.05;
            default -> item.price();
        };
    
    public static final Function<Item, Double> DISCOUNT_CALCULATOR = item ->
        item.price() > 20 ? item.price() * 0.9 : item.price();
    
    public static double visit(List<Item> items, Function<Item, Double> visitor) {
        return items.stream()
                   .mapToDouble(visitor::apply)
                   .sum();
    }
}