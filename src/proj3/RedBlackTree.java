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

    public boolean isEmpty()
    {
        return root.right == null;
    }

    public E contains(Partial x)
    {
        return getElement(x);
    }

    public E retrieveIfItContains(Partial x)
    {
        return getElement(x);
    }

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

    public void printTree()
    {
        printTree(root.right);
    }

    private void printTree(RedBlackNode x)
    {
        if(x != nullNode)
        {
            printTree(x.left);
            System.out.println(x.getElement().toString());
            printTree(x.right);
        }
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

        E getElement()
        {
            return element;
        }
    }
}
