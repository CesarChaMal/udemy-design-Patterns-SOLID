package com.balazsholczer.bridge;

public class OpenGLDrawingAPI implements DrawingAPI {
    
    @Override
    public void drawCircle(double x, double y, double radius) {
        System.out.println("OpenGL: Drawing circle at (" + x + ", " + y + ") with radius " + radius);
    }
    
    @Override
    public void drawRectangle(double x, double y, double width, double height) {
        System.out.println("OpenGL: Drawing rectangle at (" + x + ", " + y + ") with size " + width + "x" + height);
    }
}