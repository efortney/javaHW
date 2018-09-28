/**
 * Elliot Fortney
 * CSC 406
 * Assignment 3
 * Sep 25 2018
 */

package CSC406;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program is an example of multi threading with threads that
 * are both in sync and coordinated. There is two threads, one for deletion
 * and one for insertion. The insertion thread relies on the deletion thread in order
 * to push elements into the array, as if there is not enough space in the array, the
 * deletion thread needs to take over and remove elements from the array.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        //Printwriter
        PrintWriter printWriter = new PrintWriter(new File("results.txt"));

        // buffer class
        Buffer buffer = new Buffer(printWriter);

        // a list to store the jobs before being added to the run function
        ArrayList jobs = new ArrayList<ComputerJob>();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // computer jobs
        ComputerJob pb1 = new ComputerJob("PB1", 5, -3);
        jobs.add(pb1);


        Buffer.Store store = new Buffer.Store(buffer, printWriter);
        Buffer.Delete delete = new Buffer.Delete(buffer, printWriter);

        executor.execute(store);
        executor.execute(delete);
        executor.shutdown();

    } // end of main method

} // end of class Main
