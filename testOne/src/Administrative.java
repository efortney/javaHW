import java.io.PrintWriter;

/**
 * Administrative employee class, extends boston location employee class
 */
public class Administrative extends BostonEmp {

    //=====VARS=====//
    private String name;
    private String race;
    private String SSN;
    private String type;
    private int special;
    private double stateTax;
    private double premium;
    private double  fedTax;
    private double salary;

    //=====CONSTRUCTOR=====//
    public Administrative(String name, String race, String SSN, int special, String type) {
        this.name = name;
        this.race = race ;
        this.SSN  = SSN;
        this.special = special;
        this.type = type;
        getAdminType(type);
        calcPremium(special);
        calculateFedTax();
        calculateStateTax();
        calculateSalary();
    }


    //=====METHODS=====//

    /**
     * calculates the premium the Administrator earns as a total based on their bonus type
     * @param special the type of bonus they receive
     */
    public void calcPremium(int special) {
        double bonus = 0;
        if(special == 1) {
            bonus = .20;
        }else if(special == 2){
            bonus = .10;
        }
        premium = salary * bonus;
    }

    @Override
    public void calculateFedTax() {
        this.fedTax = salary * .25;
    }

    /**
     * determines the administrator type of the employee in order to determine salary and bonuses
     * @param type {String}, the type of the employee
     */
    public void getAdminType(String type) {
        switch (type){
            case "Senior Exec":
                salary = 400000;
                break;
            case "Junior Exec":
                salary = 175000;
                break;
            case "Support":
                salary = 40000;
                break;
            default:
                salary = 40000;
                break;
        }
    }

    @Override
    // calculate the total salary of the admin
    public void calculateSalary() {
        this.salary += premium;
    }

    // calculate the state tax for MA, since the admin is based in boston
    public void calculateStateTax() {
        this.stateTax = .05 * salary;
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
