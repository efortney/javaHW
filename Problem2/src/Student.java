import java.io.PrintWriter;

public class Student {

    //=====FIELDS=====//
    int grade;              // student's numerical value total grade
    Character letterGrade;     // student's letter grade, based of numerical grade
    String name;            // the name of the student
    String ID;              // the student's unique identifier


    //=====NO ARGS CONSTRUCTOR=====//
    public Student() {

    }

    //=====ARGS CONSTRUCTOR======//
    /*
    call functions in the constructor, calculating the students letter grade and int grade
     */
    public Student(String ID, String name, int[] testScores){
        this.ID = ID;
        this.name = name;
        calculateGrade(testScores);
        calculateLetterGrade(this.grade);
        this.grade = getGrade();
        this.letterGrade = getLetterGrade();
    }

    //=====METHODS=====//

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

    //=====GETTERS====//

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

    //======COMPARABLE AND DATA=====//

    /*
    Allow the user to compare two students based on their grade.
    params:
        student o: another student object
     */
    public int compareTo(Student o) {
        if(getGrade() > o.getGrade()){
            return 1;
        }
        else if(getGrade() < o.getGrade()){
            return -1;
        }
        else
            return 0;
    }

    // get basic data about the student such as their grade, ID, name, and letter grade
    protected void printData(PrintWriter print) {
        System.out.printf("%s has an id of %s, his grade is a(n) %c, a(n) %d%% \n",name,ID,letterGrade,getGrade());
        print.printf("%s has an id of %s, his grade is a(n) %c, a(n) %d%% \n",name,ID,letterGrade,getGrade());
    }

}
