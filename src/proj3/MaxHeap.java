package proj3;

import java.util.Arrays;

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
    private E[] array;

    public MaxHeap()
    {
        this (DEFAULT_CAPACITY);
    }

    public MaxHeap(int capacity)
    {
        currentSize = 0;
        array = (E[]) new Comparable[capacity];
    }

    public void insert(E x)
    {
//        System.out.println("Sup");
        int index;
        if (isFull())
        {
            array = this.resize();
        }

        if(isEmpty())
        {
            index = currentSize;
            array[index] = x;
        }
        else
        {
            currentSize++;
            index = currentSize;
            for (; index > 0 && x.compareTo(array[index/2]) > 0; index /= 2)
            {
                array[index] = array[index/2];
            }
            array[index] = x;
        }
    }

    public Comparable findMax()
    {
        if(isEmpty())
        {
            return null;
        }
        return array[0];
    }

    public boolean isEmpty()
    {
        return array[0] == null;
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

    public void printImmediateOptions()
    {
        for(int i = 0; i < 3; i++)
        {
            System.out.println(array[i]);
        }
    }

    private void percolateDown(int index)
    {
        int child;
        E temp = array[index];
        for ( ; index * 2 <= currentSize; index = child)
        {
            child = index * 2;
            if (child != currentSize && array[child+1].compareTo(array[child]) < 0)
            {
                child++;
            }
            if (array[child].compareTo(temp) < 0)
            {
                array[index] = array [child];
            }
            else
            {
                break;
            }
        }
        array[index] = temp;
    }

    private E[] resize()
    {
        return Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public String toString()
    {
        String str = "\n";
        for (int i = 0; i < currentSize; i++)
        {
            str += "[" + (i + 1) + "]" + array[i].toString() + "\n";
        }
        return str;
    }
}
