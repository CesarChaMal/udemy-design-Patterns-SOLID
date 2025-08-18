package com.balazsholczer.moduleservice;

import org.junit.jupiter.api.Test;
import java.util.ServiceLoader;
import static org.junit.jupiter.api.Assertions.*;

public class ModuleServiceTest {
    
    @Test
    public void testDefaultMessageService() {
        App.MessageService service = new App.DefaultMessageService();
        assertEquals("Hello from Default Service!", service.getMessage());
    }
    
    @Test
    public void testServiceLoader() {
        ServiceLoader<App.MessageService> loader = ServiceLoader.load(App.MessageService.class);
        assertNotNull(loader);
    }
    
    @Test
    public void testServiceInterface() {
        App.MessageService service = new App.DefaultMessageService();
        assertNotNull(service.getMessage());
        assertTrue(service.getMessage().contains("Default Service"));
    }
}