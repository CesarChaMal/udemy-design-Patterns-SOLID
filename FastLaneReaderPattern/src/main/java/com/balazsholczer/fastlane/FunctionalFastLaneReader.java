package com.balazsholczer.fastlane;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalFastLaneReader {
    
    private final ProductDAO productDAO;
    
    public FunctionalFastLaneReader(ProductDAO productDAO) {
        this.productDAO = productDAO;
        System.out.println("FunctionalFastLaneReader: Initialized functional fast lane reader");
    }
    
    // Functional read with transformation
    public <R> List<R> readAndTransform(Function<ProductReadOnlyData, R> transformer) {
        System.out.println("FunctionalFastLaneReader: Reading and transforming data");
        return productDAO.findAllReadOnly().stream()
                .map(transformer)
                .collect(Collectors.toList());
    }
    
    // Functional read with filtering
    public List<ProductReadOnlyData> readWithFilter(Predicate<ProductReadOnlyData> filter) {
        System.out.println("FunctionalFastLaneReader: Reading with functional filter");
        return productDAO.findAllReadOnly().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
    
    // Functional aggregation
    public <R> R aggregateData(Function<List<ProductReadOnlyData>, R> aggregator) {
        System.out.println("FunctionalFastLaneReader: Aggregating data functionally");
        List<ProductReadOnlyData> data = productDAO.findAllReadOnly();
        return aggregator.apply(data);
    }
    
    // Predefined functional operations
    public static Function<ProductReadOnlyData, String> nameExtractor() {
        return ProductReadOnlyData::getName;
    }
    
    public static Predicate<ProductReadOnlyData> inStockFilter() {
        return product -> product.getStock() > 0;
    }
    
    public static Predicate<ProductReadOnlyData> categoryFilter(String category) {
        return product -> category.equals(product.getCategory());
    }
    
    public static Function<List<ProductReadOnlyData>, Double> averagePriceCalculator() {
        return products -> products.stream()
                .mapToDouble(p -> p.getPrice().doubleValue())
                .average()
                .orElse(0.0);
    }
    
    public static Function<List<ProductReadOnlyData>, Long> totalStockCalculator() {
        return products -> products.stream()
                .mapToLong(ProductReadOnlyData::getStock)
                .sum();
    }
}