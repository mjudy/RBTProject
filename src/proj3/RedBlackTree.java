package proj3;

/**
 * @author theghv
 * @version 1.0 Date: 4/4/14 Time: 5:33 PM
 */
public class RedBlackTree<E extends Comparable<? super E>>
{
    final int BLACK = 1;
    final int RED = 0;
    private RedBlackNode<E> nullNode = new RedBlackNode<>();
    private RedBlackNode<E> root = new RedBlackNode<>(null);
    private RedBlackNode<E> current;
    private RedBlackNode<E> parent;
    private RedBlackNode<E> grand;
    private RedBlackNode<E> great;

    public RedBlackTree( E element )
    {
        root = new RedBlackNode<>( element );
        root.left = root.right = nullNode;
    }

    public void insert( E item )
    {
        current = parent = grand = root;
        nullNode.element = item;

        while(current.element != null && current.element.compareTo( item ) != 0 )
        {
            great = grand; grand = parent; parent = current;
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

    private void handleReorient( E item )
    {
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;

        if( parent.color == RED )   // Have to rotate
        {
            grand.color = RED;
            if( ( item.compareTo( grand.element ) < 0 ) != ( item.compareTo( parent.element ) < 0 ) )
                parent = rotate( item, grand );  // Start dbl rotate
            current = rotate( item, great );
            current.color = BLACK;
        }
        root.right.color = BLACK; // Make root black
    }

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

    private RedBlackNode<E> rotateWithLeftChild( RedBlackNode<E> k2 )
    {
        RedBlackNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    private RedBlackNode<E> rotateWithRightChild( RedBlackNode<E> k1 )
    {
        RedBlackNode<E> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    private class RedBlackNode<E extends Comparable<? super E>>
    {
        E element;
        RedBlackNode<E> left;
        RedBlackNode<E> right;
        private int color;
        
        RedBlackNode()
        {
            this (null, null, null);
        }
        
        RedBlackNode( E theElement )
        {
            this( theElement, null, null );
        }

        RedBlackNode( E theElement, RedBlackNode<E> lt, RedBlackNode<E> rt )
        {
            element = theElement;
            left = lt;
            right = rt;
            color = BLACK;
        }
    }

    public boolean isEmpty()
    {
        return root.right == nullNode;
    }

    public boolean contains(Partial x)
    {
        return root != nullNode && contains(x, root);
    }

    private boolean contains(Partial x, RedBlackNode n)
    {
        if (n != nullNode)
        {
            contains(x, n.left);
            if(x.compareTo(n.element) == 0)
            {
                return true;
            }
            contains(x, n.right);
        }
        return false;
    }

    public E getElement(Partial x)
    {
        return getElement(x, root);
    }

    private E getElement(Partial x, RedBlackNode n)
    {
        System.out.println(n.element);
        if(n != nullNode)
        {
            getElement(x, n.left);
            if (x.compareTo(n.element) == 0)
            {
                return (E)n.element;
            }
            getElement(x, n.right);
        }
        return null;
    }

    public E getRoot()
    {
        if (!isEmpty())
        {
            return null;
        }
        else
        {
            return root.element;
        }

    }

    public void printRoot()
    {
        if(!isEmpty())
        {
            System.out.println(root.element);
        }
        else
        {
            System.out.println("Empty Tree");
        }
    }

    public void printTree()
    {
        printTree(root);
    }

    private void printTree(RedBlackNode x)
    {
        if(x != nullNode)
        {
            printTree(x.left);
            System.out.println(x.element);
            printTree(x.right);
        }
    }
}
