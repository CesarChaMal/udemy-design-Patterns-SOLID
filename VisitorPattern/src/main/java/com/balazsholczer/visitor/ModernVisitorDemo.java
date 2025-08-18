package com.balazsholczer.visitor;

import java.util.ArrayList;
import java.util.List;

import static com.balazsholczer.visitor.EnumVisitor.ItemType;
import static com.balazsholczer.visitor.EnumVisitor.VisitorType;

public class ModernVisitorDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Visitor Pattern ===");
        List<ShoppingItem> traditionalItems = new ArrayList<>();
        traditionalItems.add(new Table("table", 22));
        traditionalItems.add(new Table("table", 3));
        traditionalItems.add(new Table("table", 2));
        traditionalItems.add(new Chair("chair", 45));
        
        double sum = 0;
        ShoppingCartVisitor shoppingCart = new ShoppingCart();
        for (ShoppingItem item : traditionalItems) {
            sum += item.accept(shoppingCart);
        }
        System.out.println("Total: " + sum);
        
        System.out.println("\n=== Functional Visitor Pattern ===");
        List<FunctionalVisitor.Item> functionalItems = List.of(
            new FunctionalVisitor.Item("table", 22, "table"),
            new FunctionalVisitor.Item("table", 3, "table"),
            new FunctionalVisitor.Item("table", 2, "table"),
            new FunctionalVisitor.Item("chair", 45, "chair")
        );
        
        System.out.println("Price: " + FunctionalVisitor.visit(functionalItems, FunctionalVisitor.PRICE_CALCULATOR));
        System.out.println("With Tax: " + FunctionalVisitor.visit(functionalItems, FunctionalVisitor.TAX_CALCULATOR));
        System.out.println("With Discount: " + FunctionalVisitor.visit(functionalItems, FunctionalVisitor.DISCOUNT_CALCULATOR));
        
        System.out.println("\n=== Enum Visitor Pattern ===");
        List<EnumVisitor.Item> enumItems = List.of(
            new EnumVisitor.Item("table", 22, ItemType.TABLE),
            new EnumVisitor.Item("table", 3, ItemType.TABLE),
            new EnumVisitor.Item("table", 2, ItemType.TABLE),
            new EnumVisitor.Item("chair", 45, ItemType.CHAIR)
        );
        
        System.out.println("Price: " + VisitorType.visit(enumItems, VisitorType.PRICE_CALCULATOR));
        System.out.println("With Tax: " + VisitorType.visit(enumItems, VisitorType.TAX_CALCULATOR));
        System.out.println("With Discount: " + VisitorType.visit(enumItems, VisitorType.DISCOUNT_CALCULATOR));
        
        System.out.println("\n=== Stream Visitor Pattern ===");
        List<StreamVisitor.Item> streamItems = List.of(
            new StreamVisitor.Item("table", 22, "table"),
            new StreamVisitor.Item("table", 3, "table"),
            new StreamVisitor.Item("table", 2, "table"),
            new StreamVisitor.Item("chair", 45, "chair")
        );
        
        System.out.println("Price: " + StreamVisitor.visit(streamItems, "price"));
        System.out.println("With Tax: " + StreamVisitor.visit(streamItems, "tax"));
        System.out.println("With Discount: " + StreamVisitor.visit(streamItems, "discount"));
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Stream - All Visitors:");
        StreamVisitor.visitAll(streamItems).forEach((type, total) -> 
            System.out.println(type + ": " + total));
        
        System.out.println("Functional - Custom Lambda:");
        double customTotal = FunctionalVisitor.visit(functionalItems, 
            item -> item.price() * (item.type().equals("table") ? 2 : 1));
        System.out.println("Custom calculation: " + customTotal);
        
        System.out.println("Enum - All Visitor Types:");
        for (VisitorType visitor : VisitorType.values()) {
            System.out.println(visitor.name() + ": " + VisitorType.visit(enumItems, visitor));
        }
    }
}