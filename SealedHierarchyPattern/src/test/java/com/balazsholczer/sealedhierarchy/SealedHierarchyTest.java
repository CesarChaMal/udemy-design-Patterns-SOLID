package com.balazsholczer.sealedhierarchy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SealedHierarchyTest {
    
    @Test
    public void testCircleArea() {
        App.Shape circle = new App.Circle(5.0);
        double area = App.calculateArea(circle);
        assertEquals(Math.PI * 25, area, 0.001);
    }
    
    @Test
    public void testRectangleArea() {
        App.Shape rectangle = new App.Rectangle(4.0, 6.0);
        double area = App.calculateArea(rectangle);
        assertEquals(24.0, area, 0.001);
    }
    
    @Test
    public void testTriangleArea() {
        App.Shape triangle = new App.Triangle(3.0, 8.0);
        double area = App.calculateArea(triangle);
        assertEquals(12.0, area, 0.001);
    }
}