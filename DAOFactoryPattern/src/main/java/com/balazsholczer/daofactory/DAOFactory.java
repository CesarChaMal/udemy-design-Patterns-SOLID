package com.balazsholczer.daofactory;

public abstract class DAOFactory {
    
    public static final int MYSQL = 1;
    public static final int POSTGRESQL = 2;
    
    public abstract UserDAO getUserDAO();
    public abstract ProductDAO getProductDAO();
    
    public static DAOFactory getDAOFactory(int whichFactory) {
        return switch (whichFactory) {
            case MYSQL -> {
                System.out.println("DAOFactory: Creating MySQL DAO Factory");
                yield new MySQLDAOFactory();
            }
            case POSTGRESQL -> {
                System.out.println("DAOFactory: Creating PostgreSQL DAO Factory");
                yield new PostgreSQLDAOFactory();
            }
            default -> throw new IllegalArgumentException("Unknown factory type: " + whichFactory);
        };
    }
}