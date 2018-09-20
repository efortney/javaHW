import java.io.PrintWriter;

/**
 * Generic object manager for Binary trees, This class is of type T, and extends the comparable interface
 *
 * @param <T>
 */
public class BinaryTreeMgr<T extends Comparable> {

    //the root of the tree
    protected TreeNode<T> root;

    // the # of nodes in the tree
    protected int number;

    protected T nodeValue;
    protected TreeNode<T> left;
    protected TreeNode<T> right;
    //this holds the found node
    protected TreeNode<T> foundNode;
    // holds left child node
    protected TreeNode<T> foundLeftChild;
    // holds right child
    protected TreeNode<T> foundRightChild;
    // holds the parent of the found node
    protected TreeNode<T> foundNodeParent;
    // holds the value of the current node
    protected TreeNode<T> currentNode;
    // holds the left current value
    protected TreeNode<T> currentLeft;
    // hold the right current value
    protected TreeNode<T> currentRight;
    // holds the parent tree node
    protected TreeNode<T> parentNode;

    //=====CONSTRUCTOR=====//
    public BinaryTreeMgr() {
        root = null;
        int number = 0;
    }

    // Get minimum element in binary search tree
    public static TreeNode getMin(TreeNode root) {
       while (root.left != null){
           return root;
       }
       return root;
    }

    /**
     * Deletes a node using a recursive solution
     * @param root the root of the binary tree
     * @param value the value that needs to be deleted. Found with findNode
     * @return root value
     */
    public static TreeNode deleteNode(TreeNode root, TreeNode value) {
        if (root == null)
            return null;
        if (root.nodevalue.compareTo(value.nodevalue) > 0) {
            root.left = deleteNode(root.left, value);
        } else if (root.nodevalue.compareTo(value.nodevalue) < 0) {
            root.right = deleteNode(root.right, value);

        } else {
            // if nodeToBeDeleted have both children
            if (root.left != null && root.right != null) {
                TreeNode temp = root;
                // Finding minimum element from right
                TreeNode minNodeForRight = getMin(temp.right);
                // Replacing current node with minimum node from right subtree
                root.nodevalue = minNodeForRight.nodevalue;
                // Deleting minimum node from right now
                root.right = deleteNode(root.right, minNodeForRight);

            }
            // if nodeToBeDeleted has only left child
            else if (root.left != null) {
                root = root.left;
            }
            // if nodeToBeDeleted has only right child
            else if (root.right != null) {
                root = root.right;
            }
            // if nodeToBeDeleted do not have child (Leaf node)
            else
                root = null;
        }
        return root;
    }

    public void printParent() {
        if (foundNodeParent == null) {
            System.out.println("parent null");
        } else {
            System.out.println("parent: " + foundNodeParent.nodevalue);
        }
    }

    public void printLeft() {
        if (foundLeftChild == null) {
            System.out.println("left null");
        } else {
            System.out.println("left child: " + foundLeftChild.nodevalue);
        }
    }

    public void printRight() {
        if (foundRightChild == null) {
            System.out.println("right null");
        } else {
            System.out.println("right child: " + foundRightChild.nodevalue);
        }
    }


