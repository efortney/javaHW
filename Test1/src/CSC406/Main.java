package CSC406;

/**
 * Elliot Fortney
 * Test 1, Threading
 * CSC 406
 * Professor Pickett
 * Oct 1 2018
 */


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program represents an online bidding system that utilizes threads in order to perform bidding.
 * Threads are created each time a MakeBid object, representing a bid, is placed. This bid takes control
 * of the system, locking the thread and releasing the processor once the system has determined the result
 * of the bid. The MakeBid object is a nested class that implements runnable, sitting inside of the ForSale
 * class. This class is the primary component of the system, and allows for the bidding object to perform
 * comparisons on the ForSale object. The functions that are nested in ForSale serve as an API of sorts, for
 * efficiency the only function that is called by the MakeBid object is initiateBidding, which takes the bidding
 * object as an argument. This function proceeds to call all other functions, allowing for a streamlined result.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // object being bid on
        ForSale painting = new ForSale("Painting", 5000, 60000, 4827, 17050 + (24 * 60));

        // instantiate executor
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        // create bids and add them to an array
        ForSale.MakeBid bid1 = new ForSale.MakeBid(painting, 4827, 18481, 5283, 4000);
        ForSale.MakeBid bid2 = new ForSale.MakeBid(painting, 4827, 18482, 4681, 15000);
        ForSale.MakeBid bid3 = new ForSale.MakeBid(painting, 4827, 18483, 5283, 14500);
        ForSale.MakeBid bid4 = new ForSale.MakeBid(painting, 4827, 18485, 5283, 17500);
        ForSale.MakeBid bid5 = new ForSale.MakeBid(painting, 4827, 18489, 4681, 25000);
        ForSale.MakeBid bid6 = new ForSale.MakeBid(painting, 4827, 18495, 5283, 32000);

        ArrayList<ForSale.MakeBid> bids = new ArrayList<>(Arrays.asList(bid1, bid2, bid3, bid4, bid5, bid6));

        // execute the bids
        for(ForSale.MakeBid bid : bids) {
            executorService.execute(bid);
        }

        // shutdown the executor
        executorService.shutdown();
    }
} // end of program
