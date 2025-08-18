package com.balazsholczer.dto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FunctionalDTO {
    
    public record DataTransferObject<T>(T data, Map<String, Object> metadata) {
        
        public <R> DataTransferObject<R> map(Function<T, R> mapper) {
            return new DataTransferObject<>(mapper.apply(data), metadata);
        }
        
        public DataTransferObject<T> withMetadata(String key, Object value) {
            var newMetadata = new java.util.HashMap<>(metadata);
            newMetadata.put(key, value);
            return new DataTransferObject<>(data, newMetadata);
        }
        
        public static <T> DataTransferObject<T> of(T data) {
            return new DataTransferObject<>(data, Map.of());
        }
    }
    
    // Functional transformers
    public static final Function<User, Map<String, Object>> TO_PUBLIC_MAP = user -> Map.of(
        "id", user.getId(),
        "username", user.getUsername(),
        "email", user.getEmail(),
        "active", user.isActive()
    );
    
    public static final Function<User, Map<String, Object>> TO_ADMIN_MAP = user -> Map.of(
        "id", user.getId(),
        "username", user.getUsername(),
        "email", user.getEmail(),
        "createdAt", user.getCreatedAt(),
        "lastLogin", user.getLastLogin(),
        "active", user.isActive()
    );
    
    public static final Function<User, Map<String, Object>> TO_SUMMARY_MAP = user -> Map.of(
        "id", user.getId(),
        "username", user.getUsername(),
        "active", user.isActive()
    );
    
    public static List<DataTransferObject<Map<String, Object>>> transformUsers(
            List<User> users, Function<User, Map<String, Object>> transformer) {
        return users.stream()
                   .map(user -> DataTransferObject.of(transformer.apply(user)))
                   .toList();
    }
}