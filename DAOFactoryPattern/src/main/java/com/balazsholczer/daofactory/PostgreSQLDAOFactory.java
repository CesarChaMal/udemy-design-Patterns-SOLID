package com.balazsholczer.daofactory;

public class PostgreSQLDAOFactory extends DAOFactory {
    
    @Override
    public UserDAO getUserDAO() {
        System.out.println("PostgreSQLDAOFactory: Creating PostgreSQL UserDAO");
        return new PostgreSQLUserDAO();
    }
    
    @Override
    public ProductDAO getProductDAO() {
        System.out.println("PostgreSQLDAOFactory: Creating PostgreSQL ProductDAO");
        return new PostgreSQLProductDAO();
    }
}