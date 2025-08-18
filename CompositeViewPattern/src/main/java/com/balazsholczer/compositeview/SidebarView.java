package com.balazsholczer.compositeview;

import java.util.List;

public class SidebarView implements View {
    
    private List<String> widgets;
    
    public SidebarView(List<String> widgets) {
        this.widgets = widgets;
        System.out.println("SidebarView: Created sidebar view with " + widgets.size() + " widgets");
    }
    
    @Override
    public String render() {
        System.out.println("SidebarView: Rendering sidebar");
        StringBuilder sidebar = new StringBuilder("<aside>\n");
        
        for (String widget : widgets) {
            sidebar.append("  <div class='widget'>").append(widget).append("</div>\n");
        }
        
        sidebar.append("</aside>");
        return sidebar.toString();
    }
}