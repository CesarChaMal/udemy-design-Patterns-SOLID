package com.balazsholczer.visitor;

import java.util.List;
import java.util.function.Function;

public class EnumVisitor {
    
    public record Item(String name, double price, ItemType type) {}
    
    public enum ItemType {
        TABLE, CHAIR
    }
    
    public enum VisitorType implements Function<Item, Double> {
        PRICE_CALCULATOR(Item::price),
        
        TAX_CALCULATOR(item -> switch (item.type()) {
            case TABLE -> item.price() * 1.1;
            case CHAIR -> item.price() * 1.05;
        }),
        
        DISCOUNT_CALCULATOR(item -> 
            item.price() > 20 ? item.price() * 0.9 : item.price());
        
        private final Function<Item, Double> calculator;
        
        VisitorType(Function<Item, Double> calculator) {
            this.calculator = calculator;
        }
        
        @Override
        public Double apply(Item item) {
            return calculator.apply(item);
        }
        
        public static double visit(List<Item> items, VisitorType visitor) {
            return items.stream()
                       .mapToDouble(visitor::apply)
                       .sum();
        }
    }
}