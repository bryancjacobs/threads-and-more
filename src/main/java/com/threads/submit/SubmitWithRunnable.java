package com.threads.submit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubmitWithRunnable {

    public static void main(String[] args) {

        new SubmitWithRunnable().run();
    }

    public void run() {
        ExecutorService es = Executors.newFixedThreadPool(3);

        runnables().forEach(runnable -> es.submit(runnable));

        System.out.println("done");

        es.shutdown();

    }


    private List<Runnable> runnables() {

        var callables = new ArrayList<Runnable>();

        callables.add(() -> {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("ready for dinner");
        });

        callables.add(() -> System.out.println("yes i am"));



        return callables;
    }


}
