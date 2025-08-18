package com.balazsholczer.compositeview;

import java.util.ArrayList;
import java.util.List;

public class CompositeView implements View {
    
    private List<View> childViews;
    private String template;
    
    public CompositeView(String template) {
        this.childViews = new ArrayList<>();
        this.template = template;
        System.out.println("CompositeView: Created composite view with template");
    }
    
    public void addView(View view) {
        childViews.add(view);
        System.out.println("CompositeView: Added child view - " + view.getClass().getSimpleName());
    }
    
    public void removeView(View view) {
        childViews.remove(view);
        System.out.println("CompositeView: Removed child view - " + view.getClass().getSimpleName());
    }
    
    @Override
    public String render() {
        System.out.println("CompositeView: Rendering composite view with " + childViews.size() + " child views");
        
        StringBuilder result = new StringBuilder();
        result.append("<!DOCTYPE html>\n<html>\n<head>\n  <title>").append(template).append("</title>\n</head>\n<body>\n");
        
        // Render all child views
        for (View childView : childViews) {
            result.append(childView.render()).append("\n");
        }
        
        result.append("</body>\n</html>");
        return result.toString();
    }
    
    public List<View> getChildViews() {
        return new ArrayList<>(childViews);
    }
}