package com.balazsholczer.fastlane;

import java.util.List;
import java.util.stream.Collectors;

public class FastLaneReader {
    
    private final ProductDAO productDAO;
    
    public FastLaneReader(ProductDAO productDAO) {
        this.productDAO = productDAO;
        System.out.println("FastLaneReader: Initialized fast lane reader");
    }
    
    // Fast read operations bypassing entity beans
    public ProductReadOnlyData getProductInfo(String productId) {
        System.out.println("FastLaneReader: Getting product info via fast lane");
        return productDAO.findReadOnlyById(productId);
    }
    
    public List<ProductReadOnlyData> getAllProducts() {
        System.out.println("FastLaneReader: Getting all products via fast lane");
        return productDAO.findAllReadOnly();
    }
    
    public List<ProductReadOnlyData> getProductsByCategory(String category) {
        System.out.println("FastLaneReader: Getting products by category via fast lane");
        return productDAO.findReadOnlyByCategory(category);
    }
    
    // Aggregated read operations (still fast)
    public ProductSummary getProductSummary() {
        System.out.println("FastLaneReader: Generating product summary via fast lane");
        
        List<ProductReadOnlyData> allProducts = productDAO.findAllReadOnly();
        
        int totalProducts = allProducts.size();
        long totalStock = allProducts.stream().mapToInt(ProductReadOnlyData::getStock).sum();
        
        List<String> categories = allProducts.stream()
                .map(ProductReadOnlyData::getCategory)
                .distinct()
                .collect(Collectors.toList());
        
        return new ProductSummary(totalProducts, totalStock, categories);
    }
    
    // Filtered read operations
    public List<ProductReadOnlyData> getProductsInPriceRange(double minPrice, double maxPrice) {
        System.out.println("FastLaneReader: Getting products in price range via fast lane");
        
        return productDAO.findAllReadOnly().stream()
                .filter(p -> p.getPrice().doubleValue() >= minPrice && p.getPrice().doubleValue() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    public static class ProductSummary {
        private final int totalProducts;
        private final long totalStock;
        private final List<String> categories;
        
        public ProductSummary(int totalProducts, long totalStock, List<String> categories) {
            this.totalProducts = totalProducts;
            this.totalStock = totalStock;
            this.categories = categories;
        }
        
        public int getTotalProducts() { return totalProducts; }
        public long getTotalStock() { return totalStock; }
        public List<String> getCategories() { return categories; }
        
        @Override
        public String toString() {
            return "ProductSummary{totalProducts=" + totalProducts + 
                   ", totalStock=" + totalStock + ", categories=" + categories + "}";
        }
    }
}