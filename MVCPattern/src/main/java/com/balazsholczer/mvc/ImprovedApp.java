package com.balazsholczer.mvc;

import com.balazsholczer.mvc.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * MVC Pattern: separates application into three interconnected components
 * 
 * Key Concepts:
 * - Separation of Concerns: UI, business logic, and data are separated
 * - Loose Coupling: Components interact through well-defined interfaces
 * - Reusability: Models can have multiple views, views can work with different models
 * - Testability: Each component can be tested independently
 * 
 * Structure:
 * - Model: manages data and business logic, notifies observers of changes
 * - View: presents data to user, handles user interface rendering
 * - Controller: handles user input, updates model, coordinates view updates
 * 
 * Benefits:
 * - Maintainability: Changes in one component don't affect others
 * - Scalability: Easy to add new views or modify existing ones
 * - Parallel Development: Teams can work on different components simultaneously
 * - Code Reuse: Models and controllers can be shared across different views
 * 
 * Use Cases:
 * - Desktop applications (Swing, JavaFX)
 * - Web applications (Spring MVC, Struts)
 * - Mobile applications (Android Activities/Fragments)
 * - Game development (game state, UI, input handling)
 */

public class ImprovedApp {
    
    private static final Logger LOGGER = Logger.getLogger(ImprovedApp.class.getName());
    
    public static void main(String[] args) {
        // Configure system look and feel for better user experience
        configureLookAndFeel();
        
        // Launch application on Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                LOGGER.info("Starting MVC Pattern Demo Application");
                new MainFrame();
                LOGGER.info("Application started successfully");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to start application", e);
                System.exit(1);
            }
        });
    }
    
    private static void configureLookAndFeel() {
        try {
            // Use system look and feel for native appearance
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LOGGER.info("System Look and Feel configured successfully");
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.WARNING, "Failed to set system look and feel, using default", e);
            // Application continues with default look and feel
        }
    }
}