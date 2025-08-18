package com.balazsholczer.nullobject;

public class ModernNullObjectDemo {
    
    public static void main(String[] args) {
        
        System.out.println("=== Traditional Null Object Pattern ===");
        CustomerFactory traditional = new CustomerFactory();
        System.out.println(traditional.getCustomer("Joe").getPerson());
        System.out.println(traditional.getCustomer("Joel").getPerson());
        System.out.println(traditional.getCustomer("Daniel").getPerson());
        System.out.println(traditional.getCustomer("Adam").getPerson());
        
        System.out.println("\n=== Optional Pattern ===");
        OptionalCustomerFactory optional = new OptionalCustomerFactory();
        System.out.println(optional.getCustomerName("Joe"));
        System.out.println(optional.getCustomerName("Joel"));
        System.out.println(optional.getCustomerName("Daniel"));
        System.out.println(optional.getCustomerName("Adam"));
        
        System.out.println("\n=== Functional Pattern ===");
        FunctionalCustomerFactory functional = new FunctionalCustomerFactory();
        System.out.println(functional.getCustomerName("Joe"));
        System.out.println(functional.getCustomerName("Joel"));
        System.out.println(functional.getCustomerName("Daniel"));
        System.out.println(functional.getCustomerName("Adam"));
        
        System.out.println("\n=== Record Pattern ===");
        RecordCustomerFactory record = new RecordCustomerFactory();
        System.out.println(record.getCustomer("Joe").getPerson());
        System.out.println(record.getCustomer("Joel").getPerson());
        System.out.println(record.getCustomer("Daniel").getPerson());
        System.out.println(record.getCustomer("Adam").getPerson());
        
        System.out.println("\n=== Advanced Features ===");
        System.out.println("Optional - Check if present:");
        optional.getCustomer("Joe").ifPresentOrElse(
            name -> System.out.println("Found: " + name),
            () -> System.out.println("Not found")
        );
        
        System.out.println("Record - Check existence:");
        CustomerRecord customer = record.getCustomer("Joel");
        System.out.println("Exists: " + !customer.isNull() + ", Name: " + customer.name());
    }
}