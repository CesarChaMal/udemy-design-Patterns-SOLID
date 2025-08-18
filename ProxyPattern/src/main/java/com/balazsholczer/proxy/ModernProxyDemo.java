package com.balazsholczer.proxy;

public class ModernProxyDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Proxy Pattern ===");
        Image image1 = new ImageProxy("photo1.jpg");
        System.out.println("Proxy created, image not loaded yet");
        image1.display(); // Loads and displays
        image1.display(); // Just displays (already loaded)
        
        System.out.println("\n=== Functional Proxy Pattern ===");
        FunctionalProxy.LazyImage lazyImage = new FunctionalProxy.LazyImage("photo2.jpg");
        System.out.println("Lazy image created");
        lazyImage.display(); // Loads and displays
        lazyImage.display(); // Just displays (cached)
        
        System.out.println("\n=== Dynamic Proxy Pattern ===");
        Image dynamicImage = DynamicProxy.createProxy("photo3.jpg");
        System.out.println("Dynamic proxy created");
        dynamicImage.display(); // Loads and displays
        dynamicImage.display(); // Just displays (already loaded)
        
        System.out.println("\n=== Caching Proxy Pattern ===");
        CachingProxy.CachedImageService service = new CachingProxy.CachedImageService();
        service.display("photo4.jpg"); // Loads and displays
        service.display("photo4.jpg"); // Uses cache
        service.display("photo5.jpg"); // Loads new image
        service.display("photo4.jpg"); // Uses cache
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Caching Proxy - Cache Management:");
        service.clearCache();
        service.display("photo4.jpg"); // Reloads after cache clear
        
        System.out.println("Dynamic Proxy - Runtime Creation:");
        Image[] proxies = {
            DynamicProxy.createProxy("image1.png"),
            DynamicProxy.createProxy("image2.png")
        };
        for (Image proxy : proxies) {
            proxy.display();
        }
    }
}