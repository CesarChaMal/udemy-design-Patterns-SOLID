package com.balazsholczer.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Comprehensive test class for DTO Pattern - Traditional vs Modern approaches
 */
public class DTOPatternTest {
    
    private User domainUser;
    
    @BeforeEach
    void setUp() {
        domainUser = new User(1L, "john", "john@example.com", "password", 
                            LocalDateTime.now(), LocalDateTime.now(), true);
    }
    
    @Test
    void testTraditionalDTOPattern() {
        // Traditional DTO approach using UserMapper
        UserDTO dto = UserMapper.toDTO(domainUser);
        
        assertEquals(1L, dto.getId());
        assertEquals("john", dto.getUsername());
        assertEquals("john@example.com", dto.getEmail());
        assertTrue(dto.isActive());
        
        // Test serialization properties
        assertNotNull(dto.toString());
        assertTrue(dto.toString().contains("john"));
        
        // Test mapping back
        User mappedBack = UserMapper.toEntity(dto);
        assertEquals(domainUser.getId(), mappedBack.getId());
        assertEquals(domainUser.getUsername(), mappedBack.getUsername());
    }
    
    @Test
    void testRecordDTOPattern() {
        // Modern Record DTO approach
        RecordDTO.UserRecord recordDTO = RecordDTO.fromEntity(domainUser);
        
        assertEquals(1L, recordDTO.id());
        assertEquals("john", recordDTO.username());
        assertEquals("john@example.com", recordDTO.email());
        assertTrue(recordDTO.active());
        
        // Test transformations
        RecordDTO.UserPublicRecord publicRecord = recordDTO.toPublic();
        assertEquals("john", publicRecord.username());
        assertEquals("john@example.com", publicRecord.email());
        
        RecordDTO.UserSummaryRecord summaryRecord = recordDTO.toSummary();
        assertEquals(1L, summaryRecord.id());
        assertEquals("john", summaryRecord.username());
    }
    
    @Test
    void testFunctionalDTOPattern() {
        // Functional DTO approach using transformers
        Map<String, Object> publicData = FunctionalDTO.TO_PUBLIC_MAP.apply(domainUser);
        
        assertEquals(1L, publicData.get("id"));
        assertEquals("john", publicData.get("username"));
        assertEquals("john@example.com", publicData.get("email"));
        assertTrue((Boolean) publicData.get("active"));
        
        // Test DataTransferObject wrapper
        FunctionalDTO.DataTransferObject<User> dto = FunctionalDTO.DataTransferObject.of(domainUser);
        assertEquals(domainUser, dto.data());
        
        // Test with metadata
        FunctionalDTO.DataTransferObject<User> dtoWithMeta = dto.withMetadata("version", "1.0");
        assertEquals("1.0", dtoWithMeta.metadata().get("version"));
    }
    
    @Test
    void testGenericDTOPattern() {
        // Generic DTO approach using transformer
        GenericDTO.DTOTransformer<User> transformer = GenericDTO.createUserTransformer();
        
        GenericDTO.PublicView<?> publicView = transformer.transform(domainUser, GenericDTO.PublicView.class);
        assertNotNull(publicView.data());
        
        GenericDTO.AdminView<?> adminView = transformer.transform(domainUser, GenericDTO.AdminView.class);
        assertNotNull(adminView.data());
        
        GenericDTO.SummaryView<?> summaryView = transformer.transform(domainUser, GenericDTO.SummaryView.class);
        assertNotNull(summaryView.data());
    }
    
    @Test
    void testDTOMapping() {
        // Test mapping between domain and DTO
        UserDTO traditionalDTO = UserMapper.toDTO(domainUser);
        User mappedUser = UserMapper.toEntity(traditionalDTO);
        
        assertEquals(domainUser.getId(), mappedUser.getId());
        assertEquals(domainUser.getUsername(), mappedUser.getUsername());
        assertEquals(domainUser.getEmail(), mappedUser.getEmail());
        assertEquals(domainUser.isActive(), mappedUser.isActive());
        
        // Test record mapping
        RecordDTO.UserRecord recordDTO = RecordDTO.fromEntity(domainUser);
        assertEquals(domainUser.getUsername(), recordDTO.username());
        assertEquals(domainUser.getEmail(), recordDTO.email());
    }
    
    @Test
    void testEquivalenceAcrossApproaches() {
        // Verify all DTO approaches carry same data
        UserDTO traditional = UserMapper.toDTO(domainUser);
        RecordDTO.UserRecord record = RecordDTO.fromEntity(domainUser);
        Map<String, Object> functional = FunctionalDTO.TO_PUBLIC_MAP.apply(domainUser);
        
        // All should contain same data
        assertEquals(traditional.getUsername(), record.username());
        assertEquals(traditional.getEmail(), record.email());
        assertEquals(traditional.getUsername(), functional.get("username"));
        assertEquals(traditional.getEmail(), functional.get("email"));
        assertEquals(traditional.isActive(), record.active());
        assertEquals(traditional.isActive(), functional.get("active"));
    }
    
    @Test
    void testDataTransferCapabilities() {
        // Test DTO's core purpose: data transfer
        UserDTO dto = UserMapper.toDTO(domainUser);
        
        // Should be serializable for transfer
        assertNotNull(dto.toString());
        
        // Should contain only necessary data (no business logic)
        assertDoesNotThrow(() -> {
            dto.getId();
            dto.getUsername();
            dto.getEmail();
            dto.isActive();
        });
        
        // Record DTO should be immutable
        RecordDTO.UserRecord recordDTO = RecordDTO.fromEntity(domainUser);
        assertNotNull(recordDTO.username());
        assertNotNull(recordDTO.email());
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
        
        // Record DTO list transformation
        List<RecordDTO.UserPublicRecord> publicRecords = RecordDTO.toPublicList(users);
        assertEquals(2, publicRecords.size());
        assertEquals("user1", publicRecords.get(0).username());
        
        // Functional DTO list transformation
        List<FunctionalDTO.DataTransferObject<Map<String, Object>>> functionalDtos = 
            FunctionalDTO.transformUsers(users, FunctionalDTO.TO_PUBLIC_MAP);
        assertEquals(2, functionalDtos.size());
        assertEquals("user1", functionalDtos.get(0).data().get("username"));
    }
}