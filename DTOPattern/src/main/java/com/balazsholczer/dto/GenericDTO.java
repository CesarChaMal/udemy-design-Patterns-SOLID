package com.balazsholczer.dto;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericDTO {
    
    public sealed interface DTOView<T> permits PublicView, AdminView, SummaryView {}
    
    public record PublicView<T>(T data) implements DTOView<T> {}
    public record AdminView<T>(T data) implements DTOView<T> {}
    public record SummaryView<T>(T data) implements DTOView<T> {}
    
    public static class DTOTransformer<T> {
        private final Map<String, Function<T, ?>> transformers;
        
        public DTOTransformer(Map<String, Function<T, ?>> transformers) {
            this.transformers = transformers;
        }
        
        @SuppressWarnings("unchecked")
        public <V extends DTOView<R>, R> V transform(T entity, Class<V> viewType) {
            String viewName = viewType.getSimpleName();
            Function<T, ?> transformer = transformers.get(viewName);
            if (transformer == null) {
                throw new IllegalArgumentException("No transformer for view type: " + viewType);
            }
            
            Object result = transformer.apply(entity);
            
            if (viewType.equals(PublicView.class)) {
                return (V) new PublicView<>(result);
            } else if (viewType.equals(AdminView.class)) {
                return (V) new AdminView<>(result);
            } else if (viewType.equals(SummaryView.class)) {
                return (V) new SummaryView<>(result);
            }
            
            throw new IllegalArgumentException("Unknown view type: " + viewType);
        }
        
        public <V extends DTOView<R>, R> List<V> transformList(List<T> entities, Class<V> viewType) {
            return entities.stream()
                          .map(entity -> transform(entity, viewType))
                          .collect(Collectors.toList());
        }
    }
    
    public static DTOTransformer<User> createUserTransformer() {
        Map<String, Function<User, ?>> transformerMap = new java.util.HashMap<>();
        
        transformerMap.put("PublicView", (User user) -> Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "email", user.getEmail(),
            "active", user.isActive()
        ));
        
        transformerMap.put("AdminView", (User user) -> Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "email", user.getEmail(),
            "createdAt", user.getCreatedAt(),
            "lastLogin", user.getLastLogin(),
            "active", user.isActive()
        ));
        
        transformerMap.put("SummaryView", (User user) -> Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "active", user.isActive()
        ));
        
        return new DTOTransformer<>(transformerMap);
    }
}