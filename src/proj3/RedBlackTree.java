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
     * Constructs a RedBlackTree with a header node.
     * Accepts a minimum value element to store as the header node of the tree.
     * The header's right node points to the root of the tree.
     *
     * @param element
     */
    public RedBlackTree( E minElement )
    {
        root = new RedBlackNode<>( minElement );
        root.left = root.right = nullNode;
    }

    /**
     * Inserts an element as a new RedBlackNode into the tree.
     *
     * @param item the element to be inserted into the tree.
     */
    public void insert( E item )
    {
        current = parent = gParent = root;
        nullNode.element = item;

        while(current.element != null && current.element.compareTo( item ) != 0 )
        {
            g2Parent = gParent; gParent = parent; parent = current;
            current = item.compareTo( current.element ) < 0 ? current.left : current.right;

            if(current.left != null && current.right != null)
            {
                if( current.left.color == RED && current.right.color == RED )
                    handleReorient( item );
            }
        }

        if( current != nullNode )
            return;
        current = new RedBlackNode<>( item, nullNode, nullNode );

        if( item.compareTo( parent.element ) < 0 )
            parent.left = current;
        else
            parent.right = current;
        handleReorient( item );
    }

    /**
     * Reorients the tree to maintain the color properties of a RedBlackTree. Calls rotation methods to maintain balance
     * properties of the tree.
     *
     * @param item item to reorient the tree around.
     */
    private void handleReorient( E item )
    {
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if( parent.color == RED )   // Have to rotate
        {
            gParent.color = RED;
            if( ( item.compareTo( gParent.element ) < 0 ) != ( item.compareTo( parent.element ) < 0 ) )
                parent = rotate( item, gParent );  // Start dbl rotate
            current = rotate( item, g2Parent );
            current.color = BLACK;
        }
        root.right.color = BLACK; // Make root black
    }

    /**
     * Determines the necessary rotations to apply to the RedBlackTree to maintain balance property.
     *
     * @param item element for comparison
     * @param parent node at which to perform rotation
     * @return the new node after rotation
     */
    private RedBlackNode rotate( Comparable item, RedBlackNode parent )
    {
        if( item.compareTo( parent.element ) < 0 )
            return parent.left = item.compareTo( parent.left.element ) < 0 ?
                    rotateWithLeftChild( parent.left )  :  // LL
                    rotateWithRightChild( parent.left ) ;  // LR
        else
            return parent.right = item.compareTo( parent.right.element ) < 0 ?
                    rotateWithLeftChild( parent.right ) :  // RL
                    rotateWithRightChild( parent.right );  // RR
    }

    /**
     * Rotates the tree with given left child of a node to maintain the balance property.
     *
     * @param k2 the left child of a parent node.
     * @return the new node at the given position.
     */
    private RedBlackNode<E> rotateWithLeftChild( RedBlackNode<E> k2 )
    {
        RedBlackNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * Rotates the tree with the given right child of a node to maintain the balance property.
     *
     * @param k1 the right child of a parent node.
     * @return the new node at the given position.
     */
    private RedBlackNode<E> rotateWithRightChild( RedBlackNode<E> k1 )
    {
        RedBlackNode<E> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    /**
     * Checks if the tree is empty.
     *
     * @return true if the tree is empty, false if the tree is populated.
     */
    public boolean isEmpty()
    {
        return root.right == null;
    }

    /**
     * Checks if the tree contains a given element.
     *
     * @param x the element to search for
     * @return the element found
     */
    public E contains(Partial x)
    {
        return getElement(x);
    }

    /**
     * Retrieves an element if it is contained in the tree.
     *
     * @param x the element to search for
     * @return the element found
     */
    public E retrieveIfItContains(Partial x)
    {
        return getElement(x);
    }

    /**
     * Searches the tree for a given element.
     *
     * @param x the element to search the tree for.
     * @return the found element.
     */
    private E getElement(Partial x)
    {

        nullNode.element = (E)x;
        current = root.right;

        for( ; ; )
        {
            if( x.compareTo( current.element ) < 0 )
                current = current.left;
            else if( x.compareTo( current.element ) > 0 )
                current = current.right;
            else if( current != nullNode )
                return current.element;
            else
                return null;
        }
    }

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
        }
        else
        {
            return root.right.element;
        }

    }

    /**
     * Prints the current root of the tree. If the tree is empty, prints "Empty Tree".
     */
    public void printRoot()
    {
        if(!isEmpty())
        {
            System.out.println(root.right.element);
        }
        else
        {
            System.out.println("Empty Tree");
        }
    }

    /**
     * Prints the tree using an inorder traversal.
     */
    public void printTree()
    {
        printTree(root.right);
    }

    /**
     * Prints the tree using an inorder traversal starting from a given node.
     *
     * @param x the node to print the tree from.
     */
    private void printTree(RedBlackNode x)
    {
        if(x != nullNode)
        {
            printTree(x.left);
            System.out.println(x.getElement().toString());
            printTree(x.right);
        }
    }

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
        }

        /**
         * Constructs a RedBlackNode containing a specified element with null children.
         *
         * @param theElement the element for the RedBlackNode to contain
         */
        RedBlackNode( E theElement )
        {
            this( theElement, null, null );
        }

        /**
         * Constructs a RedBlackNode containing the given element with the specified children.
         *
         * @param element the element for the RedBlackNode to contain
         * @param left the left child of the new RedBlackNode
         * @param right the right child of the new RedBlackNode
         */
        RedBlackNode( E element, RedBlackNode<E> left, RedBlackNode<E> right )
        {
            this.element = element;
            this.left = left;
            this.right = right;
            color = BLACK;
        }

        /**
         * Returns the element contained in this RedBlackNode
         *
         * @return the element contained in the RedBlackNode
         */
        E getElement()
        {
            return element;
        }
    }
}
