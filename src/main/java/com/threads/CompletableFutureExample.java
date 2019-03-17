package com.threads;

import com.threads.factory.Factory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new CompletableFutureExample().run();
    }

    public void run() throws ExecutionException, InterruptedException {

        int cores = Runtime.getRuntime().availableProcessors();

        ForkJoinPool pool = new ForkJoinPool(cores);

        var suppliers = Factory.suppliers();

        System.out.println("\nNumber of tasks: " + suppliers.size() + "\n");

        pool.submit(() -> suppliers.parallelStream().forEach(supplier -> {
            try {
                System.out.println(CompletableFuture.supplyAsync(supplier).get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        })).get();
    }
}
