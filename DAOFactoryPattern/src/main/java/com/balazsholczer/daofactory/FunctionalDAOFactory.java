package com.balazsholczer.daofactory;

import java.util.Map;
import java.util.function.Supplier;

public class FunctionalDAOFactory {
    
    private final Map<String, Supplier<UserDAO>> userDAOSuppliers;
    private final Map<String, Supplier<ProductDAO>> productDAOSuppliers;
    
    public FunctionalDAOFactory() {
        this.userDAOSuppliers = Map.of(
            "mysql", () -> {
                System.out.println("FunctionalDAOFactory: Creating MySQL UserDAO");
                return new MySQLUserDAO();
            },
            "postgresql", () -> {
                System.out.println("FunctionalDAOFactory: Creating PostgreSQL UserDAO");
                return new PostgreSQLUserDAO();
            }
        );
        
        this.productDAOSuppliers = Map.of(
            "mysql", () -> {
                System.out.println("FunctionalDAOFactory: Creating MySQL ProductDAO");
                return new MySQLProductDAO();
            },
            "postgresql", () -> {
                System.out.println("FunctionalDAOFactory: Creating PostgreSQL ProductDAO");
                return new PostgreSQLProductDAO();
            }
        );
        
        System.out.println("FunctionalDAOFactory: Initialized with functional suppliers");
    }
    
    public UserDAO getUserDAO(String type) {
        Supplier<UserDAO> supplier = userDAOSuppliers.get(type.toLowerCase());
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown UserDAO type: " + type);
        }
        return supplier.get();
    }
    
    public ProductDAO getProductDAO(String type) {
        Supplier<ProductDAO> supplier = productDAOSuppliers.get(type.toLowerCase());
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown ProductDAO type: " + type);
        }
        return supplier.get();
    }
    
    // Factory method with configuration
    public static FunctionalDAOFactory create() {
        return new FunctionalDAOFactory();
    }
    
    // Fluent configuration
    public <T> T createDAO(String type, Class<T> daoClass) {
        if (daoClass == UserDAO.class) {
            return daoClass.cast(getUserDAO(type));
        } else if (daoClass == ProductDAO.class) {
            return daoClass.cast(getProductDAO(type));
        }
        throw new IllegalArgumentException("Unsupported DAO class: " + daoClass.getName());
    }
}