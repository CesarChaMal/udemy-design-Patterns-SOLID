package com.balazsholczer.daofactory;

public class MySQLDAOFactory extends DAOFactory {
    
    @Override
    public UserDAO getUserDAO() {
        System.out.println("MySQLDAOFactory: Creating MySQL UserDAO");
        return new MySQLUserDAO();
    }
    
    @Override
    public ProductDAO getProductDAO() {
        System.out.println("MySQLDAOFactory: Creating MySQL ProductDAO");
        return new MySQLProductDAO();
    }
}