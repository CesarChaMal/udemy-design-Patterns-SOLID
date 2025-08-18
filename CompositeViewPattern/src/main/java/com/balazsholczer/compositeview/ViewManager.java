package com.balazsholczer.compositeview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewManager {
    
    private Map<String, CompositeView> pageTemplates;
    
    public ViewManager() {
        this.pageTemplates = new HashMap<>();
        System.out.println("ViewManager: Initialized view manager");
    }
    
    public CompositeView createDashboardPage(String userName) {
        System.out.println("ViewManager: Creating dashboard page for " + userName);
        
        CompositeView dashboard = new CompositeView("Dashboard");
        
        // Add atomic views to composite
        dashboard.addView(new HeaderView("Dashboard", userName));
        dashboard.addView(new NavigationView(List.of("Home", "Profile", "Settings", "Logout")));
        dashboard.addView(new ContentView("<h2>Welcome to your dashboard!</h2><p>Here's your latest activity...</p>"));
        dashboard.addView(new SidebarView(List.of("Recent Activity", "Quick Stats", "Notifications")));
        dashboard.addView(new FooterView("© 2024 MyApp", "v1.0.0"));
        
        pageTemplates.put("dashboard_" + userName, dashboard);
        return dashboard;
    }
    
    public CompositeView createProfilePage(String userName) {
        System.out.println("ViewManager: Creating profile page for " + userName);
        
        CompositeView profile = new CompositeView("Profile");
        
        profile.addView(new HeaderView("User Profile", userName));
        profile.addView(new NavigationView(List.of("Dashboard", "Profile", "Settings", "Logout")));
        profile.addView(new ContentView("<h2>Profile Information</h2><p>Edit your profile details here...</p>"));
        profile.addView(new SidebarView(List.of("Profile Picture", "Account Settings", "Privacy")));
        profile.addView(new FooterView("© 2024 MyApp", "v1.0.0"));
        
        pageTemplates.put("profile_" + userName, profile);
        return profile;
    }
    
    public CompositeView createCustomPage(String pageName, List<View> views) {
        System.out.println("ViewManager: Creating custom page - " + pageName);
        
        CompositeView customPage = new CompositeView(pageName);
        
        for (View view : views) {
            customPage.addView(view);
        }
        
        pageTemplates.put(pageName, customPage);
        return customPage;
    }
    
    public CompositeView getPage(String pageKey) {
        return pageTemplates.get(pageKey);
    }
    
    public void cachePage(String key, CompositeView page) {
        pageTemplates.put(key, page);
        System.out.println("ViewManager: Cached page - " + key);
    }
}