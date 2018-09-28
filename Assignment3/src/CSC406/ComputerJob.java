package CSC406;

/**
 * Computer Job class. This class will hold data for both deletion and insertion jobs
 * by using method overloading in its constructor.
 */
public class ComputerJob {

    String computerID;
    int amt;
    int value;

    /**
     * Constructor for ComputerJob class
     *
     * @param computerID     {String} the ID of the computer job
     * @param amt         {int} the count of integers being stored or deleted
     * @param value {int}
     */
    public ComputerJob(String computerID, int amt, int value) {
        this.computerID = computerID;
        this.amt = amt;
        this.value = value;
    }

    /**
     * Constructor for ComputerJob class
     *
     * @param computerID {String} the ID of the computer job
     * @param count {int} the count of integers being stored or deleted
     */
    public ComputerJob(String computerID, int count) {
        this.computerID = computerID;
        this.amt = count;
    }

}
