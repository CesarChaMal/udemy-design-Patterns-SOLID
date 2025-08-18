package com.balazsholczer.servicelocator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ServiceLocatorTest {

    @BeforeEach
    void setUp() {
        // Reset cache by creating new ServiceLocator instance
        // Since cache is static, we need to ensure clean state
    }

    @Test
    void testDatabaseServiceLookup() {
        Service service = ServiceLocator.getService(DatabaseService.NAME);
        
        assertNotNull(service);
        assertTrue(service instanceof DatabaseService);
        assertEquals(DatabaseService.NAME, service.getName());
        
        // Test service execution doesn't throw
        assertDoesNotThrow(() -> service.execute());
    }

    @Test
    void testMessagingServiceLookup() {
        Service service = ServiceLocator.getService(MessagingService.NAME);
        
        assertNotNull(service);
        assertTrue(service instanceof MessagingService);
        assertEquals(MessagingService.NAME, service.getName());
        
        // Test service execution doesn't throw
        assertDoesNotThrow(() -> service.execute());
    }

    @Test
    void testServiceCaching() {
        // First call should create and cache the service
        Service service1 = ServiceLocator.getService(DatabaseService.NAME);
        
        // Second call should return the same cached instance
        Service service2 = ServiceLocator.getService(DatabaseService.NAME);
        
        assertNotNull(service1);
        assertNotNull(service2);
        assertSame(service1, service2, "Services should be cached and return same instance");
    }

    @Test
    void testMultipleServiceTypes() {
        Service dbService = ServiceLocator.getService(DatabaseService.NAME);
        Service msgService = ServiceLocator.getService(MessagingService.NAME);
        
        assertNotNull(dbService);
        assertNotNull(msgService);
        assertNotSame(dbService, msgService);
        
        assertEquals(DatabaseService.NAME, dbService.getName());
        assertEquals(MessagingService.NAME, msgService.getName());
    }

    @Test
    void testUnknownServiceReturnsNull() {
        Service service = ServiceLocator.getService("unknownService");
        assertNull(service);
    }

    @Test
    void testInitialContextDirectly() {
        InitialContext context = new InitialContext();
        
        Object dbService = context.lookup(DatabaseService.NAME);
        Object msgService = context.lookup(MessagingService.NAME);
        Object unknownService = context.lookup("unknown");
        
        assertTrue(dbService instanceof DatabaseService);
        assertTrue(msgService instanceof MessagingService);
        assertNull(unknownService);
    }

    @Test
    void testCacheDirectly() {
        Cache cache = new Cache();
        DatabaseService dbService = new DatabaseService();
        
        // Initially empty
        assertNull(cache.getService(DatabaseService.NAME));
        
        // Add service
        cache.addService(dbService);
        
        // Should find it now
        Service found = cache.getService(DatabaseService.NAME);
        assertNotNull(found);
        assertSame(dbService, found);
    }

    @Test
    void testServiceImplementations() {
        DatabaseService dbService = new DatabaseService();
        MessagingService msgService = new MessagingService();
        
        assertEquals(DatabaseService.NAME, dbService.getName());
        assertEquals(MessagingService.NAME, msgService.getName());
        
        assertDoesNotThrow(() -> dbService.execute());
        assertDoesNotThrow(() -> msgService.execute());
    }
}