package com.balazsholczer.compositeview;

public class FooterView implements View {
    
    private String copyright;
    private String version;
    
    public FooterView(String copyright, String version) {
        this.copyright = copyright;
        this.version = version;
        System.out.println("FooterView: Created footer view");
    }
    
    @Override
    public String render() {
        System.out.println("FooterView: Rendering footer");
        return "<footer>\n" +
               "  <p>" + copyright + "</p>\n" +
               "  <p>Version: " + version + "</p>\n" +
               "</footer>";
    }
}