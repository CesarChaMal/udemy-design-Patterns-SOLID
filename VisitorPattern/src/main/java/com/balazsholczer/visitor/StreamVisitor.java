package com.balazsholczer.visitor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamVisitor {
    
    public record Item(String name, double price, String type) {}
    
    private static final Map<String, Function<Item, Double>> VISITORS = Map.of(
        "price", Item::price,
        "tax", item -> switch (item.type()) {
            case "table" -> item.price() * 1.1;
            case "chair" -> item.price() * 1.05;
            default -> item.price();
        },
        "discount", item -> item.price() > 20 ? item.price() * 0.9 : item.price()
    );
    
    public static double visit(List<Item> items, String visitorType) {
        return items.stream()
                   .mapToDouble(item -> VISITORS.getOrDefault(visitorType, Item::price).apply(item))
                   .sum();
    }
    
    public static Map<String, Double> visitAll(List<Item> items) {
        return VISITORS.entrySet().stream()
                      .collect(Collectors.toMap(
                          Map.Entry::getKey,
                          entry -> items.stream()
                                       .mapToDouble(item -> entry.getValue().apply(item))
                                       .sum()
                      ));
    }
    
    public static double visitChain(List<Item> items, String... visitorTypes) {
        return items.stream()
                   .mapToDouble(item -> {
                       double result = item.price();
                       for (String type : visitorTypes) {
                           Function<Item, Double> visitor = VISITORS.get(type);
                           if (visitor != null) {
                               result = visitor.apply(new Item(item.name(), result, item.type()));
                           }
                       }
                       return result;
                   })
                   .sum();
    }
}