package com.balazsholczer.abstractfactory;

public class ModernAbstractFactoryDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Traditional Abstract Factory Pattern ===");
        GUIFactory windowsFactory = new WindowsFactory();
        Button winButton = windowsFactory.createButton();
        Checkbox winCheckbox = windowsFactory.createCheckbox();
        winButton.render();
        winCheckbox.render();
        
        GUIFactory macFactory = new MacFactory();
        Button macButton = macFactory.createButton();
        Checkbox macCheckbox = macFactory.createCheckbox();
        macButton.render();
        macCheckbox.render();
        
        System.out.println("\n=== Functional Abstract Factory Pattern ===");
        var functionalWinFactory = FunctionalGUIFactory.getFactory("windows");
        Button funcWinButton = functionalWinFactory.createButton();
        Checkbox funcWinCheckbox = functionalWinFactory.createCheckbox();
        funcWinButton.render();
        funcWinCheckbox.render();
        
        var functionalMacFactory = FunctionalGUIFactory.getFactory("mac");
        Button funcMacButton = functionalMacFactory.createButton();
        Checkbox funcMacCheckbox = functionalMacFactory.createCheckbox();
        funcMacButton.render();
        funcMacCheckbox.render();
        
        System.out.println("\n=== Enum Abstract Factory Pattern ===");
        EnumGUIFactory enumWinFactory = EnumGUIFactory.getFactory("windows");
        Button enumWinButton = enumWinFactory.createButton();
        Checkbox enumWinCheckbox = enumWinFactory.createCheckbox();
        enumWinButton.render();
        enumWinCheckbox.render();
        
        EnumGUIFactory enumMacFactory = EnumGUIFactory.getFactory("mac");
        Button enumMacButton = enumMacFactory.createButton();
        Checkbox enumMacCheckbox = enumMacFactory.createCheckbox();
        enumMacButton.render();
        enumMacCheckbox.render();
        
        System.out.println("\n=== Generic Abstract Factory Pattern ===");
        GenericAbstractFactory<?> genericWinFactory = GenericAbstractFactory.windowsFactory();
        Button genericWinButton = genericWinFactory.create(Button.class);
        Checkbox genericWinCheckbox = genericWinFactory.create(Checkbox.class);
        genericWinButton.render();
        genericWinCheckbox.render();
        
        GenericAbstractFactory<?> genericMacFactory = GenericAbstractFactory.macFactory();
        Button genericMacButton = genericMacFactory.create(Button.class);
        Checkbox genericMacCheckbox = genericMacFactory.create(Checkbox.class);
        genericMacButton.render();
        genericMacCheckbox.render();
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Enum - All Available Factories:");
        for (EnumGUIFactory factory : EnumGUIFactory.values()) {
            System.out.println("Factory: " + factory.name());
            factory.createButton().render();
        }
        
        System.out.println("Functional - Dynamic Factory Selection:");
        String[] platforms = {"windows", "mac"};
        for (String platform : platforms) {
            var factory = FunctionalGUIFactory.getFactory(platform);
            System.out.println("Platform: " + platform);
            factory.createButton().onClick();
        }
    }
}