package com.threads;

import com.threads.factory.Factory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * This code is optimized to return completed futures as soon as they complete.  The idea is that many different tasks
 * will take a variable amount of time, but we want to be able to get the completed tasks results back without having
 * to wait for longer running tasks.
 */
public class CompletableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new CompletableFutureExample().run();
    }

    public void run() throws ExecutionException, InterruptedException {

        int cores = Runtime.getRuntime().availableProcessors();


        /**
         * This ForkJoinPool is created so that the parallelStream call happens with this pool, rather than the default
         * pool.  The default thread pool is used for the entire application which can lead to performance issues.
         * The separate pool ensures sufficient threads to service the suppliers that will be submitted.
         */
        ForkJoinPool pool = new ForkJoinPool(cores);

        var suppliers = Factory.suppliers();

        System.out.println("\nNumber of tasks: " + suppliers.size() + "\n");


        /**
         * Important things to realize about this code:
         *
         * First, parallelStream() is what allows the all the different suppliers to run in parallel.  You can test
         * this by changing suppliers.parallelStream() to suppliers.stream() and you will see that the suppliers execute
         * in the order they are added to the List, rather than allowing shorter running suppliers to return as they
         * complete.
         *
         * What is happening under the covers with parallelStream() is that multiple threads are processing the
         * iterations so that the CompletableFuture(s) can happen concurrently.  Without the parallelStream() you get
         * blocked processing each thread on each iteration.
         *
         * Second, the supplyAsync call is used so that a value can be returned from the thread.  In this case its the
         * string value that is being returned.
         */
        Runnable runnable = () -> suppliers.parallelStream().forEach(supplier -> {
            try {
                System.out.println(CompletableFuture.supplyAsync(supplier).get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        // This kicks off all the fun
        pool.submit(runnable).get();
    }
}
