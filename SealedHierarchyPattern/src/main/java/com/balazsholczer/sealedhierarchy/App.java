package com.balazsholczer.sealedhierarchy;

public class App {
    public sealed interface Shape permits Circle, Rectangle, Triangle {}
    
    public record Circle(double radius) implements Shape {}
    public record Rectangle(double width, double height) implements Shape {}
    public record Triangle(double base, double height) implements Shape {}
    
    public static double calculateArea(Shape shape) {
        return switch (shape) {
            case Circle(var radius) -> Math.PI * radius * radius;
            case Rectangle(var width, var height) -> width * height;
            case Triangle(var base, var height) -> 0.5 * base * height;
        };
    }
    
    public static void main(String[] args) {
        System.out.println("=== Sealed Hierarchy Pattern Demo ===");
        
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 8.0);
        
        System.out.println("Circle area: " + calculateArea(circle));
        System.out.println("Rectangle area: " + calculateArea(rectangle));
        System.out.println("Triangle area: " + calculateArea(triangle));
    }
}