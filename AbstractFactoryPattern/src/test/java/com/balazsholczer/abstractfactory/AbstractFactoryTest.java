package com.balazsholczer.abstractfactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test for Abstract Factory Pattern
 * Tests both Traditional and Modern approaches
 */
class AbstractFactoryTest {
    
    @Test
    void testTraditionalMacFactory() {
        GUIFactory factory = new MacFactory();
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        
        assertNotNull(button);
        assertNotNull(checkbox);
        assertTrue(button instanceof MacButton);
        assertTrue(checkbox instanceof MacCheckbox);
    }
    
    @Test
    void testTraditionalWindowsFactory() {
        GUIFactory factory = new WindowsFactory();
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        
        assertNotNull(button);
        assertNotNull(checkbox);
        assertTrue(button instanceof WindowsButton);
        assertTrue(checkbox instanceof WindowsCheckbox);
    }
    
    @Test
    void testEnumGUIFactory() {
        EnumGUIFactory macType = EnumGUIFactory.MAC;
        Button macButton = macType.createButton();
        Checkbox macCheckbox = macType.createCheckbox();
        
        assertNotNull(macButton);
        assertNotNull(macCheckbox);
        assertTrue(macButton instanceof MacButton);
        assertTrue(macCheckbox instanceof MacCheckbox);
    }
    
    @Test
    void testFunctionalGUIFactory() {
        FunctionalGUIFactory.ComponentFactory factory = FunctionalGUIFactory.getFactory("MAC");
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        
        assertNotNull(button);
        assertNotNull(checkbox);
        assertTrue(button instanceof MacButton);
        assertTrue(checkbox instanceof MacCheckbox);
    }
    
    @Test
    void testGenericAbstractFactory() {
        GenericAbstractFactory<?> factory = GenericAbstractFactory.macFactory();
        Button button = factory.create(Button.class);
        Checkbox checkbox = factory.create(Checkbox.class);
        
        assertNotNull(button);
        assertNotNull(checkbox);
        assertTrue(button instanceof MacButton);
        assertTrue(checkbox instanceof MacCheckbox);
    }
    
    @Test
    void testEquivalence() {
        // Test that all approaches create equivalent objects
        GUIFactory traditional = new MacFactory();
        EnumGUIFactory enumFactory = EnumGUIFactory.MAC;
        FunctionalGUIFactory.ComponentFactory functional = FunctionalGUIFactory.getFactory("MAC");
        GenericAbstractFactory<?> generic = GenericAbstractFactory.macFactory();
        
        Button traditionalButton = traditional.createButton();
        Button enumButton = enumFactory.createButton();
        Button functionalButton = functional.createButton();
        Button genericButton = generic.create(Button.class);
        
        // All should be MacButton instances
        assertEquals(traditionalButton.getClass(), enumButton.getClass());
        assertEquals(enumButton.getClass(), functionalButton.getClass());
        assertEquals(functionalButton.getClass(), genericButton.getClass());
    }
}