package com.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompleteableFutureExample {

    public static void main(String[] args)  {
        new CompleteableFutureExample().run();
    }

    public void run() {

        var suppliers = suppliers();

        System.out.println(suppliers.size());

        suppliers.parallelStream().forEach(supplier -> {

            CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier);

            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

    private List<Supplier<String>> suppliers() {
        var suppliers = new ArrayList<Supplier<String>>();

        suppliers.add(() -> "Gotta eat fast 0");


        suppliers.add(() -> {

            int timeout = 100;

            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

            return "I'm a little hungry " + timeout;
        });

        suppliers.add(() -> {

            int timeout = 50;

            try {

                TimeUnit.MILLISECONDS.sleep(timeout);

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Just a little time to eat " + timeout;
        });

        suppliers.add(() -> {

            int timeout = 1000;

            try {

                TimeUnit.MILLISECONDS.sleep(timeout);

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "more food please " + timeout;
        });

        suppliers.add(() -> {

            int timeout = 10000;

            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "BRING ME A BUCKET! " + timeout;
        });

        suppliers.add(() -> {

            int timeout = 8000;

            try {

                TimeUnit.MILLISECONDS.sleep(timeout);

            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "I'm super hungry " + timeout;
        });

        suppliers.add(() -> "I ate so fast 0");

        suppliers.add(() -> "I ate so fast too 0");

        return suppliers;

    }

}
