package com.balazsholczer.daofactory;

import java.math.BigDecimal;

/**
 * DAO Factory Pattern: factory for creating Data Access Object instances
 * 
 * Key Concepts:
 * - Abstracts DAO creation and provides unified factory interface
 * - Supports multiple database implementations (MySQL, PostgreSQL, etc.)
 * - Centralizes DAO instantiation logic
 * - Enables easy switching between different data sources
 * 
 * Structure:
 * - DAOFactory: abstract factory for creating DAO instances
 * - ConcreteDAOFactory: specific factory implementations for different databases
 * - DAO: data access object interfaces
 * - ConcreteDAO: specific DAO implementations for different databases
 * 
 * Benefits:
 * - Database-agnostic application code
 * - Easy switching between different data sources
 * - Centralized DAO creation logic
 * - Consistent DAO instantiation across application
 * 
 * Use Cases:
 * - Multi-database applications
 * - Applications requiring database migration capability
 * - Enterprise applications with multiple data sources
 * - Testing with different database implementations
 */

public class App {
    
    public static void main(String[] args) {
        System.out.println("=== DAO Factory Pattern ===");
        System.out.println("Factory for creating Data Access Object instances");
        System.out.println();
        
        System.out.println("=== Traditional DAO Factory ===");
        
        // Get MySQL DAO factory
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        
        UserDAO mysqlUserDAO = mysqlFactory.getUserDAO();
        ProductDAO mysqlProductDAO = mysqlFactory.getProductDAO();
        
        // Use MySQL DAOs
        User user1 = new User("1", "John Doe", "john@example.com");
        mysqlUserDAO.save(user1);
        
        Product product1 = new Product("P1", "Laptop", BigDecimal.valueOf(999.99));
        mysqlProductDAO.save(product1);
        
        User retrievedUser = mysqlUserDAO.findById("1");
        System.out.println("Retrieved from MySQL: " + retrievedUser);
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Get PostgreSQL DAO factory
        DAOFactory postgresFactory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
        
        UserDAO postgresUserDAO = postgresFactory.getUserDAO();
        ProductDAO postgresProductDAO = postgresFactory.getProductDAO();
        
        // Use PostgreSQL DAOs
        User user2 = new User("2", "Jane Smith", "jane@example.com");
        postgresUserDAO.save(user2);
        
        Product product2 = new Product("P2", "Mouse", BigDecimal.valueOf(29.99));
        postgresProductDAO.save(product2);
        
        User retrievedUser2 = postgresUserDAO.findById("2");
        System.out.println("Retrieved from PostgreSQL: " + retrievedUser2);
        
        System.out.println("\n=== Functional DAO Factory ===");
        
        FunctionalDAOFactory funcFactory = FunctionalDAOFactory.create();
        
        // Create DAOs using functional approach
        UserDAO funcMySQLUserDAO = funcFactory.getUserDAO("mysql");
        ProductDAO funcPostgresProductDAO = funcFactory.getProductDAO("postgresql");
        
        User user3 = new User("3", "Bob Johnson", "bob@example.com");
        funcMySQLUserDAO.save(user3);
        
        Product product3 = new Product("P3", "Keyboard", BigDecimal.valueOf(79.99));
        funcPostgresProductDAO.save(product3);
        
        // Generic DAO creation
        UserDAO genericUserDAO = funcFactory.createDAO("mysql", UserDAO.class);
        ProductDAO genericProductDAO = funcFactory.createDAO("postgresql", ProductDAO.class);
        
        User user4 = new User("4", "Alice Brown", "alice@example.com");
        genericUserDAO.save(user4);
        
        System.out.println("\n=== Database Migration Simulation ===");
        
        // Simulate migrating from MySQL to PostgreSQL
        System.out.println("Migrating data from MySQL to PostgreSQL...");
        
        // Get all users from MySQL
        var mysqlUsers = mysqlUserDAO.findAll();
        System.out.println("Found " + mysqlUsers.size() + " users in MySQL");
        
        // Save to PostgreSQL
        for (User user : mysqlUsers) {
            postgresUserDAO.save(user);
        }
        
        // Verify migration
        var postgresUsers = postgresUserDAO.findAll();
        System.out.println("Migrated " + postgresUsers.size() + " users to PostgreSQL");
        
        System.out.println("\n=== Configuration-Based Factory ===");
        
        // Simulate configuration-driven DAO selection
        String databaseType = System.getProperty("database.type", "mysql");
        System.out.println("Using database type from configuration: " + databaseType);
        
        DAOFactory configFactory = switch (databaseType.toLowerCase()) {
            case "mysql" -> DAOFactory.getDAOFactory(DAOFactory.MYSQL);
            case "postgresql" -> DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
            default -> throw new IllegalArgumentException("Unsupported database: " + databaseType);
        };
        
        UserDAO configUserDAO = configFactory.getUserDAO();
        User configUser = new User("CONFIG", "Config User", "config@example.com");
        configUserDAO.save(configUser);
        
        System.out.println("\n=== Benefits Demonstrated ===");
        System.out.println("✅ Database-agnostic application code");
        System.out.println("✅ Easy switching between different data sources");
        System.out.println("✅ Centralized DAO creation logic");
        System.out.println("✅ Consistent DAO instantiation across application");
        System.out.println("✅ Support for database migration scenarios");
        System.out.println("✅ Configuration-driven DAO selection");
        
        System.out.println("\n=== Factory Comparison ===");
        System.out.println("Traditional: Type-safe with constants, compile-time checking");
        System.out.println("Functional: String-based, runtime flexibility, lambda suppliers");
        System.out.println("Both: Enable database abstraction and easy switching");
    }
}