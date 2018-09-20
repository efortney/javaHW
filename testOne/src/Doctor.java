import java.io.PrintWriter;

/**
 * Doctor employee class, extending the boston location
 */
public class Doctor extends BostonEmp{

       //=====VARS=====//
    private String name;
    private String race;
    private String SSN;
    private int special;
    private double stateTax;
    private double premium;
    private double  fedTax;
    private double salary = 155000;

    //=====CONSTRUCTOR=====//
    public Doctor(String name, String race, String SSN, int special) {
        super();
        this.name = name;
        this.race = race ;
        this.SSN  = SSN;
        this.special = special;
        calculateFedTax();
        calcPremium(special);
        calculateStateTax();
        calculateSalary();
    }


    //=====METHODS=====//

    /**
     * calculates the premium the doctor earns as a total based on a .025% increase per patient to their overall salary
     * @param special the number of patients that they have
     */
    public void calcPremium(int special) {
        premium += (salary * .025) * special;
    }

    @Override
    public void calculateFedTax() {
        this.fedTax = salary * .25;
    }

    // calculate the total salary of the doctor
    public void calculateSalary() {
        this.salary += premium;
    }

    @Override
    // calculate the state tax for MA, since the doctor is based in boston
    public void calculateStateTax() {
        this.stateTax = .05 * salary;
    }


    public void print(PrintWriter print) {
        print.printf("Doctor %s is %s, SSN is %s, makes %f. His state tax is estimated to be %f, his federal tax is estimated to be %f.\n",name,race,SSN,salary,stateTax,fedTax);
    }

    //=====GETTERS AND SETTERS=====//

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getFedTax() {
        return fedTax;
    }

    public double getPremium() {
        return premium;
    }

    @Override
    public double getSalary() {
        return salary;
    }

    public double getStateTax() {
        return stateTax;
    }

    public String getRace() {
        return race;
    }

    public String getSSN() {
        return SSN;
    }

    public int getSpecial() {
        return special;
    }

}
