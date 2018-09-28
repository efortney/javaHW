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
 * This program is an example of multi threading with ... //TODO
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        //Printwriter
        PrintWriter printWriter = new PrintWriter(new File("test.txt"));

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
