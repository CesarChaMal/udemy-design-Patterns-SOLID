package com.balazsholczer.visitor;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Visitor Pattern
 * Tests Traditional, Functional, Enum, and Stream approaches
 */
class VisitorTest {
    
    @Test
    void testTraditionalVisitor() {
        Chair chair = new Chair("Office Chair", 100.0);
        Table table = new Table("Dining Table", 200.0);
        
        ShoppingCart visitor = new ShoppingCart();
        
        assertEquals(100.0, chair.accept(visitor));
        assertEquals(200.0, table.accept(visitor));
    }
    
    @Test
    void testFunctionalVisitor() {
        List<FunctionalVisitor.Item> items = List.of(
            new FunctionalVisitor.Item("chair", 100.0, "chair"),
            new FunctionalVisitor.Item("table", 200.0, "table")
        );
        
        double price = FunctionalVisitor.visit(items, FunctionalVisitor.PRICE_CALCULATOR);
        assertEquals(300.0, price);
        
        double withTax = FunctionalVisitor.visit(items, FunctionalVisitor.TAX_CALCULATOR);
        assertTrue(withTax > 300.0); // Should be higher due to tax
        
        double withDiscount = FunctionalVisitor.visit(items, FunctionalVisitor.DISCOUNT_CALCULATOR);
        assertTrue(withDiscount < 300.0); // Should be lower due to discount
    }
    
    @Test
    void testEnumVisitor() {
        List<EnumVisitor.Item> items = List.of(
            new EnumVisitor.Item("chair", 100.0, EnumVisitor.ItemType.CHAIR),
            new EnumVisitor.Item("table", 200.0, EnumVisitor.ItemType.TABLE)
        );
        
        double price = EnumVisitor.VisitorType.visit(items, EnumVisitor.VisitorType.PRICE_CALCULATOR);
        assertEquals(300.0, price);
        
        double withTax = EnumVisitor.VisitorType.visit(items, EnumVisitor.VisitorType.TAX_CALCULATOR);
        assertTrue(withTax > 300.0);
        
        double withDiscount = EnumVisitor.VisitorType.visit(items, EnumVisitor.VisitorType.DISCOUNT_CALCULATOR);
        assertTrue(withDiscount < 300.0);
    }
    
    @Test
    void testStreamVisitor() {
        List<StreamVisitor.Item> items = List.of(
            new StreamVisitor.Item("chair", 100.0, "chair"),
            new StreamVisitor.Item("table", 200.0, "table")
        );
        
        double price = StreamVisitor.visit(items, "price");
        assertEquals(300.0, price);
        
        double withTax = StreamVisitor.visit(items, "tax");
        assertTrue(withTax > 300.0);
        
        double withDiscount = StreamVisitor.visit(items, "discount");
        assertTrue(withDiscount < 300.0);
    }
    
    @Test
    void testVisitorBehavior() {
        Chair chair = new Chair("Test Chair", 50.0);
        Table table = new Table("Test Table", 150.0);
        
        ShoppingCart visitor = new ShoppingCart();
        
        assertEquals(50.0, chair.getPrice());
        assertEquals(150.0, table.getPrice());
        assertEquals(50.0, chair.accept(visitor));
        assertEquals(150.0, table.accept(visitor));
    }
    
    @Test
    void testStreamVisitorAdvanced() {
        List<StreamVisitor.Item> items = List.of(
            new StreamVisitor.Item("chair1", 100.0, "chair"),
            new StreamVisitor.Item("table1", 200.0, "table"),
            new StreamVisitor.Item("chair2", 80.0, "chair")
        );
        
        Map<String, Double> allResults = StreamVisitor.visitAll(items);
        assertEquals(380.0, allResults.get("price"));
        assertTrue(allResults.get("tax") > 380.0);
        assertTrue(allResults.get("discount") < 380.0);
        
        double chainResult = StreamVisitor.visitChain(items, "tax", "discount");
        assertTrue(chainResult > 0);
    }
    
    @Test
    void testEquivalence() {
        // All approaches should handle same data correctly
        double expectedPrice = 300.0;
        
        // Traditional approach
        Chair chair = new Chair("chair", 100.0);
        Table table = new Table("table", 200.0);
        ShoppingCart traditional = new ShoppingCart();
        double traditionalTotal = chair.accept(traditional) + table.accept(traditional);
        
        // Functional approach
        List<FunctionalVisitor.Item> functionalItems = List.of(
            new FunctionalVisitor.Item("chair", 100.0, "chair"),
            new FunctionalVisitor.Item("table", 200.0, "table")
        );
        double functionalTotal = FunctionalVisitor.visit(functionalItems, FunctionalVisitor.PRICE_CALCULATOR);
        
        // Enum approach
        List<EnumVisitor.Item> enumItems = List.of(
            new EnumVisitor.Item("chair", 100.0, EnumVisitor.ItemType.CHAIR),
            new EnumVisitor.Item("table", 200.0, EnumVisitor.ItemType.TABLE)
        );
        double enumTotal = EnumVisitor.VisitorType.visit(enumItems, EnumVisitor.VisitorType.PRICE_CALCULATOR);
        
        // Stream approach
        List<StreamVisitor.Item> streamItems = List.of(
            new StreamVisitor.Item("chair", 100.0, "chair"),
            new StreamVisitor.Item("table", 200.0, "table")
        );
        double streamTotal = StreamVisitor.visit(streamItems, "price");
        
        // All should produce same result
        assertEquals(expectedPrice, traditionalTotal);
        assertEquals(expectedPrice, functionalTotal);
        assertEquals(expectedPrice, enumTotal);
        assertEquals(expectedPrice, streamTotal);
    }
    
    @Test
    void testTaxCalculations() {
        // Test tax calculations are consistent
        List<FunctionalVisitor.Item> functionalItems = List.of(
            new FunctionalVisitor.Item("chair", 100.0, "chair"),
            new FunctionalVisitor.Item("table", 200.0, "table")
        );
        
        List<EnumVisitor.Item> enumItems = List.of(
            new EnumVisitor.Item("chair", 100.0, EnumVisitor.ItemType.CHAIR),
            new EnumVisitor.Item("table", 200.0, EnumVisitor.ItemType.TABLE)
        );
        
        List<StreamVisitor.Item> streamItems = List.of(
            new StreamVisitor.Item("chair", 100.0, "chair"),
            new StreamVisitor.Item("table", 200.0, "table")
        );
        
        double functionalTax = FunctionalVisitor.visit(functionalItems, FunctionalVisitor.TAX_CALCULATOR);
        double enumTax = EnumVisitor.VisitorType.visit(enumItems, EnumVisitor.VisitorType.TAX_CALCULATOR);
        double streamTax = StreamVisitor.visit(streamItems, "tax");
        
        // All tax calculations should be equal
        assertEquals(functionalTax, enumTax, 0.01);
        assertEquals(functionalTax, streamTax, 0.01);
    }
    
    @Test
    void testDiscountCalculations() {
        // Test discount calculations
        List<FunctionalVisitor.Item> items = List.of(
            new FunctionalVisitor.Item("expensive", 100.0, "chair"), // > 20, gets discount
            new FunctionalVisitor.Item("cheap", 10.0, "table")       // <= 20, no discount
        );
        
        double discountTotal = FunctionalVisitor.visit(items, FunctionalVisitor.DISCOUNT_CALCULATOR);
        double expectedTotal = (100.0 * 0.9) + 10.0; // 90 + 10 = 100
        assertEquals(expectedTotal, discountTotal, 0.01);
    }
}