package com.balazsholczer.compositeview;

public class HeaderView implements View {
    
    private String title;
    private String userName;
    
    public HeaderView(String title, String userName) {
        this.title = title;
        this.userName = userName;
        System.out.println("HeaderView: Created header view");
    }
    
    @Override
    public String render() {
        System.out.println("HeaderView: Rendering header");
        return "<header>\n" +
               "  <h1>" + title + "</h1>\n" +
               "  <div class='user-info'>Welcome, " + userName + "</div>\n" +
               "</header>";
    }
}