package com.balazsholczer.pagecontroller;

public interface PageController {
    HttpResponse handleRequest(HttpRequest request);
}