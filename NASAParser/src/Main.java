import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


/**
 * Elliot Fortney
 * Take Home Test 2
 * CSC 285
 *
 * This program is a parser that will calculate rocket corrections given a precise set of rules.
 * The equation given must be placed into a char array in order to be processed. The key values must
 * be in the form of an array allowing for our parser to perform actions on each item.
 * We will need to build a generic manager for our stack data structure, as this will function as
 * the foundation of the parser. We will need methods to pop, push, and examine nodes in order to
 * give our parser instructions.
 * We will then be required to build a stack for operands and a stack for operators, that will contain
 * only their respective objects and be used evaluate the equation.
 * After we split the expression into a character array, we will iterate over it, determining what the
 * char is, and placing it into the correct stack. If we see a ')' , we will call our popevalpush function
 * which will gather all elements that are currently in the stack and perform the appropriate calculations.
 * This will continue until all elements have been parsed and we reach the '#" character at the end of the
 * sequence.
 */

public class Main {

    public static void main(String[] args) {
        PrintWriter print = null;
        try {
            print = new PrintWriter(new File("NasaOutput.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Could not print to file");
            e.printStackTrace();
        }

        // create two stacks, one for operators and one for the operands
        GenericStackManager<Operand> operandStack = new GenericStackManager<>();
        GenericStackManager<Operator> operatorStack = new GenericStackManager<>();

        // char [] to hold the expression value for testing
        char [] expression = {'a','+','(','d','%','(','c','+','A','*','b',')',')','#'};

        // char [] to hold the variables that are in the expression
        char [] variables  = {'a','b','c','d','A'};

        // create an arraylist to store the key value pairs
        ArrayList<Operand> variableValues = new ArrayList<>();
        // create doubles to hold the values for the variables, which function as corrections to rocket flight path
        double[] a = {0,12.3};
        double[] b = {15000,0.0};
        double[] c = {-2000,5.0};
        double[] d = {0,-14.5};
        //this is the current state of the test rocket
        double[] A = {30000,75.2};

        //add the variables to the arraylist
        variableValues.add(new Operand(a,'a'));
        variableValues.add(new Operand(b,'b'));
        variableValues.add(new Operand(c,'c'));
        variableValues.add(new Operand(d,'d'));
        variableValues.add(new Operand(A,'A'));

        // create an array to hold our operators
        char[] operators = {'%','*','+',')','(','#'};

        // adding the operators priorities
        int[] operatorPriorities = {3,2,1,99,-99,-100};

        print.println("Pushing operator #");
        Operator pnode1 = new Operator('#',-100);
        operatorStack.pushNode(pnode1);
        print.println("Parsing a + (d%(c+A*b))");

        int oprior;
        Operand exvalue;
        int i = 0;
        Operand iValue;

        /*
         * While loop running the main logic of the program.
         * It will test each character of the array for against a given set of rules, and call
         * functions accordingly. The goal is to add all operands and operators to the correct stack
         * and then pop evaluate and push them back on. This will allow for us to perform the actual
         * 'parsing' of the expression.
         */
        while(expression[i] != '#') {
            print.println("parsing: " + expression[i]);
            // dealing with operands
            if( ((expression[i] >= 'a') && (expression[i] <= 'z')) ||  ((expression[i] >= 'A') && (expression[i] <= 'Z'))) {
                print.println("Operand object: " + expression[i]);
                iValue = findval(expression[i],variables,variableValues,4, print);
                if(iValue == null){
                    print.println("No value found for " + expression[i]);
                }
                operandStack.pushNode(iValue);
            }
            // dealing with operators
            else {
                print.println("This is an operator: " + expression[i]);
                // if we have the start of parenthesis, assign a low priority and add it to the stack
                if(expression[i] == '(') {
                    print.println("start of parenthetical expression: " + expression[i]);
                    Operator pnodeo = new Operator(expression[i],-99);
                    operatorStack.pushNode(pnodeo);
                }
                // if we have a right parenthesis, we need to pop both stacks and perform an evaluation ya feel?
                else if(expression[i] == ')') {
                    while ( (operatorStack.peekNode()).operator != '(') {
                        popEvalAndPush(operatorStack,operandStack,print);
                    }
                    // pop the node, expression is complete
                    operatorStack.popNode();
                }
                // handling every other operator
                else {
                    oprior = findPriority(expression[i],operators,operatorPriorities, 5, print);
                    print.println("peeking top of operator stack. " + operatorStack.peekNode().getPriority());

                    while(oprior <= operatorStack.peekNode().priority ){
                        popEvalAndPush(operatorStack,operandStack,print);
                    }
                    print.println("Pushing operator " + expression[i] + ", priority: " + oprior);
                    Operator pnodeo = new Operator(expression[i],oprior);
                    operatorStack.pushNode(pnodeo);
                }
            }
            // move to the next character in the array
            i++;
        } // end of while loop

        print.println("The final value is: ");

        // final while loop to perform calculation
        while( (operatorStack.peekNode().operator != '#')){
            popEvalAndPush(operatorStack,operandStack,print);
        }

        // close print writer, end program successfully
        print.close();
        System.exit(1);

    }// end of main


    /**
     * Given two operands,
     * @param operand first operand object
     * @param operator operator
     * @param operand2 second operand object
     * @return
     */
    public static Operand intEval(Operand operand, char operator, Operand operand2, PrintWriter print){
        // numerical values of the operands
        double[] result = {0,0};
        double[] operand1Values;
        double[] operand2Values;

        switch (operator) {
            // if we get a % char, we need to only adjust the flight angle
            case '%':
                operand1Values = operand.getOperand();
                operand2Values = operand2.getOperand();
                // set the first element equal to op1 values since there is no change
                result[0] = operand2Values[0];
                // add op1 and op2 to get a new value for the
                result[1] = operand1Values[1] + operand2Values[1];
                print.println("***EVAL: " + operand.print() + operator + operand2.print());
                print.println("Result: " + "("+ result[0]+ ", " + result[1] + ")" );
                return new Operand(result,'A');
            // if we get a * char, we need to adjust the rocket thrust
            case '*' :
                operand1Values = operand.getOperand();
                operand2Values = operand2.getOperand();
                result[0] = operand1Values[0] + operand2Values[0];
                result[1] = operand1Values[1];
                print.println("***EVAL: " + operand.print() + operator + operand2.print());
                print.println("Result: " + "("+ result[0]+ ", " + result[1] + ")" );
                return new Operand(result,'A');
            // if we get + char, we need to adjust both angle and thrust
            case '+' :
                operand1Values = operand.getOperand();
                operand2Values = operand2.getOperand();
                result[0] = operand1Values[0] + operand2Values[0];
                result[1] = operand1Values[1] + operand2Values[1];
                print.println("***EVAL: " + operand.print() + operator + operand2.print());
                print.println("Result: " + "("+ result[0]+ ", " + result[1] + ")" );
                return new Operand(result,'A');
            // default
            default: System.out.println("bad operator " + operator);
                return null;

        }//end of switch
    }// end of intEval


    /**
     * Finds the values of the character given
     * @param x
     * @param valuesTable
     * @param values
     * @param last
     * @param print
     * @return
     */
    public static Operand findval(char x, char[] valuesTable, ArrayList<Operand> values, int last, PrintWriter print){
        int i;
        Operand vreturn = null;
        for(i = 0;i<=last; i++)
            if(valuesTable[i] == x)
                vreturn = values.get(i);
        print.println("found this char "+ x +",value is " + vreturn.print());

        return vreturn;
    }

    /**
     * Finds the priority of an operator
     * @param x char
     * @param operatorTable operator array
     * @param priorityTable operator priority array
     * @param last position of the last element in the operator tables
     * @return  int vreturn
     */
    public static int findPriority(char x, char[] operatorTable, int[] priorityTable, int last, PrintWriter print){
        int i;
        int vreturn = -99;
        for(i = 0;i<=last; i++)
            if(operatorTable[i]==x){
                vreturn = priorityTable[i];
            }
        print.println("found this char: "+x+" value: " + vreturn);

        return vreturn;
    }

    /**
     * Removes the top nodes from each stack and performs a calculation using intEval.
     * @param x Operator stack
     * @param y Operand stack
     */
    public static void popEvalAndPush(GenericStackManager<Operator> x, GenericStackManager<Operand> y,PrintWriter print){
        Operand a,b,c;
        char operandx;
        operandx =(x.popNode()).getOperator();
        a=y.popNode();
        b=y.popNode();
        System.out.println("in popeval" + b + operandx+ a);
        c = intEval(b,operandx,a,print);
        y.pushNode(c);
    }


}// end of Main
