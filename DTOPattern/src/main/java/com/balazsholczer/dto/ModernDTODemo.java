package com.balazsholczer.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ModernDTODemo {
    
    public static void main(String[] args) {
        
        // Sample data
        List<User> users = List.of(
            new User(1L, "john_doe", "john@example.com", "password123", 
                    LocalDateTime.now().minusDays(30), LocalDateTime.now().minusHours(2), true),
            new User(2L, "jane_smith", "jane@example.com", "secret456", 
                    LocalDateTime.now().minusDays(15), LocalDateTime.now().minusDays(1), true),
            new User(3L, "bob_wilson", "bob@example.com", "pass789", 
                    LocalDateTime.now().minusDays(60), LocalDateTime.now().minusDays(7), false)
        );
        
        System.out.println("=== Traditional DTO Pattern ===");
        List<UserDTO> traditionalDTOs = UserMapper.toDTOList(users);
        traditionalDTOs.forEach(System.out::println);
        
        System.out.println("\n=== Record DTO Pattern ===");
        List<RecordDTO.UserPublicRecord> publicRecords = RecordDTO.toPublicList(users);
        publicRecords.forEach(System.out::println);
        
        System.out.println("\nRecord - Different Views:");
        User sampleUser = users.get(0);
        RecordDTO.UserRecord userRecord = RecordDTO.fromEntity(sampleUser);
        System.out.println("Public: " + userRecord.toPublic());
        System.out.println("Admin: " + userRecord.toAdmin());
        System.out.println("Summary: " + userRecord.toSummary());
        
        System.out.println("\n=== Functional DTO Pattern ===");
        var publicDTOs = FunctionalDTO.transformUsers(users, FunctionalDTO.TO_PUBLIC_MAP);
        publicDTOs.forEach(dto -> System.out.println("Public DTO: " + dto.data()));
        
        var adminDTOs = FunctionalDTO.transformUsers(users, FunctionalDTO.TO_ADMIN_MAP);
        adminDTOs.forEach(dto -> System.out.println("Admin DTO: " + dto.data()));
        
        System.out.println("\n=== Generic DTO Pattern ===");
        var transformer = GenericDTO.createUserTransformer();
        
        List<GenericDTO.PublicView<Object>> publicViews = transformer.transformList(users, GenericDTO.PublicView.class);
        publicViews.forEach(view -> System.out.println("Public View: " + view.data()));
        
        List<GenericDTO.AdminView<Object>> adminViews = transformer.transformList(users, GenericDTO.AdminView.class);
        adminViews.forEach(view -> System.out.println("Admin View: " + view.data()));
        
        List<GenericDTO.SummaryView<Object>> summaryViews = transformer.transformList(users, GenericDTO.SummaryView.class);
        summaryViews.forEach(view -> System.out.println("Summary View: " + view.data()));
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Functional - DTO with metadata:");
        var dtoWithMetadata = FunctionalDTO.DataTransferObject.of(users.get(0))
                                          .map(FunctionalDTO.TO_PUBLIC_MAP)
                                          .withMetadata("timestamp", LocalDateTime.now())
                                          .withMetadata("version", "1.0");
        System.out.println("Data: " + dtoWithMetadata.data());
        System.out.println("Metadata: " + dtoWithMetadata.metadata());
        
        System.out.println("Record - Immutable transformations:");
        var baseRecord = RecordDTO.fromEntity(sampleUser);
        var publicRecord = baseRecord.toPublic();
        System.out.println("Original has password: " + (baseRecord.password() != null));
        System.out.println("Public excludes password: " + publicRecord);
        
        System.out.println("Generic - Type-safe views:");
        try {
            var publicView = transformer.transform(sampleUser, GenericDTO.PublicView.class);
            System.out.println("Successfully created public view: " + publicView.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}