package com.balazsholczer.specification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Comprehensive test class for Specification Pattern - Traditional vs Modern approaches
 */
class SpecificationPatternTest {
    
    private List<Product> products;
    private Product expensiveProduct;
    private Product cheapProduct;
    private Product electronicProduct;
    private Product bookProduct;
    
    @BeforeEach
    void setUp() {
        expensiveProduct = new Product("Laptop", BigDecimal.valueOf(1500.0), "Electronics", true);
        cheapProduct = new Product("Pen", BigDecimal.valueOf(2.0), "Stationery", true);
        electronicProduct = new Product("Phone", BigDecimal.valueOf(800.0), "Electronics", false);
        bookProduct = new Product("Java Book", BigDecimal.valueOf(50.0), "Books", true);
        
        products = Arrays.asList(expensiveProduct, cheapProduct, electronicProduct, bookProduct);
    }
    
    @Test
    void testTraditionalSpecificationPattern() {
        Specification<Product> expensiveSpec = ProductSpecifications.priceGreaterThan(BigDecimal.valueOf(100.0));
        Specification<Product> electronicsSpec = ProductSpecifications.inCategory("Electronics");
        
        assertTrue(expensiveSpec.isSatisfiedBy(expensiveProduct));
        assertFalse(expensiveSpec.isSatisfiedBy(cheapProduct));
        
        assertTrue(electronicsSpec.isSatisfiedBy(expensiveProduct));
        assertFalse(electronicsSpec.isSatisfiedBy(bookProduct));
        
        Specification<Product> expensiveElectronics = expensiveSpec.and(electronicsSpec);
        assertTrue(expensiveElectronics.isSatisfiedBy(expensiveProduct));
        assertTrue(expensiveElectronics.isSatisfiedBy(electronicProduct));
        assertFalse(expensiveElectronics.isSatisfiedBy(cheapProduct));
    }
    
    @Test
    void testFunctionalSpecificationPattern() {
        Predicate<Product> expensiveSpec = product -> product.getPrice().compareTo(BigDecimal.valueOf(100.0)) > 0;
        Predicate<Product> electronicsSpec = product -> "Electronics".equals(product.getCategory());
        
        assertTrue(expensiveSpec.test(expensiveProduct));
        assertFalse(expensiveSpec.test(cheapProduct));
        
        assertTrue(electronicsSpec.test(expensiveProduct));
        assertFalse(electronicsSpec.test(bookProduct));
        
        Predicate<Product> expensiveElectronics = expensiveSpec.and(electronicsSpec);
        assertTrue(expensiveElectronics.test(expensiveProduct));
        assertTrue(expensiveElectronics.test(electronicProduct));
        assertFalse(expensiveElectronics.test(cheapProduct));
    }
    
    @Test
    void testStreamBasedFiltering() {
        Predicate<Product> expensiveSpec = product -> product.getPrice().compareTo(BigDecimal.valueOf(100.0)) > 0;
        
        List<Product> expensiveProducts = products.stream()
            .filter(expensiveSpec)
            .collect(Collectors.toList());
        
        assertEquals(2, expensiveProducts.size());
        assertTrue(expensiveProducts.contains(expensiveProduct));
        assertTrue(expensiveProducts.contains(electronicProduct));
        assertFalse(expensiveProducts.contains(cheapProduct));
    }
    
    @Test
    void testCompositeSpecifications() {
        Specification<Product> expensiveSpec = ProductSpecifications.priceGreaterThan(BigDecimal.valueOf(100.0));
        Specification<Product> electronicsSpec = ProductSpecifications.inCategory("Electronics");
        
        Specification<Product> expensiveElectronics = expensiveSpec.and(electronicsSpec);
        assertTrue(expensiveElectronics.isSatisfiedBy(expensiveProduct));
        assertFalse(expensiveElectronics.isSatisfiedBy(bookProduct));
        
        Specification<Product> expensiveOrElectronics = expensiveSpec.or(electronicsSpec);
        assertTrue(expensiveOrElectronics.isSatisfiedBy(expensiveProduct));
        assertTrue(expensiveOrElectronics.isSatisfiedBy(electronicProduct));
        assertFalse(expensiveOrElectronics.isSatisfiedBy(bookProduct));
        assertFalse(expensiveOrElectronics.isSatisfiedBy(cheapProduct));
        
        Specification<Product> notExpensive = expensiveSpec.not();
        assertFalse(notExpensive.isSatisfiedBy(expensiveProduct));
        assertTrue(notExpensive.isSatisfiedBy(cheapProduct));
    }
    
    @Test
    void testComplexBusinessRules() {
        Specification<Product> expensiveElectronics = ProductSpecifications.expensiveElectronics();
        Specification<Product> affordableInStock = ProductSpecifications.affordableAndInStock(BigDecimal.valueOf(100.0));
        
        assertTrue(expensiveElectronics.isSatisfiedBy(expensiveProduct));
        assertTrue(expensiveElectronics.isSatisfiedBy(electronicProduct));
        assertFalse(expensiveElectronics.isSatisfiedBy(bookProduct));
        
        assertTrue(affordableInStock.isSatisfiedBy(bookProduct));
        assertTrue(affordableInStock.isSatisfiedBy(cheapProduct));
        assertFalse(affordableInStock.isSatisfiedBy(expensiveProduct));
        assertFalse(affordableInStock.isSatisfiedBy(electronicProduct));
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        Specification<Product> traditionalSpec = ProductSpecifications.priceGreaterThan(BigDecimal.valueOf(100.0))
            .and(ProductSpecifications.inCategory("Electronics"));
        
        Predicate<Product> functionalSpec = product -> product.getPrice().compareTo(BigDecimal.valueOf(100.0)) > 0 
            && "Electronics".equals(product.getCategory());
        
        for (Product product : products) {
            boolean traditionalResult = traditionalSpec.isSatisfiedBy(product);
            boolean functionalResult = functionalSpec.test(product);
            assertEquals(traditionalResult, functionalResult);
        }
    }
}