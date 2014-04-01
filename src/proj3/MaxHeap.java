package proj3;

/**
 * @author theghv
 * @version 1.0
 *  Date: 4/1/14
 *  Time: 1:51 AM
 */
public class MaxHeap <E extends Comparable<? super E>>
{
    private static int DEFAULT_CAPACITY = 100;
    private int currentSize;
    private Comparable[] array;

    public MaxHeap()
    {
        this (DEFAULT_CAPACITY);
    }

    public MaxHeap(int capacity)
    {
        currentSize = 0;
        array = new Comparable[capacity + 1];
    }

    public void insert(E x) throws Overflow
    {
        if (isFull())
        {
            throw new Overflow();
        }

        int hole = ++currentSize;
        for ( ; hole > 1 && x.compareTo(array[hole/2]) < 0; hole /= 2)
        {
            array[hole] = array [hole/2];
        }

        array[hole] = x;
    }

    public Comparable findMax()
    {
        if(isEmpty())
        {
            return null;
        }
        return array[1];
    }

    public boolean isEmpty()
    {
        return currentSize == array.length -1;
    }

    public boolean isFull()
    {
        return currentSize == array.length;
    }

    private void buildHeap()
    {
        for (int i = currentSize / 2; i > 0; i--)
        {
            percolateDown(i);
        }
    }

    private void percolateDown(int hole)
    {
        int child;
        Comparable temp = array[hole];
        for ( ; hole * 2 <= currentSize; hole = child)
        {
            child = hole * 2;
            if (child != currentSize && array[child+1].compareTo(array[child]) < 0)
            {
                child++;
            }
            if (array[child].compareTo(temp) < 0)
            {
                array[hole] = array [child];
            }
            else
            {
                break;
            }
        }
        array[hole] = temp;
    }
}
