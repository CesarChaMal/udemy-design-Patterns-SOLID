package com.balazsholczer.compositeview;

import java.util.List;

public class NavigationView implements View {
    
    private List<String> menuItems;
    
    public NavigationView(List<String> menuItems) {
        this.menuItems = menuItems;
        System.out.println("NavigationView: Created navigation view with " + menuItems.size() + " items");
    }
    
    @Override
    public String render() {
        System.out.println("NavigationView: Rendering navigation");
        StringBuilder nav = new StringBuilder("<nav>\n  <ul>\n");
        
        for (String item : menuItems) {
            nav.append("    <li><a href='#'>").append(item).append("</a></li>\n");
        }
        
        nav.append("  </ul>\n</nav>");
        return nav.toString();
    }
}