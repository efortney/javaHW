import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Elliot Fortney
 * CSC 285
 * Binary Trees
 *
 * This program demonstrates the use of node deletion in Binary tree transversal.
 * There are three types of objects being added to the binary tree:
 * integers, doubles, and characters.
 * They are added to the tree, the tree is transversed, and we spit out the results to the print writer.
 *
 */

public class BinaryTree {


    public static void main(String[] args) throws FileNotFoundException {

        PrintWriter print;
        // print the results to this file
        print = new PrintWriter( new File("bTree.txt"));



        // create a manager for integers
        BinaryTreeMgr<Integer> BtreeInt = new BinaryTreeMgr<Integer>();
        BtreeInt.insertNode(78);
        BtreeInt.insertNode(39);
        BtreeInt.insertNode(52);
        BtreeInt.insertNode(28);
        BtreeInt.insertNode(33);
        BtreeInt.insertNode(14);
        BtreeInt.insertNode(16);
        BtreeInt.insertNode(4);
        BtreeInt.insertNode(35);
        BtreeInt.insertNode(105);
        BtreeInt.insertNode(85);
        BtreeInt.insertNode(92);
        BtreeInt.insertNode(80);
        BtreeInt.insertNode(97);
        BtreeInt.insertNode(110);
        BtreeInt.insertNode(99);

        print.println("Printing in order");
        BtreeInt.inorder(print);
        print.println("------------------");
        print.println("Printing in pre order");
        BtreeInt.preOrder(print);
        print.println("------------------");
        print.println("Printing in post order");
        BtreeInt.postOrder(print);
        print.println("------------------");
        // delete node 28
        BtreeInt.findNode(28);
        print.println("Deleting node 28");
        BinaryTreeMgr.deleteNode(BtreeInt.root,BtreeInt.foundNode );
        print.println("Printing in order");
        BtreeInt.inorder(print);
        print.println("------------------");
        print.println("Printing in pre order");
        BtreeInt.preOrder(print);
        print.println("------------------");
        print.println("Printing in post order");
        BtreeInt.postOrder(print);
        print.println("------------------");
        print.println("Deleting node 105");
        // delete node 105
        BtreeInt.findNode(105);
        BinaryTreeMgr.deleteNode(BtreeInt.root,BtreeInt.foundNode);
        print.println("Printing in order");
        BtreeInt.inorder(print);
        print.println("------------------");
        print.println("Printing in pre order");
        BtreeInt.preOrder(print);
        print.println("------------------");
        print.println("Printing in post order");
        BtreeInt.postOrder(print);
        print.println("------------------");
        // delete node 110
        BtreeInt.findNode(110);
        BinaryTreeMgr.deleteNode(BtreeInt.root,BtreeInt.foundNode);
        print.println("Deleting node 110");
        print.println("Printing in order");
        BtreeInt.inorder(print);
        print.println("------------------");
        print.println("Printing in pre order");
        BtreeInt.preOrder(print);
        print.println("------------------");
        print.println("Printing in post order");
        BtreeInt.postOrder(print);
        print.println("------------------");

        // close the print writer and end the program
        print.close();
        System.exit(0);
    } // end of main


} // end of Binary Tree
