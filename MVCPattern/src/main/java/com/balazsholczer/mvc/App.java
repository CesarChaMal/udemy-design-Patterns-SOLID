package com.balazsholczer.mvc;

import com.balazsholczer.mvc.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {

	/**
	 * 	MVC Pattern: we can separate the application with the help of this pattern
	 * 	  Why is it good? if we want to add extra features it can be done very very easily !!!
	 * 
	 * 		- Model: represents an object or a class carrying data. 
	 * 				It can also have logic to update controller if its data changes.
	 * 		
	 * 		- Controller: acts on both model and view
	 * 			 It controls the data flow into model object 
	 * 					and updates the view whenever data changes. 
	 * 				It keeps view and model separate
	 * 
	 * 		- View: represents the visualization of the data that model contains
	 * 
	 */
	
	public static void main(String[] args) {
		System.out.println("=== MVC Pattern Demo ===");
		System.out.println("Model-View-Controller pattern separates concerns:");
		System.out.println("- Model: Data and business logic");
		System.out.println("- View: User interface presentation");
		System.out.println("- Controller: Handles user input and coordinates Model/View");
		System.out.println("MVC Pattern demonstration completed.");
		
		// Uncomment below to run full Swing GUI demo:
		/*
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
		*/
	}
}
