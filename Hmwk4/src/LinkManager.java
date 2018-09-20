import java.io.PrintWriter;

/**
 * Linked list manager to hold and manage objects
 * Extends the comparable interface
 * Sorts objects based on their value from largest to smallest
 * @param <T>: object of any type
 */

public class LinkManager <T extends Comparable> {

    // head and tail, aka first and last position of the linked list
    protected Node<T> head, tail;

    protected  int number;

    //=====CONSTRUCTOR=====//
    public LinkManager() {
        LinkManager<T> head = null;
        LinkManager<T> tail = null;
        int number = 0;
    }// end of constructor

    // getter for number
    public int getNumber() {
        return number;
    }

    /**
     * Removes node at given position
     * @param position {int}
     */
     void removeNode(int position) {
        // If linked list is empty
        if (head == null)
            return;

        // Store head node
        Node temp = head;

        // If head needs to be removed
        if (position == 0)
        {
            head = temp.next;   // Change head
            return;
        }

        // Find previous node of the node to be deleted
        for (int i=0; temp!=null && i<position-1; i++)
            temp = temp.next;

        // If position is more than number of ndoes
        if (temp == null || temp.next == null)
            return;

        // Node temp->next is the node to be deleted
        // Store pointer to the next of node to be deleted
        temp.next = temp.next.next;

        number --;
    }

    /**
     * Adds a node to a linked list
     * @param x, any object or type T
     * @return number, the postion of the node
     */
    public int addNode(T x) {
        addFront(x);
        return number;
    }

    /**
     * Adds a node to the front of the linked list
     * @param x any object
     */
    public void addFront(T x) {
        Node<T> newnode = new Node<T>(x); // create a new node
        if(head == null) {
            head = newnode;
            tail = newnode;
        }else {
            newnode.next = head;
            head = newnode;
        }
        number ++;
        return;
    }

    /**
     * Returns the node at position x, as requested by user
     * @param x any object
     * @return position of node x
     */
    public T getNode(int x) {
        if((x<0) || (x>number)) {
            System.out.printf("error in getNode %d while list holds %d\n", x, number);
        }
            int ict = 1;
            Node<T> currentNode;
            currentNode = head;
            while(ict < x) {
                currentNode = currentNode.next;
                ict++;
            }
            return currentNode.nodeValue;
    } // end of getNode

    /**
     * Adds all nodes in the order of their size
     * @param x any object
     */
    public void addInOrder(T x) {
        Node<T> newNode = new Node<T>(x);
        Node<T> cnode, nnode;
        if(number == 0) {
            head = newNode;
            tail = newNode;
            number = 1;
            return;
        }
        if(x.compareTo(head.nodeValue) == 1) {
            newNode.next = head;
            head = newNode;
            number ++;
            return;
        }
        if(x.compareTo(tail.nodeValue)==-1) {
            tail.next = newNode;
            tail = newNode;
            number ++;
            return;
        }
        cnode = head;
        nnode = head.next;
        while(x.compareTo(nnode.nodeValue) != 1) {
            cnode = nnode;
            nnode = nnode.next;
        }

        cnode.next = newNode;
        newNode.next = nnode;
        number ++;
        return;
    } // end of addInOrder

    /**
     * Internal class for building nodes within the node manager
     * @param <T> any object
     */
    private static class Node<T> {
        protected T nodeValue;
        protected Node<T> next;
        public Node(T x) {
            nodeValue = x;
            next = null;
        }
    } // end of node class



} // end of Link Manager


