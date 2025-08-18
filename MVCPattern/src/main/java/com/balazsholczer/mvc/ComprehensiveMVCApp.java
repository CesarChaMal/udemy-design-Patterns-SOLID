package com.balazsholczer.mvc;

import com.balazsholczer.mvc.controller.Controller;
import com.balazsholczer.mvc.model.Database;
import com.balazsholczer.mvc.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Comprehensive MVC Application Client
 * 
 * This client properly initializes and coordinates all three MVC components:
 * - Model: Database (data storage and business logic)
 * - View: MainFrame with FormPanel and TextPanel (user interface)
 * - Controller: Controller (mediates between Model and View)
 * 
 * Responsibilities:
 * - Initialize all MVC components in correct order
 * - Wire dependencies between components
 * - Configure application environment
 * - Handle startup errors gracefully
 */

public class ComprehensiveMVCApp {
    
    private static final Logger LOGGER = Logger.getLogger(ComprehensiveMVCApp.class.getName());
    
    public static void main(String[] args) {
        
        // Configure application environment
        configureLookAndFeel();
        
        // Initialize MVC components on EDT
        SwingUtilities.invokeLater(() -> {
            try {
                initializeMVCApplication();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to initialize MVC application", e);
                System.exit(1);
            }
        });
    }
    
    private static void initializeMVCApplication() {
        LOGGER.info("Initializing MVC Pattern Demo Application");
        
        // 1. Initialize View (MainFrame)
        MainFrame mainFrame = new MainFrame();
        LOGGER.info("View (MainFrame) initialized");
        
        // 2. Initialize Controller with View (Controller creates its own Database)
        Controller controller = new Controller(mainFrame);
        LOGGER.info("Controller initialized with View (Database created internally)");
        
        // 3. Wire Controller to View components
        wireControllerToView(mainFrame, controller);
        
        // 4. Make application visible
        mainFrame.setVisible(true);
        
        LOGGER.info("MVC Application started successfully");
        LOGGER.info("Components: Database (Model) ↔ Controller ↔ MainFrame (View)");
    }
    
    private static void wireControllerToView(MainFrame mainFrame, Controller controller) {
        // The Controller is already wired to MainFrame through constructor
        // Additional wiring can be done here if needed by the specific implementation
        
        LOGGER.info("Controller-View wiring completed (Controller has MainFrame reference)");
        LOGGER.info("Ready to handle user interactions through MVC pattern");
    }
    
    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            LOGGER.info("System Look and Feel configured");
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            LOGGER.log(Level.WARNING, "Failed to set system look and feel, using default", e);
        }
    }
}