/**
 * Generic Stack manager. Handles all stacks.
 */

import java.util.ArrayList;

public class GenericStackManager <T> {

    protected ArrayList<T> myStack;
    protected int number;

    //=====CONSTRUCTOR=====//
    public GenericStackManager(){
        number = 0;
        myStack = new ArrayList<>(100);
    }

    //=====GETTER=====//
    public int getNumber(){return number;}

    /**
     * Pushes a node to the top of the stack
     * @param x the object to place on the stack
     * @return the value of the node position
     */
    public int pushNode(T x){
        System.out.println("in pushnode " + number +" x is " + x);
        myStack.add(number, x);
        number++;
        System.out.println("leaving pushnode");
        return number;
    }

    /**
     * Removes the top node from the stack
     * @return nodeval {value of the node}
     */
    public T popNode(){
        T nodeval;
        nodeval = myStack.get(number - 1);
        myStack.remove(number -1);
        number--;
        return nodeval;
    }

    /**
     * Gets the value of the node that  is behind the top node
     * @return nodeval
     */
    public T peekNode(){
        T nodeval;
        nodeval = myStack.get(number-1);
        return nodeval;
    }

    /**
     * Checks if the stack is empty
     * @return true, false
     */
    boolean stackEmpty(){
        if(number ==0)
            return true;
        else
            return false;}

}
