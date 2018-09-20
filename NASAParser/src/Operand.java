/**
 * Defines an operand, which are displayed as an array of numbers
 */
public class Operand {

    protected double[] operand;
    protected char letter;

    public Operand(double[] operand, char letter) {
        this.operand = operand;
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public double[]getOperand(){
        return operand;
    }

    public String print(){
        return String.format("(" + operand[0] +", " + operand[1] + ")" );
    }

}
