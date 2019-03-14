package com.threads.submit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * My attempt at creating a multi-threaded solution that finds threads that have completed
 */
public class SubmitWithCallable {

    public static void main(String[] args) throws Exception {
        new SubmitWithCallable().run();
    }

    public void run() throws Exception {

        // build an executor that has three threads to service the callables
        var es = Executors.newFixedThreadPool(3);

        // callables are threads which can return a value...lets make some to process
        var callables = callables();

        System.out.println("\n *** Total tasks: " + callables.size() + "\n");

        var pendingFutures = new ArrayList<Future<String>>();

        // this first loop tries to find all the callables that completed quickly
        for (var callable : callables) {

            // use the ExecutorService to spin of a thread that will do the work defined by the callable
            var future = es.submit(callable);

            try {

                // wait 50 microseconds for the value otherwise throw a TimeoutException
                System.out.println(future.get(50, TimeUnit.MICROSECONDS));

            } catch (TimeoutException e) {

                /**
                 * if we ended up here it means that the call to future.get was unable to return in time
                 * so we add to pendingFutures because we need to come back later and check to see if the
                 * callable has completed.
                 * A future in java is just a way of saying something might not be done yet
                 */
                pendingFutures.add(future);
            }
        }

        System.out.println("\n *** Completed tasks: " + (callables.size() - pendingFutures.size()));

        System.out.println("\n *** Remaining tasks: " + pendingFutures.size() + "\n");

        // this next block goes on to complete the remaining tasks and tries to optimize finding completed tasks
        if (!pendingFutures.isEmpty()) {

            // this is used to grab items out of the pendingFutures list
            var index = 0;

            // we are going to loop until all the pendingFutures have returned their values
            while (true) {

                // get a pendingFuture based on the index
                var future = pendingFutures.get(index);

                try {

                    /**
                     * try and get the value in the specified amount of time
                     * if the get call doesn't return in time then TimeoutException is thrown
                     */
                    System.out.println(future.get(100, TimeUnit.MILLISECONDS));

                    /**
                     * if we return it, meaning we don't throw a TimeoutException, then remove it from the list
                     * because the thread/callable has completed running and we have successfully returned its value.
                     * It's important to realize the the List will resize itself such that the element after the current
                     * index will now have the same index as the one removed.
                     * Example list = {0,1,2}
                     * if you remove element 1 which has index one
                     * element 2 will now have a new index of 1
                     * This is really handy because it creates an easy way to test the next element in the list
                     */
                    pendingFutures.remove(index);

                } catch (TimeoutException e) {

                    /**
                     *if the pendingFuture wasn't done, increment the index and try the next one.
                     * The reason for moving forward is because the current callable/future
                     * might take an hour and we don't want to get stuck trying that one
                     * when other callables may have already completed.
                    */
                    index++;
                }

                // if we have an empty list then lets break out because there is nothing to process
                if (pendingFutures.isEmpty()) {
                    break;
                }

                /**
                 * if we have gotten to the end of the list lets try another pass by resetting to zero,
                 * and pick up any of the futures that may not have finished the first time we checked.
                 */
                boolean isEndOfListResetToBeginning = index > pendingFutures.size() - 1;

                if (isEndOfListResetToBeginning) {
                    index = 0;
                }


            }
        }

        es.shutdown();
    }

    /**
     * This method creates a bunch of callables to test the code.
     *
     * The Thread.sleep calls are used to demonstrate how quick callables return quickly, and longer
     * tasks, once completed, will also return.
     *
     * @return
     */
    private List<Callable<String>> callables() {

        var callables = new ArrayList<Callable<String>>();

        callables.add(() -> "quick");
        callables.add(() -> "more quick");
        callables.add(() -> "even quicker");
        callables.add(() -> "yes i am");


        callables.add(() -> {

            Thread.sleep(5000);

            return "ready for dinner";
        });


        callables.add(() -> {

            Thread.sleep(150);

            return "yummy";
        });

        callables.add(() -> {

            Thread.sleep(3000);

            return "ready for playtime";
        });

        return callables;
    }
}
