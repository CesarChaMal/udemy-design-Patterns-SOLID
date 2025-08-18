package com.balazsholczer.abstractfactory;

import java.util.function.Supplier;

public enum EnumGUIFactory {
    WINDOWS(WindowsButton::new, WindowsCheckbox::new),
    MAC(MacButton::new, MacCheckbox::new);
    
    private final Supplier<Button> buttonFactory;
    private final Supplier<Checkbox> checkboxFactory;
    
    EnumGUIFactory(Supplier<Button> buttonFactory, Supplier<Checkbox> checkboxFactory) {
        this.buttonFactory = buttonFactory;
        this.checkboxFactory = checkboxFactory;
    }
    
    public Button createButton() {
        return buttonFactory.get();
    }
    
    public Checkbox createCheckbox() {
        return checkboxFactory.get();
    }
    
    public static EnumGUIFactory getFactory(String os) {
        return switch (os.toLowerCase()) {
            case "windows" -> WINDOWS;
            case "mac" -> MAC;
            default -> throw new IllegalArgumentException("Unsupported OS: " + os);
        };
    }
}