/**
 * Operator object
 */

public class Operator {

    //=====Attributes=====//
    protected  int priority;
    protected char operator;

    //=====CONSTRUCTOR=====//
    public Operator(char operator, int priority){
        this.operator = operator;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public char getOperator() {
        return operator;
    }



}
