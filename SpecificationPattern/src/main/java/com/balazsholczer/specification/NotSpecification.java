package com.balazsholczer.specification;

public class NotSpecification<T> implements Specification<T> {
    
    private final Specification<T> specification;
    
    public NotSpecification(Specification<T> specification) {
        this.specification = specification;
    }
    
    @Override
    public boolean isSatisfiedBy(T candidate) {
        return !specification.isSatisfiedBy(candidate);
    }
}