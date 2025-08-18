package com.balazsholczer.command;

public class ModernCommandDemo {
    
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("=== Traditional Command Pattern ===");
        traditionalApproach();
        
        Thread.sleep(2000);
        
        System.out.println("\n=== Lambda Command Pattern ===");
        lambdaApproach();
        
        Thread.sleep(2000);
        
        System.out.println("\n=== Stream Command Pattern ===");
        StreamAlgorithm.executeAsync();
    }
    
    private static void traditionalApproach() {
        Algorithm algorithm = new Algorithm();
        
        Thread producer = new Thread(algorithm::produce);
        Thread consumer = new Thread(algorithm::consume);
        
        producer.start();
        consumer.start();
    }
    
    private static void lambdaApproach() {
        LambdaAlgorithm algorithm = new LambdaAlgorithm();
        
        Thread producer = new Thread(algorithm::produce);
        Thread consumer = new Thread(algorithm::consume);
        
        producer.start();
        consumer.start();
    }
}