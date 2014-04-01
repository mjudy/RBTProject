package proj3;

/**
 * @author theghv
 * @version 1.0 Date: 4/1/14 Time: 1:52 AM
 */
public class RedBlackTree <E extends Comparable<? super E>>
{
    private static final int BLACK = 1;
    private static final int RED = 0;
    
    private RedBlackNode<E> root;
    private RedBlackNode<E> nullNode;
    
    private RedBlackNode<E> current;
    private RedBlackNode<E> parent;
    private RedBlackNode<E> gParent;
    private RedBlackNode<E> g2Parent;
    
    public RedBlackTree()
    {
        nullNode = new RedBlackNode<E>(null);
        nullNode.left = nullNode.right = nullNode;
        root = new RedBlackNode<E>(null);
        root.left = root.right = nullNode;
    }
    
    private RedBlackNode<E> rotate (E element, RedBlackNode<E> parent)
    {
        if (compare(element, parent) < 0)
        {
            return parent.left = compare(element, parent.left) < 0 ?
                rotateWithLeftChild(parent.left) :
                rotateWithRightChild(parent.left);        
        }
        else
        {
            return parent.right = compare(element, parent.right) < 0 ?
                rotateWithLeftChild(parent.right) :
                rotateWithRightChild(parent.right);
        }
    }
    
    private int compare(E element, RedBlackNode<E> node)
    {
        if(node == root)
        {
            return 1;
        }
        else
        {
            return element.compareTo(node.element);
        }
    }
    
    private void handleReorient(E element)
    {
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;
        
        if (parent.color == RED)
        {
            gParent.color = RED;
            if ((compare(element, gParent) < 0) != (compare(element, parent) < 0))
            {
                parent = rotate(element, gParent);                
            }
            current = rotate(element, gParent);
            current.color = BLACK;
        }
        root.right.color = BLACK;
    }
    
    public void insert(E element)
    {
        current = parent = gParent = root;
        nullNode.element = element;

        while (compare(element, current) != 0)
        {
            g2Parent = gParent;
            gParent = parent;
            parent = current;

            current = compare(element, current) < 0  ? current.left : current.right;
            if (current.left.color == RED && current.right.color == RED)
            {
                handleReorient(element);
            }
        }

        if (current != nullNode)
        {
            return;
        }
        current = new RedBlackNode<E>(element, nullNode, nullNode);

        if(compare(element, parent) < 0)
        {
            parent.left = current;
        }
        else
        {
            parent.right = current;
        }
        handleReorient(element);
    }

    public boolean isEmpty()
    {
        return root.element == null;
    }

    public void printTree()
    {
        if(isEmpty())
        {
            System.out.println("Empty Tree");
        }
        else
        {
            printTree(root);
        }
    }

    private void printTree(RedBlackNode<E> node)
    {
        if (node != nullNode)
        {
            printTree(node.left);
            System.out.println(node.element);
            printTree(node.right);
        }
    }

    private void printRoot()
    {
        if (isEmpty())
        {
            System.out.println("Empty Tree");
        }
        else
        {
            System.out.println(root.element);
        }
    }

    private RedBlackNode<E> rotateWithLeftChild (RedBlackNode<E> n2)
    {
        RedBlackNode<E> n1 = n2.left;
        n2.left = n1.right;
        n1.right = n2;
        return n1;
    }

    private RedBlackNode<E> rotateWithRightChild (RedBlackNode<E> n2)
    {
        RedBlackNode<E> n1 = n2.right;
        n2.right = n1.left;
        n1.left = n2;
        return n1;
    }

    private static class RedBlackNode<E>
    {
        E element;
        RedBlackNode<E> left;
        RedBlackNode<E> right;
        int color;
        
        RedBlackNode(E element)
        {
            this (element, null, null);
        }
        
        RedBlackNode(E element, RedBlackNode<E> left, RedBlackNode<E> right)
        {
            this.element = element;
            this.left = left;
            this.right = right;
            color = BLACK;
        }
    }
}
