
import java.io.PrintWriter;

/**
 * Boston base employee class. Allows us to sort and manage all employees that are found within the boston location
 */

public class BostonEmp extends Employee implements Comparable{

    //=====VARS=====//

    private double stateTax;
    private double  fedTax;
    private double salary;

    public BostonEmp() {
        this.salary = getSalary();
    }


    public void print(PrintWriter print) {
        print.printf("Base boston employee");
    }

    public void calculateStateTax() {
        this.stateTax = .5 * salary;
    }

    public void calculateFedTax() {
        this.fedTax = .25 * salary;
    }

    public void calculateSalary() {
        return;
    }


    public double getSalary() {
        return 0.0;
    }

    /**
     * compare to method allowing us to sort and employee object based on their salary
     * @param o any other boston employee
     * @return result as a 1 -1 or 0
     */
    public int compareTo(Object o) {
        if(getSalary() > ((BostonEmp)o).getSalary()) {
            return 1;
        }else if(getSalary() < ((BostonEmp)o).getSalary()) {
            return -1;
        }else
            return 0;
    }

    public double getStateTax() {
        return stateTax;
    }

    public void setStateTax(double stateTax) {
        this.stateTax = stateTax;
    }

    public double getFedTax() {
        return fedTax;
    }

    public void setFedTax(double fedTax) {
        this.fedTax = fedTax;
    }
}
