package com.balazsholczer.mvc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.balazsholczer.mvc.model.*;
import com.balazsholczer.mvc.view.*;
import com.balazsholczer.mvc.controller.*;

/**
 * Comprehensive test for MVC Pattern
 * Tests Traditional MVC architecture and separation of concerns
 */
class MVCTest {
    
    @Test
    void testModelViewControllerSeparation() {
        // Model
        Database database = new Database();
        Person person = new Person("John", "Developer");
        database.addPerson(person);
        
        // View
        MainFrame view = new MainFrame();
        assertNotNull(view);
        
        // Controller
        Controller controller = new Controller(view);
        assertNotNull(controller);
        
        // Test separation - each component has distinct responsibility
        assertEquals(1, database.getPeopleDatabase().size());
        assertNotNull(view);
        assertNotNull(controller);
    }
    
    @Test
    void testModelDataManagement() {
        Database database = new Database();
        
        Person person1 = new Person("Alice", "Designer");
        Person person2 = new Person("Bob", "Manager");
        
        database.addPerson(person1);
        database.addPerson(person2);
        
        assertEquals(2, database.getPeopleDatabase().size());
        assertTrue(database.getPeopleDatabase().contains(person1));
        assertTrue(database.getPeopleDatabase().contains(person2));
        
        database.removePerson(person1);
        assertEquals(1, database.getPeopleDatabase().size());
        assertFalse(database.getPeopleDatabase().contains(person1));
        assertTrue(database.getPeopleDatabase().contains(person2));
    }
    
    @Test
    void testViewComponents() {
        MainFrame mainFrame = new MainFrame();
        
        assertNotNull(mainFrame);
        
        // Test form listener setup
        FormListener listener = new FormListener() {
            @Override
            public void okButtonClicked(String name, String occupation) {
                // Test listener implementation
                assertNotNull(name);
                assertNotNull(occupation);
            }
        };
        
        assertDoesNotThrow(() -> mainFrame.okButtonClicked("Test", "Tester"));
    }
    
    @Test
    void testControllerCoordination() {
        MainFrame view = new MainFrame();
        Controller controller = new Controller(view);
        
        // Controller should coordinate between model and view
        controller.addPerson("Test", "Tester");
        
        // Controller should be able to handle adding people
        assertDoesNotThrow(() -> {
            controller.addPerson("New Person", "Analyst");
        });
        
        // Database should now have 2 people
        assertEquals(2, controller.getPeopleDatabase().size());
    }
    
    @Test
    void testMVCDataFlow() {
        MainFrame view = new MainFrame();
        Controller controller = new Controller(view);
        
        // Simulate user input through view -> controller -> model flow
        String name = "MVC Test";
        String occupation = "Architect";
        
        // Controller processes form data and updates model
        controller.addPerson(name, occupation);
        
        // Model should contain the new data
        assertEquals(1, controller.getPeopleDatabase().size());
        Person addedPerson = controller.getPeopleDatabase().get(0);
        assertEquals(name, addedPerson.getName());
        assertEquals(occupation, addedPerson.getOccupation());
    }
    
    @Test
    void testMVCResponsibilities() {
        // Test that each component has distinct responsibilities
        
        // Model - Data management
        Database model = new Database();
        Person testPerson = new Person("Model Test", "Data Manager");
        model.addPerson(testPerson);
        assertEquals(1, model.getPeopleDatabase().size());
        
        // View - User interface
        MainFrame view = new MainFrame();
        assertNotNull(view);
        
        // Controller - Business logic coordination
        Controller controller = new Controller(view);
        assertDoesNotThrow(() -> controller.addPerson("Controller Test", "Coordinator"));
        
        // Each component should handle its own responsibility
        assertEquals(1, model.getPeopleDatabase().size()); // Model manages data
        assertNotNull(view); // View manages UI components
        assertEquals(1, controller.getPeopleDatabase().size()); // Controller coordinates
    }
    
    @Test
    void testMVCIntegration() {
        // Test complete MVC integration
        MainFrame view = new MainFrame();
        Controller controller = new Controller(view);
        
        // Add initial data
        controller.addPerson("Initial", "Starter");
        
        // Simulate multiple user interactions
        controller.addPerson("User1", "Developer");
        controller.addPerson("User2", "Designer");
        controller.addPerson("User3", "Manager");
        
        // Model should contain all data
        assertEquals(4, controller.getPeopleDatabase().size());
        
        // Verify data integrity
        boolean foundUser1 = controller.getPeopleDatabase().stream()
            .anyMatch(p -> "User1".equals(p.getName()));
        boolean foundUser2 = controller.getPeopleDatabase().stream()
            .anyMatch(p -> "User2".equals(p.getName()));
        boolean foundUser3 = controller.getPeopleDatabase().stream()
            .anyMatch(p -> "User3".equals(p.getName()));
        
        assertTrue(foundUser1);
        assertTrue(foundUser2);
        assertTrue(foundUser3);
    }
}