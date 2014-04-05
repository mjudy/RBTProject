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
    private RedBlackNode<E> root = nullNode;

    public RedBlackTree()
    {
        root.left = null;
        root.right = null;
        root.parent = null;
    }

    public boolean isEmpty()
    {
        return root == nullNode;
    }

    public boolean isNull(RedBlackNode n)
    {
        return n == nullNode;
    }

    public boolean contains(Partial x)
    {
        return contains(x, root);
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
//                System.out.println(n.element);
                return (E)n.element;
            }
            getElement(x, n.right);
        }
        return null;
    }

    public void printRoot()
    {
        if(!isEmpty())
        {
            System.out.println(root.element.toString());
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

    public void insert(E element)
    {
        insert(new RedBlackNode<>(element));
    }

    private void insert(RedBlackNode<E> node)
    {
        RedBlackNode<E> previous = nullNode;
        RedBlackNode<E> current = root;

        while (!isNull(current))
        {
            previous = current;
            if (node.element.compareTo(current.element) < 0)
            {
                current.numLeft++;
                current = current.left;
            }
            else
            {
                current.numRight++;
                current = current.right;
            }
        }

        node.parent = previous;

        if(isNull(previous))
        {
            root = node;
        }
        else if(node.element.compareTo(previous.element) < 0)
        {
            previous.left = node;
        }
        else
        {
            previous.right = node;
        }

        node.left = nullNode;
        node.right = nullNode;
        node.color = RED;

        insertFix(node);
    }

    private void insertFix(RedBlackNode<E> node)
    {
        RedBlackNode<E> y;

        while(node.parent.color == RED)
        {
            if(node.parent == node.parent.parent.left)
            {
                y = node.parent.parent.right;

                if (y.color == RED)
                {
                    node.parent.color = BLACK;
                    y.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                }
                else if(node == node.parent.right)
                {
                    node = node.parent;
                    rotateLeft(node);
                }
                else
                {
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            }
            else
            {
                y = node.parent.parent.left;
                if(y.color == RED)
                {
                    node.parent.color = BLACK;
                    y.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                }
                else if(node == node.parent.left)
                {
                    node = node.parent;
                    rotateRight(node);
                }
                else
                {
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
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
