package com.balazsholczer.compositeview;

public class ContentView implements View {
    
    private String content;
    
    public ContentView(String content) {
        this.content = content;
        System.out.println("ContentView: Created content view");
    }
    
    @Override
    public String render() {
        System.out.println("ContentView: Rendering main content");
        return "<main>\n" +
               "  <div class='content'>\n" +
               "    " + content + "\n" +
               "  </div>\n" +
               "</main>";
    }
}