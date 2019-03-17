package com.threads;

/**
 * Provides an example of how to create threads and have them return in the order they started
 *
 * ExecutorService.invokeAll is a better implementation
 */
public class JoinExample {

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("1");
        });


        System.out.println("starting t1");
        t1.start();
        t1.join(); // blocks here until t1 dies


        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("2");
        });

        System.out.println("starting t2");
        t2.start();
        t2.join(); // blocks here until t2 thread has died


        Thread t3 = new Thread(() -> System.out.println("3"));

        System.out.println("starting t3");
        t3.start(); // no join needed because can't get here until other threads are completed


    }
}
