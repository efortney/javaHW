import java.io.PrintWriter;
import java.text.DecimalFormat;

// STUDENT OBJECT FOR PART 2

public class Student implements Comparable {

    //=====FIELDS=====//
    protected int grade;              // student's numerical value total grade
    protected Character letterGrade;  // student's letter grade, based of numerical grade
    protected String name;            // the name of the student
    protected String ID;              // the student's unique identifier
    protected String rank;            // the classification of the student
    protected double gpa;              // the gpa of the student
    protected int totalHours;              // the total hours the student has taken
    DecimalFormat f = new DecimalFormat("##.00"); // rounds to 2 decimal places

    //=====NO ARGS CONSTRUCTOR=====//
    public Student() {

    }

    //=====ARGS CONSTRUCTOR======//

    /*
     *call functions in the constructor, calculating the students letter grade and int grade
     */
    public Student(String ID, String name, int[] testScores, int hours, double gpa){
        calculateRank(hours);
        calculateGrade(testScores);
        calculateLetterGrade(this.grade);
        this.ID = ID;
        this.name = name;
        this.totalHours = hours;
        this.grade = getGrade();
        this.letterGrade = getLetterGrade();
        calculateGPA();
    }

    //=====METHODS=====//

    /*
     * Calculates the students overall GPA based on the grade that they currently have in their class.
     * Hours are assumed to be @ 2 each, ans GP's are assumed to be standard.
     *
     */
    public void calculateGPA() {
        // GPA = (gpa * totalHrs) + (2*GP) / (totalHrs+2)
        gpa = ((gpa + totalHours) + (2 * findGP())) / totalHours + 2 ;
    }

    // find the grade point earned for a class, using the letter grade attribute
    private int findGP() {
        int GP;
        switch (letterGrade) {
            case 'A': GP = 4;
            break;
            case 'B': GP =  3;
            break;
            case 'C': GP = 2;
            break;
            case 'D': GP = 1;
            break;
            default: GP = 0;
            break;
        }
        return GP;
    }

    /*
    Calculates the students rank based on the number of hours they have taken.
    Params:
        hours, int: the number of hours the student has taken to date
     */
    public void calculateRank(int hours) {
        if(hours <= 30) {
            rank = "FR";
        }
        else if(hours <= 60) {
            rank = "SO";
        }
        else if(hours <= 90) {
            rank = "JR";
        }
        else {
            rank = "SR";
        }

    }

    /*
    calculates the student's grade based off of all individual scores.
    This method assumes all tests are based out of 100 total points
    params:
        scores: an array of ints representing the student's scores on each test
    */
    public void calculateGrade(int[] scores) {
        int grade;
        grade = 0;
        // add each test score together
        for(int testScore : scores) {
            grade += testScore;
        }

        this.grade = grade / scores.length ;  //set the grade % by taking to total and dividing it by the number of tests taken.
    }



    /*
    determines the student's letter grade from A - F where is A represents the highest scores possible and F is the lowest
    params:
        grade: int, the numerical score of the student
     */
    public void calculateLetterGrade(int grade) {
        Character letterGrade;
        //set the letter grade to an F, adjust based on the score
        this.letterGrade = 'F';
        if(grade >= 90){
            this.letterGrade = 'A';
        }
        else if(grade >= 80) {
            this.letterGrade = 'B';
        }
        else if(grade >= 70) {
            this.letterGrade = 'C';
        }
        else if(grade >= 60) {
            this.letterGrade = 'D';
        }
        else
            this.letterGrade = 'F';

    }

    //=====SETTERS=====//


    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLetterGrade(Character letterGrade) {
        this.letterGrade = letterGrade;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public void setHours(int hours) {
        this.totalHours = hours;
    }

    //=====GETTERS====//


    public double getGpa() {
        return gpa;
    }

    public int getHours() {
        return totalHours;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public Character getLetterGrade() {
        return letterGrade;
    }

    public String getID() {
        return ID;
    }

    public String getRank() {
        return rank;
    }


    //======COMPARABLE AND DATA=====//

    /*
    Allow the user to compare two students based on their grade.
    params:
        student o: another student object
     */
    public int compareTo(Object o) {
        if(getGrade() > ((Student)o).getGrade()){
            return 1;
        }
        else if(getGrade() < ((Student)o).getGrade()){
            return -1;
        }
        else
            return 0;
    }

    // get basic data about the student such as their grade, ID, name, gpa, and letter grade
    protected void printData(PrintWriter print) {
        System.out.printf("%s has an id of %s, his grade is a(n) %c, a(n) %d%%. %s's overall gpa is %s. He is ranked as a %s, with %d hours.\n",name,ID,letterGrade,getGrade(),name,f.format(gpa),rank,totalHours);
        print.printf("%s has an id of %s, his grade is a(n) %c, a(n) %d%%. %s's overall gpa is %s. He is ranked as a %s, with %d hours.\n",name,ID,letterGrade,getGrade(),name,f.format(gpa),rank,totalHours);
    }


}
