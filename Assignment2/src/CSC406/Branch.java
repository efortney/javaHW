package CSC406;

/**
 * Base branch class.
 *
 */
public class Branch {

    // string to hold the char value of the branch
    String branchChar;

    double totalCharsProccessed = 0, totalCost = 0;

    // doubles that will hold the total cost of operations.
    double totalPrintingCost = 0, totalDataConnectionCost = 0,
            totalDataCharsRequested = 0, totalPrintCharsRequested = 0, printValueCost = 0, dataConnectionValueCost = 0;

    // constructor
    Branch(String branchChar, double printValue, double dataConnectionValue ) {
        this.branchChar = branchChar;
        this.printValueCost = printValue;
        this.dataConnectionValueCost = dataConnectionValue;
    }

} // end of Branch class
