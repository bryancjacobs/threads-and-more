package com.threads.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Just creates the objects needed for testing purposes
 */
public class Factory {



    public static List<Supplier<String>> suppliers() {

        var suppliers = new ArrayList<Supplier<String>>();

        suppliers.add(() -> "Gotta eat fast 0");

        suppliers.add(() -> {

            int timeout = 1100;

            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }

            return "Yeah I could eat " + timeout;
        });

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

    public static List<Callable<String>> callables() {

        var callables = new ArrayList<Callable<String>>();

        callables.add(() -> "quick");

        callables.add(() -> {

            Thread.sleep(5000);

            return "ready for dinner";
        });

        callables.add(() -> "yes i am");


        callables.add(() -> "more quick");
        callables.add(() -> {

            Thread.sleep(150);

            return "yummy";
        });

        callables.add(() -> {

            Thread.sleep(3000);

            return "ready for playtime";
        });

        callables.add(() -> "even quicker");


        return callables;
    }
}
