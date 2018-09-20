/*
 * Elliot Fortney
 * 02-15-18
 *
 * This class extends problem2's functionality by allowing student object to calculate their year in school, as well as
 * the total number of hours that the student is taking.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Problem3 {

    public static void main(String[] args) throws FileNotFoundException {
        //=====VARS======//
        PrintWriter print = new PrintWriter(new File("output.txt"));          // lets us print data to separate file
        ArrayList<Student> academicClass = new ArrayList<>();                          // create an array list to store the values of students who are in the class
        ArrayList<Student> droppedStudents = new ArrayList<>();                        // create an array list to store the values of students who have dropped the class
        File studentFile = new File("Students.csv");                          // file path and name
        BufferedReader reader = new BufferedReader(new FileReader(studentFile));       // buffered reader object

        //=====METHOD CALLS=====//
        System.out.println("!!=========PART 2==========!!");
        print.println("!!=========PART 2==========!!");
        createStudents(reader,academicClass);                                           // create students and add them to the array list
        System.out.println("!!============Current Students==============!!");           // print some info so the output text is easier to read
        print.println("!!============Current Students==============!!");
        for(Student student : academicClass) {                                          // print data for each student in the class
            student.printData(print);
        }
        System.out.println("!!============Dropped Students==============!!");           // print some info so the output text is easier to read
        print.println("!!============Dropped Students==============!!");
        deleteStudents(academicClass, "42P4",droppedStudents);                      // delete students from the array list that contains the current class
        deleteStudents(academicClass, "45A3",droppedStudents);

        printClass(droppedStudents,print);

        //=====ADD MORE STUDENTS=====//

        Student newStudent1 = new Student("67T4","Clouse B",new int[]{80,75,98},102,3.65);
        Student newStudent2 = new Student("45P5","Garrison J",new int[]{75,78,72},39,1.85);
        Student newStudent3 = new Student("98P0","Singer A",new int[]{85,95,99},130,3.89);
        // add the new students to the array
        academicClass.addAll(Arrays.asList(newStudent1,newStudent2,newStudent3));   // add all the students into the array as a list

        //======PRINT DATA======//
        System.out.println("!!=========UPDATED CLASS==========!!");
        print.println("!!=========UPDATED CLASS==========!!");
        sortLarge(academicClass); // sort the class using the built in method
        printClass(academicClass,print); // print the class

        // close the print writer and end the program
        print.close();
    }

    /*
    Creates student methods by reading from a given CSV file.
    First we need to open the file. We will then read the file line by line with the bufferedReader,
    We will split the lines on commas, and then create an array of attributes with string.split()
    We will add all those attributes into the student, and then call addStudent in order to create the student.
    ****I prefer to call this in a method rather then doing this in main...is this something I should avoid?*********
    */
    public static void createStudents(BufferedReader reader, ArrayList<Student> students) {

        String line = null;
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

    /*
    Add students to the array list, this method is called by the createStudents method
    params:
    academicClass: an array list defined in main, contains student objects
    student: a student object created from the csv file
    */
    private static void addStudent(ArrayList<Student> academicClass, Student student) throws FileNotFoundException {
        academicClass.add(student);
    }

    /*
    Delete a student from the array list.
    params:
    academicClass: arraylist of students
    ID: string, the ID of a student that you want to delete
    droppedStudents: arraylist, places the students into a dropped list if needed
    */
    private static void deleteStudents(ArrayList<Student> academicClass, String ID,ArrayList<Student>droppedStudents) {
        // first iterate through the array list and get each object ID, if the ID matches the string given, remove it
        for(int i =0; i < academicClass.size(); i++) {
            Student stud = academicClass.get(i);
            // try and find the student, if found remove him from current students and add to dropped students
            try {
                if (stud.getID().equals(ID)) {
                    academicClass.remove(stud);
                    droppedStudents.add(stud);
                    System.out.printf("Student %s, %s was successfully removed!\n",stud.name, stud.ID); // let user know its been removed
                    break;      // break out of the loop once the ID is found
                }
            }catch (Error e){
                System.out.println("invalid id!");
                System.exit(1);
            }// end catch
        }// end for loop
    }// end of deleteStudents

    private static void printClass(ArrayList<Student> students, PrintWriter print) {
        for(Student student: students) {

            student.printData(print);
        }
    }

    /*
    Sort the class based on grade(int). Uses a bubble sort and the compareTo function in student.
    */
    private static void sortLarge(ArrayList<Student> academicClass) {
        Student dumbest;   // marker to hold the smallest grade
        int swap = 1;      // set this var to 1 to make sure that the while loop runs at least one time
        while(swap == 1) {
            swap = 0;      // now set it to zero, perform comparison between students
            for(int i = 0; i < academicClass.size() - 1; i++) {
                switch (academicClass.get(i).compareTo(academicClass.get(i+1))) {
                    // the students are in the right order, do nothing
                    case 1:
                        break;
                    // the student has a lower grade then what is being compared to...
                    case -1:
                        dumbest = academicClass.get(i);
                        academicClass.set(i, academicClass.get(i+1));
                        academicClass.set(i+1, dumbest);
                        swap = 1;
                        break;

                    default:
                        // equal students require no action
                }
            }
        }
    }// end of sortLarge





}