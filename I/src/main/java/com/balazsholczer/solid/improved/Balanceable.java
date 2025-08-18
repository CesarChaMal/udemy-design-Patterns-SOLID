package com.balazsholczer.solid.improved;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for tree balancing operations
 */
public interface Balanceable {
    CompletableFuture<Void> balance();
    CompletableFuture<Boolean> isBalanced();
    CompletableFuture<Integer> getHeight();
}