package com.balazsholczer.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Comprehensive test for DTO Pattern
 * Tests Traditional, Record-based, Functional, and Generic approaches
 */
class DTOTest {
    
    @Test
    void testTraditionalUserDTO() {
        User user = new User(1L, "john", "john@example.com", "password", 
                           LocalDateTime.now(), LocalDateTime.now(), true);
        
        UserDTO dto = UserMapper.toDTO(user);
        
        assertEquals(1L, dto.getId());
        assertEquals("john", dto.getUsername());
        assertEquals("john@example.com", dto.getEmail());
        assertTrue(dto.isActive());
        
        User mappedBack = UserMapper.toEntity(dto);
        assertEquals(user.getId(), mappedBack.getId());
        assertEquals(user.getUsername(), mappedBack.getUsername());
        assertEquals(user.getEmail(), mappedBack.getEmail());
        assertEquals(user.isActive(), mappedBack.isActive());
    }
    
    @Test
    void testRecordDTO() {
        User user = new User(1L, "jane", "jane@example.com", "password", 
                           LocalDateTime.now(), LocalDateTime.now(), true);
        
        RecordDTO.UserRecord record = RecordDTO.fromEntity(user);
        
        assertEquals(1L, record.id());
        assertEquals("jane", record.username());
        assertEquals("jane@example.com", record.email());
        assertTrue(record.active());
        
        // Test transformations
        RecordDTO.UserPublicRecord publicRecord = record.toPublic();
        assertEquals("jane", publicRecord.username());
        assertEquals("jane@example.com", publicRecord.email());
        
        RecordDTO.UserSummaryRecord summaryRecord = record.toSummary();
        assertEquals(1L, summaryRecord.id());
        assertEquals("jane", summaryRecord.username());
    }
    
    @Test
    void testFunctionalDTO() {
        User user = new User(1L, "alice", "alice@example.com", "password", 
                           LocalDateTime.now(), LocalDateTime.now(), true);
        
        // Test functional transformations
        Map<String, Object> publicData = FunctionalDTO.TO_PUBLIC_MAP.apply(user);
        assertEquals(1L, publicData.get("id"));
        assertEquals("alice", publicData.get("username"));
        assertEquals("alice@example.com", publicData.get("email"));
        assertTrue((Boolean) publicData.get("active"));
        
        Map<String, Object> adminData = FunctionalDTO.TO_ADMIN_MAP.apply(user);
        assertEquals(1L, adminData.get("id"));
        assertEquals("alice", adminData.get("username"));
        assertNotNull(adminData.get("createdAt"));
        
        Map<String, Object> summaryData = FunctionalDTO.TO_SUMMARY_MAP.apply(user);
        assertEquals(1L, summaryData.get("id"));
        assertEquals("alice", summaryData.get("username"));
        assertTrue((Boolean) summaryData.get("active"));
    }
    
    @Test
    void testGenericDTO() {
        User user = new User(1L, "bob", "bob@example.com", "password", 
                           LocalDateTime.now(), LocalDateTime.now(), true);
        
        GenericDTO.DTOTransformer<User> transformer = GenericDTO.createUserTransformer();
        
        GenericDTO.PublicView<?> publicView = transformer.transform(user, GenericDTO.PublicView.class);
        assertNotNull(publicView.data());
        
        GenericDTO.AdminView<?> adminView = transformer.transform(user, GenericDTO.AdminView.class);
        assertNotNull(adminView.data());
        
        GenericDTO.SummaryView<?> summaryView = transformer.transform(user, GenericDTO.SummaryView.class);
        assertNotNull(summaryView.data());
    }
    
    @Test
    void testDataTransferObject() {
        User user = new User(1L, "test", "test@example.com", "password", 
                           LocalDateTime.now(), LocalDateTime.now(), true);
        
        // Test functional DTO wrapper
        FunctionalDTO.DataTransferObject<User> dto = FunctionalDTO.DataTransferObject.of(user);
        assertEquals(user, dto.data());
        assertTrue(dto.metadata().isEmpty());
        
        // Test with metadata
        FunctionalDTO.DataTransferObject<User> dtoWithMeta = dto.withMetadata("version", "1.0");
        assertEquals("1.0", dtoWithMeta.metadata().get("version"));
        
        // Test mapping
        FunctionalDTO.DataTransferObject<String> mappedDto = dto.map(User::getUsername);
        assertEquals("test", mappedDto.data());
    }
    
    @Test
    void testListTransformation() {
        User user1 = new User(1L, "user1", "user1@example.com", "password", 
                            LocalDateTime.now(), LocalDateTime.now(), true);
        User user2 = new User(2L, "user2", "user2@example.com", "password", 
                            LocalDateTime.now(), LocalDateTime.now(), false);
        
        List<User> users = List.of(user1, user2);
        
        // Traditional DTO list transformation
        List<UserDTO> dtoList = UserMapper.toDTOList(users);
        assertEquals(2, dtoList.size());
        assertEquals("user1", dtoList.get(0).getUsername());
        assertEquals("user2", dtoList.get(1).getUsername());
        
        // Record DTO list transformation
        List<RecordDTO.UserPublicRecord> publicRecords = RecordDTO.toPublicList(users);
        assertEquals(2, publicRecords.size());
        assertEquals("user1", publicRecords.get(0).username());
        assertEquals("user2", publicRecords.get(1).username());
        
        // Functional DTO list transformation
        List<FunctionalDTO.DataTransferObject<Map<String, Object>>> functionalDtos = 
            FunctionalDTO.transformUsers(users, FunctionalDTO.TO_PUBLIC_MAP);
        assertEquals(2, functionalDtos.size());
        assertEquals("user1", functionalDtos.get(0).data().get("username"));
        assertEquals("user2", functionalDtos.get(1).data().get("username"));
        
        // Generic DTO list transformation
        GenericDTO.DTOTransformer<User> transformer = GenericDTO.createUserTransformer();
        List<GenericDTO.PublicView<?>> genericViews = transformer.transformList(users, GenericDTO.PublicView.class);
        assertEquals(2, genericViews.size());
    }
    
    @Test
    void testEquivalence() {
        User user = new User(1L, "equiv", "equiv@example.com", "password", 
                           LocalDateTime.now(), LocalDateTime.now(), true);
        
        // Traditional DTO approach
        UserDTO traditionalDto = UserMapper.toDTO(user);
        assertEquals(user.getUsername(), traditionalDto.getUsername());
        assertEquals(user.getEmail(), traditionalDto.getEmail());
        
        // Record DTO approach
        RecordDTO.UserRecord recordDto = RecordDTO.fromEntity(user);
        assertEquals(user.getUsername(), recordDto.username());
        assertEquals(user.getEmail(), recordDto.email());
        
        // Functional DTO approach
        Map<String, Object> functionalDto = FunctionalDTO.TO_PUBLIC_MAP.apply(user);
        assertEquals(user.getUsername(), functionalDto.get("username"));
        assertEquals(user.getEmail(), functionalDto.get("email"));
        
        // Generic DTO approach
        GenericDTO.DTOTransformer<User> transformer = GenericDTO.createUserTransformer();
        GenericDTO.PublicView<?> genericDto = transformer.transform(user, GenericDTO.PublicView.class);
        assertNotNull(genericDto.data());
        
        // All should contain same core data
        assertEquals(traditionalDto.getUsername(), recordDto.username());
        assertEquals(traditionalDto.getEmail(), recordDto.email());
        assertEquals(traditionalDto.getUsername(), functionalDto.get("username"));
        assertEquals(traditionalDto.getEmail(), functionalDto.get("email"));
    }
}