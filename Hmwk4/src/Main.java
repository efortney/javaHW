import java.io.*;
import java.util.Scanner;

/**
 * Elliot Fortney
 * Feb 15 2018
 *
 * Part 4 of the student assignment utilizing linked lists as opposed to an array list
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter print = new PrintWriter(new File("output.txt"));          // lets us print data to separate file
        // linked list to store all current students
        LinkManager<Student> students = new LinkManager<>();
        // linked list to store all students that drop the class
        LinkManager<Student> droppedStudents = new LinkManager<>();
        File studentFile = new File("Students.csv");                          // file path and name
        BufferedReader reader = new BufferedReader(new FileReader(studentFile));       // buffered reader object
        createStudents(reader,students);
        System.out.println("!!=========Original Students==========!!");
        printListData(students,print);
        System.out.println("!!============Dropped Students==============!!");           // print some info so the output text is easier to read
        print.println("!!============Dropped Students==============!!");
        deleteStudents(students,"42P4",droppedStudents);
        deleteStudents(students,"45A3",droppedStudents);
        printListData(droppedStudents,print);
        System.out.println("!!=========Add Students==========!!");
        print.println("!!=========Add Students==========!!");
        Student newStudent1 = new Student("67T4","Clouse B",new int[]{80,75,98},102,3.65);
        Student newStudent2 = new Student("45P5","Garrison J",new int[]{75,78,72},39,1.85);
        Student newStudent3 = new Student("98P0","Singer A",new int[]{85,95,99},130,3.89);
        students.addInOrder(newStudent1);
        students.addInOrder(newStudent2);
        students.addInOrder((newStudent3));

        printListData(students,print);

        // end the print writer and close the program
        print.close();
    }

    /*
    Delete a student from the array list.
    params:
    academicClass: arraylist of students
    ID: string, the ID of a student that you want to delete
    droppedStudents: arraylist, places the students into a dropped list if needed
    */
    private static void deleteStudents(LinkManager<Student> academicClass, String ID,LinkManager<Student>droppedStudents) {
        // first iterate through the linked list and get each object ID, if the ID matches the string given, remove it
        for(int i = 0; i < academicClass.getNumber(); i++) {
            Student stud = academicClass.getNode(i);
            // try and find the student, if found remove him from current students and add to dropped students
            try {
                if (stud.getID().equals(ID)) {
                    academicClass.removeNode(i-1);
                    droppedStudents.addNode(stud);
                    System.out.printf("Student %s, %s was successfully removed!\n",stud.name, stud.ID); // let user know its been removed
                    break;      // break out of the loop once the ID is found
                }
            }catch (Error e){
                System.out.println("invalid id!");
                System.exit(1);
            }// end catch
        }// end for loop
    }// end of deleteStudents

    /*
    Creates student objects by reading from a given CSV file.
    First we need to open the file. We will then read the file line by line with the bufferedReader,
    We will split the lines on commas, and then create an array of attributes with string.split()
    We will add all those attributes into the student, and then call addStudent in order to create the student.
    */
    private static void createStudents(BufferedReader reader, LinkManager<Student> students) {

        String line;
        int index = 0;
        Scanner scan;
        // loop through the file and set the student values
        try {
            int[] grades = new int[3];  // array to store the grade values, we know that there are only 3 test scores per student so this will be fine
            while ((line = reader.readLine()) != null) {
                Student student = new Student();
                scan = new Scanner(line);
                scan.useDelimiter(",");
                while (scan.hasNext()) {
                    String data = scan.next();
                    // set the ID on index 0
                    if (index == 0) {
                        student.setID(data);
                        // set the name on index 1
                    } else if (index == 1) {
                        student.setName(data);
                    }
                    // add test scores to the array, we need to parse these for ints since they are in string format
                    else if (index == 2) {
                        grades[0] = Integer.parseInt(data);
                    } else if (index == 3) {
                        grades[1] = Integer.parseInt(data);
                    } else if (index == 4) {
                        grades[2] = Integer.parseInt(data);
                    }
                    // set the students gpa and class rank, data still needs to be parsed
                    else if(index == 5) {
                        student.setHours(Integer.parseInt(data));
                        student.calculateRank(Integer.parseInt(data));
                    } else if(index == 6) {
                        student.setGpa(Float.parseFloat(data));
                    }
                    else
                        System.out.println("Invalid data");
                    index++; // increment the index at the end of the loop
                }
                index = 0;  // reset the index counter
                student.calculateGrade(grades);
                student.calculateLetterGrade(student.getGrade());
                student.calculateGPA();
                addStudent(students, student);
            } // end while

        } catch (IOException e) {
            e.printStackTrace();
        } // end catch

    }// end createStudents

    /**
    Add students to the array list, this method is called by the createStudents method
    params:
    academicClass: an array list defined in main, contains student objects
    student: a student object created from the csv file
    */
    private static void addStudent(LinkManager<Student> academicClass, Student student) throws FileNotFoundException {
        academicClass.addInOrder(student);
    }

    /**
     * Prints details about the students
     * @param students linked list
     * @param print print writer
     */
    public static void printListData(LinkManager<Student> students,PrintWriter print) {
        int num = students.getNumber();
        for(int i = 1; i < num + 1 ; i++){
            students.getNode(i).printData(print);
        }
    }


}