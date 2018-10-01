package CSC406;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ForSale class. Represents an object that is for sale on the
 * eBay website.
 */
public class ForSale {

    PrintWriter printWriter = new PrintWriter(new File("results.txt"));

    // the String variable storing the description of the object that is being bid on.
    String itemDescription;

    // this int variable stores data about the time the product is finished
    int itemTimeDone;

    // these int variables represent the item number of the object and the bidNumber of the current highest bidder.
    int itemNumber, sold;

    // these are double variables that are representing the price required to purchase instantly, the current bid, the min bid allowed,
    // and the value of the current bid on the item.
    double buyNowPrice, currentBid, ItemMinBid;

    // lock object to ensure bid have total access to functions
    private static Lock lock = new ReentrantLock();

    // constructor for ForSale object
    public ForSale(String itemDescription, double ItemMinBid, double buyNowPrice, int itemNumber, int itemTimeDone) throws FileNotFoundException {
        this.itemDescription = itemDescription;
        this.ItemMinBid = ItemMinBid;
        this.buyNowPrice = buyNowPrice;
        this.itemNumber = itemNumber;
        this.itemTimeDone = itemTimeDone;
        this.sold = 0;
        this.currentBid = 0;
    }

    /**
     * Allows a MakeBid object to attempt to bid on an ebay item. This is the only function that is called by the MakeBid class.
     * @param makeBid {MakeBid} a make bid object.
     */
    private void initiateBidding(MakeBid makeBid) {
        lock.lock();
        if(checkTime(makeBid)) {
            checkBuyNowPrice(makeBid);
            determineBidSuccess(makeBid);
        } else {
            printWriter.println(bidPlacedLateMessage(makeBid));
        }
        printWriter.flush();
        lock.unlock();
    }

    /**
     * Determines if a bid being placed is higher than a bid coming in. If it is, the bid will be updated and the thread released.
     * @param makeBid {MakeBid} a MakeBid object.
     */
    private void determineBidSuccess(MakeBid makeBid) {
        if(makeBid.bidAmount < ItemMinBid || makeBid.bidAmount < currentBid) {
            printWriter.println(biddingFailureMessage(makeBid));
        } else if(makeBid.bidAmount > currentBid) {
            currentBid = makeBid.bidAmount;
            printWriter.println(bidPlacedHigherMessage(makeBid));
        }
    }

    /**
     * Void checking if the buy it now price is met. If so the item is marked as sold and the thread is unlocked.
     * @param makeBid {MakeBid} a MakeBid object
     */
    private void checkBuyNowPrice(MakeBid makeBid) {
         if(makeBid.bidAmount == buyNowPrice) {
             printWriter.println(buyNowSuccessMessage(makeBid));
             sold = 1;
         }
    }


    /**
     * Boolean checking that the current bid is being placed before the ending time.
     * @param makeBid {MakeBid} a MakeBid object
     * @return true if currentTime < itemTimeDone
     */
    private boolean checkTime(MakeBid makeBid) {
        return makeBid.currentTime <= itemTimeDone && makeBid.currentTime >= itemTimeDone - (24 * 60);
    }

    /**
     * Returns a message in the form of a string to a bidder who places a high bid.
     * @param makeBid
     * @return
     */
    private String bidPlacedHigherMessage(MakeBid makeBid) {
        return String.format("Congrats %d, you are currently the highest bidder for item %d with a bid of %d. Watch closely, items move quickly on Ebay!", makeBid.bidNumber, itemNumber, Math.round(makeBid.bidAmount) );
    }

    /**
     * Returns a message in the form of a string to a bidder making a late bid.
     * @param makeBid
     * @return
     */
    private String bidPlacedLateMessage(MakeBid makeBid) {
        return String.format("Sorry %d, bidding is not available on item %d.", makeBid.bidNumber, itemNumber);
    }

    /**
     * Returns a message in the form of a string to the bidder that makes a successful buy it now bid.
     * @param makeBid {MakeBid} a MakeBid object
     * @return {String} success message
     */
    private String buyNowSuccessMessage(MakeBid makeBid) {
        return String.format("Congrats %d, you have purchased %d, %s at a price of %d.", makeBid.bidNumber, itemNumber, itemDescription, Math.round(buyNowPrice));
    }

    /**
     * Returns a failure message in the form of a string to the bidder if the amount being proposed is less than the current bidAmount.
     * @param makeBid {MakeBid} a MakeBid object
     * @return {String} failure message
     */
    private String biddingFailureMessage(MakeBid makeBid) {
        return String.format("Sorry %d, your bid of $ %d was not enough to successfully bid on item number %d", makeBid.bidNumber, Math.round(makeBid.bidAmount), itemNumber);
    }

    /**
     * MakeBid is a static nested class used for making bids on ebay items. Creating a MakeBid object
     * will automatically kick off the run function, which will poll a ForSale item to attempt to outbid
     * or purchase the item.
     */
    public static class MakeBid implements Runnable {

        ForSale forSale;
        int itemNumber, currentTime, bidNumber;
        double bidAmount;


        // constructor for MakeBid
        public MakeBid(ForSale forSale, int itemNumber, int currentTime, int bidNumber, int bidAmount) {
            this.forSale = forSale;
            this.itemNumber = itemNumber;
            this.currentTime = currentTime;
            this.bidNumber = bidNumber;
            this.bidAmount = bidAmount;
        }

        @Override
        public void run() {
            forSale.initiateBidding(this);
            Thread.yield();
        }
    }

} // end of ForSale
