package com.balazsholczer.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RecordDTO {
    
    public record UserRecord(Long id, String username, String email, String password, 
                           LocalDateTime createdAt, LocalDateTime lastLogin, boolean active) {
        
        // Public DTO - excludes sensitive data
        public UserPublicRecord toPublic() {
            return new UserPublicRecord(id, username, email, active);
        }
        
        // Admin DTO - includes all data except password
        public UserAdminRecord toAdmin() {
            return new UserAdminRecord(id, username, email, createdAt, lastLogin, active);
        }
        
        // Summary DTO - minimal data
        public UserSummaryRecord toSummary() {
            return new UserSummaryRecord(id, username, active);
        }
    }
    
    public record UserPublicRecord(Long id, String username, String email, boolean active) {}
    
    public record UserAdminRecord(Long id, String username, String email, 
                                LocalDateTime createdAt, LocalDateTime lastLogin, boolean active) {}
    
    public record UserSummaryRecord(Long id, String username, boolean active) {}
    
    // Mapper methods
    public static UserRecord fromEntity(User user) {
        return new UserRecord(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getCreatedAt(),
            user.getLastLogin(),
            user.isActive()
        );
    }
    
    public static List<UserPublicRecord> toPublicList(List<User> users) {
        return users.stream()
                   .map(RecordDTO::fromEntity)
                   .map(UserRecord::toPublic)
                   .toList();
    }
}