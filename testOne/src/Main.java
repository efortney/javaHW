
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Elliot Fortney
 * Feb 21 - 18
 *
 * Take home portion of test 1.
 * This program stores employees of different types and sorts them based on their salary.
 * It then takes an arraylist of numbers and sorts them in order to prove that the GenericManager class is indeed generic.
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter print = new PrintWriter(new File("output.txt"));

        // create our employee objects
        Doctor doc1 = new Doctor("IM Bones","AA","455657890",100);
        Administrative admin1 = new Administrative("IM Boss","HS","543126787",1,"Senior Exec");
        Nurse nurse1 = new Nurse("UR Temp", "CA","789302345",3,"Adm Nurse");
        Doctor doc2 = new Doctor("DVM Jordan","CA","786456712",120);

        // create an array list to store all employees
        List<BostonEmp> bostonEmps = new ArrayList<>();
        // create an instance of our generic manager
        GenericManager<BostonEmp> bostonManager = new GenericManager<>();
        // add all employees to our array list and then proceed to calculate their taxes and salary
        bostonEmps.addAll(Arrays.asList(doc1,admin1,nurse1,doc2));

        // loop through employees and add them to the manager
        for (BostonEmp emp: bostonEmps) {
            bostonManager.setValue(emp);

        }
        print.println("!!==========UNSORTED EMPLOYEES==========!!");
        printDataBoston(bostonManager,print);
        // call the generic manager to sort our employees
        bostonManager.manageAndSort();
        print.println("!!==========SORTED EMPLOYEES==========!!");
        printDataBoston(bostonManager,print);

        print.println("!!==========NUMBERS TEST==========!!");
        /*
         * here we will add all doubles into an array, then transfer them to our generic manager, where we will then sort them
         */
        List<Double> doublesBefore = new ArrayList<>();
        doublesBefore.addAll(Arrays.asList(2.34,5.45,1.23,17.81,12.91));
        GenericManager<Double> doubles = new GenericManager<>();
        for(Double num : doublesBefore) {
            doubles.setValue(num);
        }
        doubles.manageAndSort();
        printDataDoubles(doubles,print);


        print.close();
    }

    /**
     * Print the data for the numbers test
     * @param nums doubles
     * @param print printwriter
     */
    private static void printDataDoubles(GenericManager<Double> nums, PrintWriter print) {
        for(int i = 0; i < nums.counter; i ++) {
            print.println(nums.getValue(i));
        }
    }

    /**
     * Print the data in the Generic manager to the user if given boston employee objects
     * @param emps boston employees
     */
    private static void printDataBoston(GenericManager<BostonEmp> emps,PrintWriter print) {
        for(int i = 0; i < emps.counter; i++) {
            emps.getValue(i).print(print);
        }
    }

}
