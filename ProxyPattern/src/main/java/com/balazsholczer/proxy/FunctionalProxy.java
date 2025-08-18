package com.balazsholczer.proxy;

import java.util.function.Supplier;

public class FunctionalProxy {
    
    public record ImageData(String filename) {}
    
    public static class LazyImage {
        private final Supplier<ImageData> imageSupplier;
        private ImageData cachedImage;
        
        public LazyImage(String filename) {
            this.imageSupplier = () -> {
                System.out.println("Loading image: " + filename);
                return new ImageData(filename);
            };
        }
        
        public void display() {
            if (cachedImage == null) {
                cachedImage = imageSupplier.get();
            }
            System.out.println("Displaying image: " + cachedImage.filename());
        }
    }
}