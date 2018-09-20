package CSC406;


import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Router class responsible for tracking information about each branch
 * and handling threads.
 */
public class Router {

    PrintWriter printWriter;

    // create three branches for data reference
    private Branch productionBranch = new Branch("PB", 0.007, 0.008);
    private Branch financialBranch = new Branch("FB", 0.009, 0.007);
    private Branch marketingBranch = new Branch("MB", 0.0095, 0.0082);

    // instantiate the lock
    private static Lock lock = new ReentrantLock();

    // constructor
    Router(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    // initialize requests
    void startRequests(String branchChar, int port, char dataType, int totalCharsRequested) {
        lock.lock();
        Branch branch = setBranch(branchChar);
        calculateDataChars(branch, dataType, totalCharsRequested);
        lock.unlock();
    }


    /**
     * Determines the branch that is being referenced.
     * @param branchChar the key value for the branch.
     * @return Branch
     */
    private Branch setBranch(String branchChar) {
        switch (branchChar) {
            case "PB":
                return  productionBranch;
            case "FB":
                return financialBranch;
            case "MB":
                return marketingBranch;
            default:
                return null;
        }
    }

    /**
     * Calculates the cost of the characters and the number processed.
     * @param branch {Branch} the branch to process
     * @param dataType {char} the data type to process
     * @param totalCharsRequested {double} number of chars being requested
     */
    private void calculateDataChars(Branch branch, char dataType, double totalCharsRequested) {
        double totalCost;
        if(dataType == 'D') {
            branch.totalDataCharsRequested += totalCharsRequested;
            totalCost = branch.totalDataConnectionCost += (totalCharsRequested * branch.dataConnectionValueCost) ;
        } else {
            branch.totalPrintCharsRequested += totalCharsRequested;
            totalCost = branch.totalPrintingCost += (totalCharsRequested * branch.printValueCost);
        }

        branch.totalCharsProccessed += totalCharsRequested;
        branch.totalCost += totalCost;
    }

    // prints data about a branch
    void printData(String branchCode, PrintWriter printWriter) {
        Branch branch = setBranch(branchCode);
        printWriter.println("Data for " + branch.branchChar);
        printWriter.println("data chars processed " + branch.totalDataCharsRequested);
        printWriter.println("data chars total cost " + branch.totalDataConnectionCost);
        printWriter.println("print chars processed " + branch.totalPrintCharsRequested);
        printWriter.println("print chars total cost " + branch.totalPrintingCost);
        printWriter.println("total cost for the branch " + branch.totalCost );
        printWriter.println("total number of chars processed " + branch.totalCharsProccessed);
        printWriter.println();
    }

    /*
    Static job class for handling request threads. Nested class 
     */
    public static class Job implements Runnable {
        Router router;
        String branchCode;
        int port;
        char dataType;
        int totalCharsRequested;

        // constructor
        public Job(Router router, String branchCode, int port, char dataType, int totalCharsRequested) {
            this.router = router;
            this.branchCode = branchCode;
            this.port = port;
            this.dataType = dataType;
            this.totalCharsRequested = totalCharsRequested;
        }

        @Override
        public void run() {
            router.startRequests(branchCode, port, dataType, totalCharsRequested);
        }
    }

}
