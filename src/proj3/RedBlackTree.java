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
    private RedBlackNode<E> root = null;

    public RedBlackTree()
    {
        root.left = null;
        root.right = null;
        root.parent = null;
    }

    public boolean isNull(RedBlackNode x)
    {
        return x == null;
    }

    public void insert(E element)
    {
        insert(new RedBlackNode<E>(element));
    }

    private void insert(RedBlackNode<E> z)
    {
        RedBlackNode<E> y = nullNode;
        RedBlackNode<E> x = root;

        while (!isNull(x))
        {
            y = x;
            if (z.element.compareTo(x.element) < 0)
            {
                x.numLeft++;
                x = x.left;
            }
            else
            {
                x.numRight++;
                x = x.right;
            }
        }

        z.parent = y;

        if(isNull(y))
        {
            root = z;
        }
        else if(z.element.compareTo(y.element) < 0)
        {
            y.left = z;
        }
        else
        {
            y.right = z;
        }

        z.left = nullNode;
        z.right = nullNode;
        z.color = RED;

        insertFix(z);
    }

    private void insertFix(RedBlackNode<E> z)
    {
        RedBlackNode<E> y;

        while(z.parent.color == RED)
        {
            if(z.parent == z.parent.parent.left)
            {
                y = z.parent.parent.right;

                if (y.color == RED)
                {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                }
                else if(z == z.parent.right)
                {
                    z = z.parent;
                    rotateLeft(z);
                }
                else
                {
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateRight(z.parent.parent);
                }
            }
            else
            {
                y = z.parent.parent.left;
                if(y.color == RED)
                {
                    z.parent.color = BLACK;
                    y.color = BLACK;
                    z.parent.parent.color = RED;
                    z = z.parent.parent;
                }
                else if(z == z.parent.left)
                {
                    z = z.parent;
                    rotateRight(z);
                }
                else
                {
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }

        root.color = BLACK;
    }

    private void rotateLeft(RedBlackNode<E> x)
    {
        rotateLeftFix(x);

        RedBlackNode<E> y;
        y = x.right;
        x.right = y.left;

        if (!isNull(y.left))
        {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (isNull(x.parent))
        {
            root = y;
        }
        else if(x.parent.left == x)
        {
            x.parent.left = y;
        }
        else
        {
            x.parent.right = y;
        }

        x.left = x;
        x.parent = y;
    }

    private void rotateRight(RedBlackNode<E> y)
    {
        rotateRightFix(y);

        RedBlackNode<E> x = y.left;
        y.left = x.right;

        if(!isNull(x.right))
        {
            x.right.parent = y;
        }
        x.parent = y.parent;

        if(isNull(y.parent))
        {
            root = x;
        }
        else if(y.parent.right == y)
        {
            y.parent.right = x;
        }
        else
        {
            y.parent.left = x;
        }

        x.right = y;
        y.parent = x;
    }

    private void rotateLeftFix(RedBlackNode x)
    {
        if (isNull(x.left) && isNull(x.right.left))
        {
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        }
        else if(isNull(x.left) && !isNull(x.right.left))
        {
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft + x.right.left.numRight;
        }
        else if (!isNull(x.left) && isNull(x.right.left))
        {
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;

        }
        else
        {
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight + x.right.left.numLeft + x.right.left.numRight;
        }
    }

    private void rotateRightFix(RedBlackNode y)
    {
        if (isNull(y.right) && isNull(y.left.right)){
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        }
        else if (isNull(y.right) && !isNull(y.left.right)){
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight + y.left.right.numLeft;
        }
        else if (!isNull(y.right) && isNull(y.left.right)){
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight +y.right.numLeft;

        }
        else{
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight + y.right.numLeft + y.left.right.numRight + y.left.right.numLeft;
        }
    }

    private class RedBlackNode<E extends Comparable<? super E>>
    {
        E element;

        RedBlackNode<E> parent;
        RedBlackNode<E> left;
        RedBlackNode<E> right;

        int numLeft = 0, numRight = 0, color;

        RedBlackNode()
        {
            color = BLACK;
            numLeft = 0;
            numRight = 0;
            parent = null;
            left = null;
            right = null;
        }

        RedBlackNode(E element)
        {
            this();
            this.element = element;
        }
    }
}
