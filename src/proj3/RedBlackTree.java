package proj3;

/**
 * @author Mark Judy <mjudy1@umbc.edu>
 * @version 1.0
 * Date: 4/4/14
 * Time: 5:33 PM
 */
public class RedBlackTree<E extends Comparable<? super E>>
{
    final int BLACK = 1;
    final int RED = 0;
    private RedBlackNode<E> nullNode = new RedBlackNode<>();
    private RedBlackNode<E> root = new RedBlackNode<>(null);
    private RedBlackNode<E> current;
    private RedBlackNode<E> parent;
    private RedBlackNode<E> gParent;
    private RedBlackNode<E> g2Parent;

    /**
     * Constructs a RedBlackTree with a header node. Accepts a minimum value element to store as the header node of the
     * tree. The header's right node points to the root of the tree.
     *
     * @param minElement minimum value of key data type to generate the head pointer node of the tree.
     */
    public RedBlackTree(E minElement)
    {
        root = new RedBlackNode<>(minElement);
        root.left = root.right = nullNode;
    }//end RedBlackTree(minElement)

    /**
     * Inserts an element as a new RedBlackNode into the tree.
     *
     * @param item the element to be inserted into the tree.
     */
    public void insert(E item)
    {
        current = parent = gParent = root;
        nullNode.element = item;

        while(current.element != null && current.element.compareTo(item) != 0)
        {
            g2Parent = gParent; gParent = parent; parent = current;
            current = item.compareTo(current.element) < 0 ? current.left : current.right;

            if(current.left != null && current.right != null)
            {
                if(current.left.color == RED && current.right.color == RED)
                {
                    handleReorient(item);
                }//end if
            }//end if
        }//end while

        if(current != nullNode)
        {
            return;
        }//end if

        current = new RedBlackNode<>(item, nullNode, nullNode);

        if( item.compareTo(parent.element) < 0 )
        {
            parent.left = current;
        }//end if
        else
        {
            parent.right = current;
        }//end else

        handleReorient(item);
    }//end insert(element)

    /**
     * Reorients the tree to maintain the color properties of a RedBlackTree. Calls rotation methods to maintain balance
     * properties of the tree.
     *
     * @param item item to reorient the tree around.
     */
    private void handleReorient(E item)
    {
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if(parent.color == RED)   // Have to rotate
        {
            gParent.color = RED;
            if((item.compareTo(gParent.element) < 0 ) != (item.compareTo(parent.element) < 0 ))
            {
                parent = rotate(item, gParent);  // Do double rotation
            }//end if
            current = rotate(item, g2Parent);
            current.color = BLACK;
        }//end if

        root.right.color = BLACK; // Make root black
    }//end handleReorient(item)

    /**
     * Determines the necessary rotations to apply to the RedBlackTree to maintain balance property.
     *
     * @param item element for comparison
     * @param parent node at which to perform rotation
     * @return the new node after rotation
     */
    private RedBlackNode rotate(Comparable item, RedBlackNode parent)
    {
        if(item.compareTo(parent.element) < 0)
            return parent.left = item.compareTo(parent.left.element) < 0 ?
                    rotateWithLeftChild(parent.left)  :  // Left-Left rotation
                    rotateWithRightChild(parent.left) ;  // Left-Right rotation
        else
            return parent.right = item.compareTo(parent.right.element) < 0 ?
                    rotateWithLeftChild(parent.right) :  // Right-Left rotation
                    rotateWithRightChild(parent.right);  // Right-Right rotation
    }//end rotate(item, parent)

    /**
     * Rotates the tree with given left child of a node to maintain the balance property.
     *
     * @param node1 the left child of a parent node.
     * @return the new node at the given position.
     */
    private RedBlackNode<E> rotateWithLeftChild(RedBlackNode<E> node1)
    {
        RedBlackNode<E> node2 = node1.left;
        node1.left = node2.right;
        node2.right = node1;
        return node2;
    }//end rotateWithLeftChild(node1)

