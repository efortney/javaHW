//====== GENERIC STUDENT CLASS MANAGER=====//

import java.util.ArrayList;
import java.util.List;


public class StudentClassManager <T extends Comparable> {

    //======VARS=====//
    protected List<T> myObjects = new ArrayList<T> ();
    protected int counter; // this variable will reference the next available element in the array list

    //======CONSTRUCTOR=====//
    public StudentClassManager() {
        counter = 0;
    }

    /*
     * Adds objects into the arraylist.
     * params:
     *  x: an object of type T, must match the types of all other objects in the array
     */
    public int setValue(T x) {
        myObjects.add(counter ++, x);
        return counter;
    }// end of set value

    public int compareTo(Object T) {
        return 1;
    } // end of compare to

    public T getValue(int i) {
        if (i < counter)
            return myObjects.get(i);
        else
            return myObjects.get(0);
    } // end of get value

    /*
     * Will sort any objects that contain a compareTo method
     */
    public void manageAndSort() {
        T xsave ,ysave, a, b; // set variables
        int isw = 1, xlast = myObjects.size();
        while(isw == 1){
            isw = 0;
            for(int i =0; i < xlast - 2; i++) {
                a = myObjects.get(i);
                b = myObjects.get(i+1);
                // create a switch statement in order to move items into the correct order
                switch(a.compareTo(b)) {
                    case 1:  // the objects are in the right order
                        break;
                    case -1: // the objects are out of order, must be swapped
                        xsave = myObjects.get(i);
                        ysave = myObjects.get(i+1);
                        myObjects.set(i, ysave);
                        myObjects.set(i+1, xsave);
                        isw = 1;
                        break;
                    default:
                        break;
                }
            }
        }
    }// end of manageAndSort

} // end of generic manager class
