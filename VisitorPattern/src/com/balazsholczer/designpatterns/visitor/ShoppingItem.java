package com.balazsholczer.designpatterns.visitor;

public interface ShoppingItem {
	public double accept(ShoppingCartVisitor visitor);
}
