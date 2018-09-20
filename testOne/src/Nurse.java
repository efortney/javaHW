import java.io.PrintWriter;

public class Nurse extends BostonEmp{

    //=====VARS=====//
    private String name;
    private String race;
    private String SSN;
    private String type;
    private int special;
    private double stateTax;
    private double premium;
    private double  fedTax;
    private double salary = 65000;

    //=====CONSTRUCTOR=====//
    public Nurse(String name, String race, String SSN, int special, String type) {
        super();
        this.name = name;
        this.race = race ;
        this.SSN  = SSN;
        this.special = special;
        this.type = type;
        calculateFedTax();
        calcPremium(special);
        calculateStateTax();
        calculateSalary();
    }


    //=====METHODS=====//

    /**
     * calculates the premium the nurse earns as a total based on their nurse classification
     * @param special the number of patients that they have
     */
    public void calcPremium(int special) {
        if(special == 1){
            premium = .10;
        }else if (special == 2){
            premium = .15;
        }else
            premium = .20;
    }

    @Override
    public void calculateFedTax() {
        this.fedTax = salary * .25;
    }

    @Override
    // calculate the total salary of the nurse
    public void calculateSalary() {
        this.salary += salary * premium;
    }

    @Override
    // calculate the state tax for MA, since the nurse is based in boston
    public void calculateStateTax() {
        this.stateTax = .5 * salary;
    }

    public void print(PrintWriter print) {
        print.printf("%s %s is %s, SSN is %s, makes %f. His state tax is estimated to be %f, his federal tax is estimated to be %f.\n",type,name,race,SSN,salary,stateTax,fedTax);
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