    /**
     * Rotates the tree with the given right child of a node to maintain the balance property.
     *
     * @param node1 the right child of a parent node.
     * @return the new node at the given position.
     */
    private RedBlackNode<E> rotateWithRightChild(RedBlackNode<E> node1)
    {
        RedBlackNode<E> node2 = node1.right;
        node1.right = node2.left;
        node2.left = node1;
        return node2;
    }//end rotateWithRightChild

    /**
     * Checks if the tree is empty.
     *
     * @return true if the tree is empty, false if the tree is populated.
     */
    public boolean isEmpty()
    {
        return root.right == null;
    }//end isEmpty()

    /**
     * Checks if the tree contains a given element.
     *
     * @param item the element to search for
     * @return the element found
     */
    public boolean contains(E item)
    {
        return getElement(item) != null;
    }//end contains(item)

    /**
     * Retrieves an element if it is contained in the tree.
     *
     * @param item the element to search for
     * @return the element found
     */
    public E retrieveIfItContains(E item)
    {
        return getElement(item);
    }//end retrieveIfItContains(item)

    /**
     * Searches the tree for a given element.
     *
     * @param item the element to search the tree for.
     * @return the found element.
     */
    private E getElement(E item)
    {
        nullNode.element = item;
        current = root.right;

        while(current.element != null)
        {
            if(item.compareTo(current.element) < 0)
                current = current.left;
            else if(item.compareTo(current.element) > 0)
                current = current.right;
            else if(current != nullNode)
                return current.element;
            else
                return null;
        }//end while
        return null;
    }//end getElement(item)

    /**
     * Retrieves the current root of the tree.
     *
     * @return the element contained in the tree's root.
     */
    public E getRoot()
    {
        if (isEmpty())
        {
            return null;
        }//end if
        else
        {
            return root.right.element;
        }//end else
    }//end getRoot()

    /**
     * Prints the current root of the tree. If the tree is empty, prints "Empty Tree".
     */
    public void printRoot()
    {
        if(!isEmpty())
        {
            String outStr = root.right.element.toString();
            outStr = "This tree starts with " + outStr.substring(0, 17) + " --> The heap contains:" + outStr.substring(17);
            System.out.print(outStr);
        }//end if
        else
        {
            System.out.println("Empty Tree");
        }//end else
    }//end printRoot()

    /**
     * Prints the tree using an in-order traversal.
     */
    public void printTree()
    {
        printTree(root.right);
    }//end printTree()

    /**
     * Prints the tree using an in-order traversal starting from a given node.
     *
     * @param node the node to print the tree from.
     */
    private void printTree(RedBlackNode node)
    {
        if(node != nullNode)
        {
            printTree(node.left);
            String outStr = node.element.toString();
            outStr = "This tree starts with " + outStr.substring(0, 17) + " --> The heap contains:" + outStr.substring(17);
            System.out.println(outStr);
            printTree(node.right);
        }//end if
    }//end printTree(node)

    /**
     * Helper class that defines the attributes of a RedBlackNode for the RedBlackTree.
     *
     * @param <E> the type parameter of the RedBlackNode
     */
    private class RedBlackNode<E extends Comparable<? super E>>
    {
        E element;
        RedBlackNode<E> left;
        RedBlackNode<E> right;
        private int color;

        /**
         * Constructs a default RedBlackNode containing null, with null children.
         */
        RedBlackNode()
        {
            this (null, null, null);
        }//end RedBlackNode()

        /**
         * Constructs a RedBlackNode containing a specified element with null children.
         *
         * @param element the element for the RedBlackNode to contain
         */
        RedBlackNode(E element)
        {
            this(element, null, null);
        }//end RedBlackNode(element)

        /**
         * Constructs a RedBlackNode containing the given element with the specified children.
         *
         * @param element the element for the RedBlackNode to contain
         * @param left the left child of the new RedBlackNode
         * @param right the right child of the new RedBlackNode
         */
        RedBlackNode(E element, RedBlackNode<E> left, RedBlackNode<E> right)
        {
            this.element = element;
            this.left = left;
            this.right = right;
            color = BLACK;
        }//end RedBlackNode(element, left, right)
    }//end RedBlackNode
}//end RedBlackTree
