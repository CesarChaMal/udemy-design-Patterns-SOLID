package com.balazsholczer.compositeview;

import java.util.List;

/**
 * Composite View Pattern: creates aggregate views from atomic sub-views
 * 
 * Key Concepts:
 * - Composes complex views from simpler atomic sub-views
 * - Provides unified interface for both atomic and composite views
 * - Enables flexible view composition and reusability
 * - Supports hierarchical view structures and templates
 * 
 * Structure:
 * - View: common interface for atomic and composite views
 * - AtomicView: individual view components (header, footer, content)
 * - CompositeView: aggregates multiple views into single view
 * - ViewManager: manages view creation and composition
 * 
 * Benefits:
 * - Reusable atomic view components
 * - Flexible view composition and layout
 * - Consistent rendering interface
 * - Easy to add new view types and compositions
 * 
 * Use Cases:
 * - Web page layout composition
 * - Dashboard and portal applications
 * - Template-based view systems
 * - Component-based UI frameworks
 */

public class App {
    
    public static void main(String[] args) {
        
        System.out.println("=== Composite View Pattern ===");
        System.out.println("Creating aggregate views from atomic sub-views");
        System.out.println();
        
        System.out.println("=== Traditional Composite View ===");
        
        ViewManager viewManager = new ViewManager();
        
        // Create dashboard page using composite view
        CompositeView dashboard = viewManager.createDashboardPage("john_doe");
        
        System.out.println("Dashboard page rendered:");
        System.out.println(dashboard.render());
        
        System.out.println("\n" + "=".repeat(80) + "\n");
        
        // Create profile page using composite view
        CompositeView profile = viewManager.createProfilePage("john_doe");
        
        System.out.println("Profile page rendered:");
        System.out.println(profile.render());
        
        System.out.println("\n=== Custom Composite View ===");
        
        // Create custom page with specific views
        List<View> customViews = List.of(
            new HeaderView("Custom Page", "admin"),
            new ContentView("<h2>Custom Content</h2><p>This is a custom page layout.</p>"),
            new FooterView("© 2024 Custom App", "v2.0.0")
        );
        
        CompositeView customPage = viewManager.createCustomPage("Custom", customViews);
        System.out.println("Custom page rendered:");
        System.out.println(customPage.render());
        
        System.out.println("\n=== Atomic View Reusability ===");
        
        // Demonstrate atomic view reusability
        HeaderView sharedHeader = new HeaderView("Shared Application", "user123");
        FooterView sharedFooter = new FooterView("© 2024 Shared Corp", "v1.5.0");
        
        // Create multiple pages with shared components
        CompositeView page1 = new CompositeView("Page 1");
        page1.addView(sharedHeader);
        page1.addView(new ContentView("<h2>Page 1 Content</h2>"));
        page1.addView(sharedFooter);
        
        CompositeView page2 = new CompositeView("Page 2");
        page2.addView(sharedHeader);
        page2.addView(new ContentView("<h2>Page 2 Content</h2>"));
        page2.addView(new SidebarView(List.of("Widget 1", "Widget 2")));
        page2.addView(sharedFooter);
        
        System.out.println("Page 1 with shared components:");
        System.out.println(page1.render());
        
        System.out.println("\n" + "-".repeat(40) + "\n");
        
        System.out.println("Page 2 with shared components:");
        System.out.println(page2.render());
        
        System.out.println("\n=== Functional Composite View ===");
        
        // Create functional composite view
        FunctionalCompositeView funcPage = new FunctionalCompositeView(
            FunctionalCompositeView.htmlPageCompositor("Functional Page")
        );
        
        funcPage.addView(FunctionalCompositeView.headerSupplier("Functional App", "func_user"))
                .addView(FunctionalCompositeView.contentSupplier("<h2>Functional Content</h2><p>Built with functional composition.</p>"))
                .addView(FunctionalCompositeView.footerSupplier("© 2024 Functional Corp"));
        
        System.out.println("Functional composite view:");
        System.out.println(funcPage.render());
        
        System.out.println("\n=== JSON Composition ===");
        
        // Create JSON composite view
        FunctionalCompositeView jsonPage = new FunctionalCompositeView(
            FunctionalCompositeView.jsonCompositor()
        );
        
        jsonPage.addView(() -> "Header: JSON App")
                .addView(() -> "Content: JSON-based composition")
                .addView(() -> "Footer: JSON Corp");
        
        System.out.println("JSON composite view:");
        System.out.println(jsonPage.render());
        
        System.out.println("\n=== Dynamic View Composition ===");
        
        // Demonstrate dynamic view modification
        CompositeView dynamicPage = new CompositeView("Dynamic Page");
        dynamicPage.addView(new HeaderView("Dynamic App", "dynamic_user"));
        dynamicPage.addView(new ContentView("<h2>Initial Content</h2>"));
        
        System.out.println("Initial dynamic page:");
        System.out.println("Views count: " + dynamicPage.getChildViews().size());
        
        // Add more views dynamically
        dynamicPage.addView(new SidebarView(List.of("Dynamic Widget")));
        dynamicPage.addView(new FooterView("© 2024 Dynamic Corp", "v1.0.0"));
        
        System.out.println("Enhanced dynamic page:");
        System.out.println("Views count: " + dynamicPage.getChildViews().size());
        System.out.println(dynamicPage.render());
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Reusable atomic view components");
        System.out.println("✅ Flexible view composition and layout");
        System.out.println("✅ Consistent rendering interface");
        System.out.println("✅ Dynamic view modification capabilities");
        System.out.println("✅ Multiple composition strategies (HTML, JSON)");
        System.out.println("✅ Functional and traditional approaches");
        
        System.out.println("\n=== Pattern Benefits ===");
        System.out.println("Atomic Views: Reusable, focused, single responsibility");
        System.out.println("Composite Views: Flexible layout, hierarchical structure");
        System.out.println("View Manager: Centralized creation, caching, templates");
        System.out.println("Functional Approach: Composable, immutable, type-safe");
    }
}