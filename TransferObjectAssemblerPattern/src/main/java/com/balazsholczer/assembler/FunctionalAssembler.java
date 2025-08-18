package com.balazsholczer.assembler;

import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionalAssembler {
    
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    
    // Functional composition for assembling transfer objects
    public Function<String, CustomerTO> basicAssembler() {
        return customerId -> {
            System.out.println("FunctionalAssembler: Basic assembly for " + customerId);
            return customerDAO.findById(customerId);
        };
    }
    
    public Function<String, CustomerTO> withOrders() {
        return basicAssembler().andThen(customer -> {
            if (customer != null) {
                System.out.println("FunctionalAssembler: Adding orders");
                customer.setOrders(orderDAO.findByCustomerId(customer.getCustomerId()));
            }
            return customer;
        });
    }
    
    public Function<CustomerTO, CustomerTO> withAccount() {
        return customer -> {
            if (customer != null) {
                System.out.println("FunctionalAssembler: Adding account");
                customer.setAccount(accountDAO.findByCustomerId(customer.getCustomerId()));
            }
            return customer;
        };
    }
    
    public Function<String, CustomerTO> fullAssembler() {
        return basicAssembler()
                .andThen(withAccount())
                .andThen(customer -> {
                    if (customer != null) {
                        System.out.println("FunctionalAssembler: Adding orders");
                        customer.setOrders(orderDAO.findByCustomerId(customer.getCustomerId()));
                    }
                    return customer;
                });
    }
    
    // Builder pattern with functional composition
    public static class AssemblerBuilder {
        private Function<String, CustomerTO> assembler;
        
        public AssemblerBuilder() {
            FunctionalAssembler fa = new FunctionalAssembler();
            this.assembler = fa.basicAssembler();
        }
        
        public AssemblerBuilder withOrders() {
            this.assembler = this.assembler.andThen(customer -> {
                if (customer != null) {
                    System.out.println("AssemblerBuilder: Adding orders");
                    customer.setOrders(new OrderDAO().findByCustomerId(customer.getCustomerId()));
                }
                return customer;
            });
            return this;
        }
        
        public AssemblerBuilder withAccount() {
            FunctionalAssembler fa = new FunctionalAssembler();
            this.assembler = this.assembler.andThen(fa.withAccount());
            return this;
        }
        
        public Function<String, CustomerTO> build() {
            return assembler;
        }
    }
}