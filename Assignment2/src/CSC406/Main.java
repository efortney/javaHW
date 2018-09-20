/**
 *Elliot Fortney
 * Assignment 2
 * CSC 406
 */

package CSC406;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.*;

/**
 * This program is an example of using an executor to execute threads alongside lock and unlock,
 * allowing for the processor to complete a set task before allowing another thread to take over that space in
 * memory.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // instantiate all classes and variables
        PrintWriter printWriter;
        printWriter = new PrintWriter(new File("result.txt"));

        ExecutorService executor = Executors.newFixedThreadPool(9);

        Router router  = new Router(printWriter);

        // create an array of jobs and add all of them
        Router.Job[][] jobs = new Router.Job[3][3];
        jobs[0][0] = new Router.Job(router, "PB", 1, 'D', 60000);
        jobs[0][1] = new Router.Job(router, "PB", 2, 'D', 75000);
        jobs[0][2] = new Router.Job(router, "PB", 3, 'P', 100000);

        jobs[1][0] = new Router.Job(router, "FB", 1, 'P', 30000);
        jobs[1][1] = new Router.Job(router, "FB", 2, 'D', 150000);
        jobs[1][2] = new Router.Job(router, "FB", 3, 'P', 89000);

        jobs[2][0] = new Router.Job(router, "MB", 1, 'P', 200000);
        jobs[2][1] = new Router.Job(router, "MB", 2, 'D', 140000);
        jobs[2][2] = new Router.Job(router, "MB", 3, 'P', 135000);

        // iterate through array and execute the jobs
        for(Router.Job[] jobArray : jobs) {
            for (Router.Job job : jobArray) {
                executor.execute(job);
            }
        }

        // have the router print the data for each branch
        router.printData("PB", printWriter);
        router.printData("FB", printWriter);
        router.printData("MB", printWriter);

        // flush the printwriter, shut down the executor, and end the program.
        printWriter.flush();
        executor.shutdown();

    } // end of main


}// end of program
