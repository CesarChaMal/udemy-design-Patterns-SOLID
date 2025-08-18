package com.balazsholczer.abstractfactory;

import java.util.function.Supplier;

public class FunctionalGUIFactory {
    
    public record ComponentFactory(Supplier<Button> buttonFactory, Supplier<Checkbox> checkboxFactory) {
        
        public Button createButton() {
            return buttonFactory.get();
        }
        
        public Checkbox createCheckbox() {
            return checkboxFactory.get();
        }
    }
    
    public static final ComponentFactory WINDOWS_FACTORY = 
        new ComponentFactory(WindowsButton::new, WindowsCheckbox::new);
    
    public static final ComponentFactory MAC_FACTORY = 
        new ComponentFactory(MacButton::new, MacCheckbox::new);
    
    public static ComponentFactory getFactory(String os) {
        return switch (os.toLowerCase()) {
            case "windows" -> WINDOWS_FACTORY;
            case "mac" -> MAC_FACTORY;
            default -> throw new IllegalArgumentException("Unsupported OS: " + os);
        };
    }
}