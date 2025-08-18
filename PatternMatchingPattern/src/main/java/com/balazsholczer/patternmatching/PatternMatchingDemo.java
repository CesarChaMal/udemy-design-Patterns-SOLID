package com.balazsholczer.patternmatching;

import java.util.*;

/**
 * Modern Java Pattern Matching Demonstration
 * Features: instanceof patterns, switch expressions, sealed classes, record patterns
 */
public class PatternMatchingDemo {
    
    // Sealed class hierarchy for pattern matching
    public sealed interface Shape permits Circle, Rectangle, Triangle {
        double area();
    }
    
    public record Circle(double radius) implements Shape {
        @Override
        public double area() {
            return Math.PI * radius * radius;
        }
    }
    
    public record Rectangle(double width, double height) implements Shape {
        @Override
        public double area() {
            return width * height;
        }
    }
    
    public record Triangle(double base, double height) implements Shape {
        @Override
        public double area() {
            return 0.5 * base * height;
        }
    }
    
    // Traditional instanceof pattern matching
    public static String describeShapeTraditional(Shape shape) {
        if (shape instanceof Circle circle) {
            return "Circle with radius " + circle.radius();
        } else if (shape instanceof Rectangle rect) {
            return "Rectangle " + rect.width() + "x" + rect.height();
        } else if (shape instanceof Triangle tri) {
            return "Triangle with base " + tri.base() + " and height " + tri.height();
        }
        return "Unknown shape";
    }
    
    // Modern switch expression pattern matching
    public static String describeShapeModern(Shape shape) {
        return switch (shape) {
            case Circle(var radius) -> "Circle with radius " + radius;
            case Rectangle(var width, var height) -> "Rectangle " + width + "x" + height;
            case Triangle(var base, var height) -> "Triangle with base " + base + " and height " + height;
        };
    }
    
    // Pattern matching with guards
    public static String categorizeShape(Shape shape) {
        return switch (shape) {
            case Circle(var radius) when radius > 10 -> "Large circle";
            case Circle(var radius) when radius > 5 -> "Medium circle";
            case Circle(var radius) -> "Small circle";
            case Rectangle(var w, var h) when w == h -> "Square " + w + "x" + h;
            case Rectangle(var w, var h) when w > h -> "Wide rectangle";
            case Rectangle(var w, var h) -> "Tall rectangle";
            case Triangle(var base, var height) when base == height -> "Isosceles triangle";
            case Triangle(var base, var height) -> "Triangle";
        };
    }
    
    // Collection pattern matching
    public static String analyzeList(List<?> list) {
        return switch (list) {
            case List<?> l when l.isEmpty() -> "Empty list";
            case List<?> l when l.size() == 1 -> "Single item: " + l.get(0);
            case List<?> l when l.size() == 2 -> "Pair: " + l.get(0) + ", " + l.get(1);
            case List<?> l -> "List with " + l.size() + " items";
        };
    }
    
    // Nested pattern matching
    public record Point(double x, double y) {}
    public record Line(Point start, Point end) {}
    
    public static String analyzeGeometry(Object obj) {
        return switch (obj) {
            case Point(var x, var y) when x == 0 && y == 0 -> "Origin point";
            case Point(var x, var y) when x == y -> "Diagonal point (" + x + "," + y + ")";
            case Point(var x, var y) -> "Point at (" + x + "," + y + ")";
            case Line(Point(var x1, var y1), Point(var x2, var y2)) 
                when x1 == x2 -> "Vertical line from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")";
            case Line(Point(var x1, var y1), Point(var x2, var y2)) 
                when y1 == y2 -> "Horizontal line from (" + x1 + "," + y1 + ") to (" + x2 + "," + y2 + ")";
            case Line(var start, var end) -> "Line from " + start + " to " + end;
            default -> "Unknown geometry";
        };
    }
    
    // Pattern matching with null handling
    public static String safeDescribe(Object obj) {
        return switch (obj) {
            case null -> "Null object";
            case String s when s.isEmpty() -> "Empty string";
            case String s -> "String: " + s;
            case Integer i when i > 0 -> "Positive integer: " + i;
            case Integer i when i < 0 -> "Negative integer: " + i;
            case Integer i -> "Zero";
            case List<?> l -> "List with " + l.size() + " elements";
            default -> "Unknown type: " + obj.getClass().getSimpleName();
        };
    }
    
    // Functional pattern matching
    public static <T, R> R match(T value, PatternMatcher<T, R> matcher) {
        return matcher.match(value);
    }
    
    @FunctionalInterface
    public interface PatternMatcher<T, R> {
        R match(T value);
    }
    
    // Builder pattern for complex pattern matching
    public static class PatternBuilder<T> {
        private final T value;
        
        public PatternBuilder(T value) {
            this.value = value;
        }
        
        public <R> CaseBuilder<T, R> when(Class<R> type) {
            return new CaseBuilder<>(value, type);
        }
        
        public static class CaseBuilder<T, R> {
            private final T value;
            private final Class<R> type;
            
            public CaseBuilder(T value, Class<R> type) {
                this.value = value;
                this.type = type;
            }
            
            @SuppressWarnings("unchecked")
            public Optional<R> then() {
                if (type.isInstance(value)) {
                    return Optional.of((R) value);
                }
                return Optional.empty();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Modern Java Pattern Matching Demo ===\n");
        
        // Test shapes
        List<Shape> shapes = List.of(
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 4.0),
            new Circle(12.0),
            new Rectangle(5.0, 5.0)
        );
        
        System.out.println("--- Traditional instanceof Pattern Matching ---");
        shapes.forEach(shape -> 
            System.out.println(describeShapeTraditional(shape)));
        
        System.out.println("\n--- Modern Switch Expression Pattern Matching ---");
        shapes.forEach(shape -> 
            System.out.println(describeShapeModern(shape)));
        
        System.out.println("\n--- Pattern Matching with Guards ---");
        shapes.forEach(shape -> 
            System.out.println(categorizeShape(shape)));
        
        System.out.println("\n--- Collection Pattern Matching ---");
        List<List<?>> lists = List.of(
            List.of(),
            List.of("single"),
            List.of("first", "second"),
            List.of(1, 2, 3, 4, 5)
        );
        lists.forEach(list -> 
            System.out.println(analyzeList(list)));
        
        System.out.println("\n--- Nested Pattern Matching ---");
        List<Object> geometries = List.of(
            new Point(0, 0),
            new Point(5, 5),
            new Point(3, 7),
            new Line(new Point(0, 0), new Point(5, 0)),
            new Line(new Point(2, 0), new Point(2, 5)),
            new Line(new Point(1, 1), new Point(4, 3))
        );
        geometries.forEach(geo -> 
            System.out.println(analyzeGeometry(geo)));
        
        System.out.println("\n--- Safe Pattern Matching with Null ---");
        List<Object> objects = Arrays.asList(
            null,
            "",
            "Hello",
            42,
            -10,
            0,
            List.of(1, 2, 3),
            new Date()
        );
        objects.forEach(obj -> 
            System.out.println(safeDescribe(obj)));
        
        System.out.println("\n🏆 Pattern Matching Features Demonstrated:");
        System.out.println("✅ instanceof pattern matching");
        System.out.println("✅ Switch expressions with patterns");
        System.out.println("✅ Sealed classes for exhaustive matching");
        System.out.println("✅ Record patterns and deconstruction");
        System.out.println("✅ Pattern guards with when clauses");
        System.out.println("✅ Nested pattern matching");
        System.out.println("✅ Collection pattern matching");
        System.out.println("✅ Null-safe pattern matching");
    }
}