package com.balazsholczer.streamcollector;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class App {
    public static Collector<String, ?, String> joining(String delimiter, String prefix, String suffix) {
        return Collector.of(
            StringBuilder::new,
            (sb, s) -> sb.append(s).append(delimiter),
            (sb1, sb2) -> sb1.append(sb2),
            sb -> {
                if (sb.length() > 0) sb.setLength(sb.length() - delimiter.length());
                return prefix + sb.toString() + suffix;
            }
        );
    }
    
    public static void main(String[] args) {
        System.out.println("=== Stream Collector Pattern Demo ===");
        
        List<String> words = List.of("Java", "Stream", "Collector", "Pattern");
        
        String result = words.stream()
            .collect(joining(", ", "[", "]"));
            
        System.out.println("Custom collector result: " + result);
        
        // Built-in collector comparison
        String builtin = words.stream()
            .collect(Collectors.joining(", ", "[", "]"));
            
        System.out.println("Built-in collector result: " + builtin);
    }
}