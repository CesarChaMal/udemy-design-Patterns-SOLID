package com.balazsholczer.compositeview;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FunctionalCompositeView implements View {
    
    private final List<Supplier<String>> viewSuppliers;
    private final Function<List<String>, String> compositor;
    
    public FunctionalCompositeView(Function<List<String>, String> compositor) {
        this.viewSuppliers = new ArrayList<>();
        this.compositor = compositor;
        System.out.println("FunctionalCompositeView: Created functional composite view");
    }
    
    public FunctionalCompositeView addView(Supplier<String> viewSupplier) {
        viewSuppliers.add(viewSupplier);
        System.out.println("FunctionalCompositeView: Added functional view supplier");
        return this;
    }
    
    public FunctionalCompositeView addView(View view) {
        viewSuppliers.add(view::render);
        System.out.println("FunctionalCompositeView: Added traditional view as supplier");
        return this;
    }
    
    @Override
    public String render() {
        System.out.println("FunctionalCompositeView: Rendering with " + viewSuppliers.size() + " suppliers");
        
        List<String> renderedViews = viewSuppliers.stream()
                .map(Supplier::get)
                .collect(Collectors.toList());
        
        return compositor.apply(renderedViews);
    }
    
    // Predefined compositors
    public static Function<List<String>, String> htmlPageCompositor(String title) {
        return views -> {
            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n<html>\n<head>\n  <title>").append(title).append("</title>\n</head>\n<body>\n");
            views.forEach(view -> html.append(view).append("\n"));
            html.append("</body>\n</html>");
            return html.toString();
        };
    }
    
    public static Function<List<String>, String> jsonCompositor() {
        return views -> {
            StringBuilder json = new StringBuilder("{\n  \"views\": [\n");
            for (int i = 0; i < views.size(); i++) {
                json.append("    \"").append(views.get(i).replace("\"", "\\\"")).append("\"");
                if (i < views.size() - 1) json.append(",");
                json.append("\n");
            }
            json.append("  ]\n}");
            return json.toString();
        };
    }
    
    public static Function<List<String>, String> simpleCompositor() {
        return views -> String.join("\n", views);
    }
    
    // Functional view factories
    public static Supplier<String> headerSupplier(String title, String user) {
        return () -> {
            System.out.println("Functional: Rendering header");
            return "<header><h1>" + title + "</h1><div>Welcome, " + user + "</div></header>";
        };
    }
    
    public static Supplier<String> contentSupplier(String content) {
        return () -> {
            System.out.println("Functional: Rendering content");
            return "<main><div class='content'>" + content + "</div></main>";
        };
    }
    
    public static Supplier<String> footerSupplier(String text) {
        return () -> {
            System.out.println("Functional: Rendering footer");
            return "<footer><p>" + text + "</p></footer>";
        };
    }
}