    /**
     * Finds a node given a value x, that is of type T
     *
     * @param x type T
     * @return Nsearch
     */
    public int findNode(T x) {
        // flag for finding a node with the requested value
        int Nsearch = 0;
        System.out.println("Searching for: " + x);

        // this means that there are no nodes in the tree
        if (number == 0) {
            Nsearch = 0;
            return Nsearch;
        }

        // we have nodes, checking the root first
        else {
            if (x.compareTo(root.nodevalue) == 0) {
                System.out.println("checking root node, value of: " + root.nodevalue);
                Nsearch = 2;
                foundNodeParent = parentNode = root;
                foundLeftChild = root.left;
                foundRightChild = root.right;
                return Nsearch;
            }
            // not the root node, must chain down tree
            else {
                parentNode = root;
                currentNode = root;
                currentLeft = root.left;
                currentRight = root.right;
                System.out.println("findnode not root, node right");
            }

            while (x.compareTo(currentNode.nodevalue) != 0) {
                System.out.println("we are searching currentnodevalue: " + currentNode.nodevalue + ", value of x: " + x);
                if (x.compareTo(currentNode.nodevalue) > 0) {
                    foundNodeParent = parentNode = currentNode;
                    System.out.println("Chaining right " + currentNode.nodevalue);

                    if (currentNode.right == null) {
                        Nsearch = 0;
                        System.out.println("currentNode.right is null");
                        return Nsearch;
                    }
                    currentNode = currentNode.right;
                    System.out.println("We move right: " + currentNode.nodevalue);
                } else {
                    foundNodeParent = parentNode = currentNode;
                    if (currentNode.left == null) {
                        Nsearch = 0;
                        return Nsearch;
                    }
                    currentNode = currentNode.left;
                }
                System.out.println("at the end of chaining, if current nodevalue " + currentNode.nodevalue + " , value of x: " + x);
            }
            System.out.println("parent node is: " + foundNodeParent);
            foundLeftChild = currentNode.left;
            foundRightChild = currentNode.right;
            foundNode = currentNode;
            Nsearch = 1;
        }
        return Nsearch;
    }


    public int getNumber() {
        return number;
    }

    /**
     * Creates a node within the tree
     *
     * @param x
     * @return
     */
    protected TreeNode<T> createNode(T x) {
        return new TreeNode(x);
    }

    /**
     * Inserts a node value into the tree
     *
     * @param x type T object
     * @return number of nodes in the tree
     */
    public int insertNode(T x) {
        if (root == null) {
            root = createNode(x);
            number = 1;
            return number;
        } else {
            TreeNode<T> parent = null;
            TreeNode<T> current = root;

            while (current != null)
                // this value is less than the current
                if (x.compareTo(current.nodevalue) < 0) {
                    parent = current;
                    current = current.left;
                } else {
                    if (x.compareTo(current.nodevalue) > 0) {
                        parent = current;
                        current = current.right;
                    } else {
                        return -99;
                    }
                }
            if (x.compareTo(parent.nodevalue) < 0) {
                parent.left = createNode(x);
            } else {
                parent.right = createNode(x);
            }
        }
        number++;
        return number;
    }

    /**
     * moves through the tree and prints out each node in order from the left branch, then the right, and finally the root
     *
     * @param print
     */
    public void postOrder(PrintWriter print) {
        postOrder(root, print);
    }

    protected void postOrder(TreeNode<T> root, PrintWriter print) {
        if (root == null) return;

        postOrder(root.left, print);
        postOrder(root.right, print);
        print.println(root.nodevalue + " ");
    }

    /**
     * moves through the tree and prints out each node starting at the root, and then moving to the left, then right
     *
     * @param print
     */
    public void preOrder(PrintWriter print) {
        preOrder(root, print);
    }

    protected void preOrder(TreeNode<T> root, PrintWriter print) {
        if (root == null) return;

        print.println(root.nodevalue + " ");
        preOrder(root.left, print);
        preOrder(root.right, print);
    }

    /**
     * moves through the tree and prints out each node in order from smallest to largest
     *
     * @param print
     */
    public void inorder(PrintWriter print) {
        inorder(root, print);
    }

    protected void inorder(TreeNode<T> root, PrintWriter print) {
        if (root == null) return;

        inorder(root.left, print);
        print.println(root.nodevalue + " ");
        inorder(root.right, print);
    }

    /**
     * Treenode class allows for the construction of individual tree nodes
     */
    private static class TreeNode<T extends Comparable> {
        protected T nodevalue;
        protected TreeNode<T> left;
        protected TreeNode<T> right;

        //=====CONSTRUCTOR=====/
        public TreeNode(T x) {
            nodevalue = x;
            left = null;
            right = null;
        }
    }


} // end of binary tree manager
