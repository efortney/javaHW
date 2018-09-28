package CSC406;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Buffer {
    // locks
    private static Lock lock = new ReentrantLock();
    private static Condition infoStorage = lock.newCondition();

    // int array to store the data being requested from the computer
    private int dStore[] = new int[30];

    // string array storing the name of the computer requesting access
    String[] cStore = new String[30];

    private int fill; // int to store the current fill of the array

    private PrintWriter printWriter;

    public Buffer(PrintWriter printWriter) {
        this.printWriter = printWriter;
        fill = 0;
    }

    public void printData(boolean deletion, PrintWriter printWriter) {
        if (deletion)
            printWriter.println("DELETION ---Printing storage data for the dStore array, followed by the cStore array");
        else
            printWriter.println("INSERTION---Printing storage data for the dStore array, followed by the cStore array");
        for (int i : dStore) {
            printWriter.printf("%5s", i);
        }
        printWriter.println();
        for (String j : cStore) {
            printWriter.printf("%5s", j);
        }
        printWriter.println();
        printWriter.println();
        printWriter.println();
        printWriter.flush();
    }

    /**
     * Inserts the integers into the dStore array and String value computerID into the cStore array.
     *
     * @param computerID {String} the identifier for the computer
     * @param amt        {int} the count of the integers to add to dStore and cStore.
     * @param value      {int} the integer to be inserted
     */
    void insertData(String computerID, int amt, int value, PrintWriter printWriter) {
        lock.lock(); // acquire thread lock
        try {
            while (fill + amt - 1 > 29) {
                infoStorage.await();
            }
            for (int i = fill; i <= fill + amt - 1; i++) {
                dStore[i] = value;
                cStore[i] = computerID;
                System.out.flush();
            }
            fill = fill + amt;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            printData(false, printWriter);
            infoStorage.signalAll();
            lock.unlock();
        }
    }

    /**
     * Deletes data from both the dStore and the cStore arrays.
     *
     * @param id {String} the identifier of the computer.
     * @param amt {int} the count of the integers to delete from dStore and cStore.
     * @param printWriter {Printwriter} a printwriter for documentation.
     */
    void deleteData(String id, int amt, PrintWriter printWriter) {
        lock.lock(); // lock the thread
        int deleteCount = 1; // int to count the number of deletions occurring
        try {
            while (fill < 1) {
                infoStorage.await();
            }
            // loop through the cStore and dStore arrays, removing the values that are being deleted
            for (int i = 0; i <= 29; i++) {
                // if null, continue looping
                if (cStore[i] == null) {
                    i++;
                    // we have a match,
                } else if (cStore[i].equalsIgnoreCase(id) && deleteCount <= amt) {
                    deleteCount++;
                    // replace the values with the values of the slot above them one at a time
                    for(int j = i; j < dStore.length - 1; j++) {
                        dStore[j] = dStore[j + 1];
                        cStore[j] = cStore[j + 1];
                    }
                } else {
                    i++;
                }
            }
            fill = fill - amt;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // print the data and unlock the thread
            printData(true, printWriter);
            infoStorage.signalAll();
            lock.unlock();
        }
    }

    /**
     * Storage thread, used for inserting values into the cStore and dStore arrays.
     */
    public static class Store implements Runnable {

        Buffer bufferX;
        PrintWriter printWriter;

        // constructor
        Store(Buffer bufferX, PrintWriter printWriter) {
            this.bufferX = bufferX;
            this.printWriter = printWriter;
        }

        // runnable thread
        @Override
        public void run() {
            ArrayList<ComputerJob> jobs = new ArrayList<>();
            // create jobs and add them as a list
            ComputerJob pb1 = new ComputerJob("PB1", 5, -3);
            ComputerJob fb2 = new ComputerJob("FB1", 6, 78);
            ComputerJob pb1_2 = new ComputerJob("PB1", 8, 13);
            ComputerJob mb3 = new ComputerJob("MB3", 10, 22);
            ComputerJob fb4 = new ComputerJob("FB4", 6, 75);
            jobs.addAll(Arrays.asList(pb1, fb2, pb1_2, mb3, fb4));
            // loop through the jobs and add them to the list
            for (ComputerJob job : jobs) {
                bufferX.insertData(job.computerID, job.amt, job.value, printWriter);
            }
            Thread.yield();
        }
    }

    /**
     * Deletion thread. Used for removing values from the dStore and the cStore arrays.
     */
    public static class Delete implements Runnable {
        Buffer bufferX;
        PrintWriter printWriter;

        Delete(Buffer bufferX, PrintWriter printWriter) {
            this.bufferX = bufferX;
            this.printWriter = printWriter;
        }

        @Override
        public void run() {
            ArrayList<ComputerJob> jobs = new ArrayList<>();
            ComputerJob fb1 = new ComputerJob("FB1", 2);
            ComputerJob pb1 = new ComputerJob("PB1", 4);
            ComputerJob mb3 = new ComputerJob("MB3", 4);
            ComputerJob pb1_2 = new ComputerJob("PB1", 3);
            jobs.addAll(Arrays.asList(fb1, pb1, mb3, pb1_2));
            for (ComputerJob job : jobs) {
                bufferX.deleteData(job.computerID, job.amt, printWriter);
            }
            Thread.yield();
        }
    }

} // end of StorageThread class
