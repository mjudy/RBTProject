package proj3;

import java.util.Arrays;

/**
 * @author Mark Judy <mjudy1@umbc.edu>
 * @version 1.0
 *  Date: 4/1/14
 *  Time: 1:51 AM
 */
public class MaxHeap <E extends Comparable<? super E>>
{
    private static int DEFAULT_CAPACITY = 15;
    private int currentSize;
    private E[] array;

    /**
     * Creates a MaxHeap with a default capacity.
     */
    public MaxHeap()
    {
        this (DEFAULT_CAPACITY);
    }//end MaxHeap()

    /**
     * Creates a MaxHeap with the specified capacity.
     *
     * @param capacity the size of the heap to be created
     */
    public MaxHeap(int capacity)
    {
        currentSize = 0;
        array = (E[]) new Comparable[capacity + 1];
    }//end MaxHeap(capacity)

    /**
     * Inserts a new item into the heap. If the heap is full, re-sizes the heap to accommodate more items.
     *
     * @param element the item to be inserted into the heap.
     */
    public void insert(E element)
    {
        int index;

        if(currentSize == array.length - 1)
            array = resize();

        index = ++currentSize;
        for(array[0] = element; element.compareTo(array[index / 2] ) > 0; index /= 2)
            array[index] = array[index / 2];
        array[index] = element;
    }//end insert(element)

    /**
     * Checks to see if the heap is empty.
     *
     * @return returns true if the heap is empty.
     */
    public boolean isEmpty()
    {
        return currentSize == 0;
    }//end isEmpty()

    /**
     * Prints the initial nodes of the heap to show its general contents.
     */
    public void printImmediateOptions()
    {
        for(int i = 1; i <= 3; i++)
        {
            System.out.println(array[i]);
        }//end for(i)
    }//end printImmediateOptions()

    /**
     * Re-sizes the heap to twice its original size.
     *
     * @return the re-sized heap
     */
    private E[] resize()
    {
        return Arrays.copyOf(array, array.length * 2 + 1);
    }//end resize()

    /**
     * Creates a string containing the contents of the heap, with each node on a new line.
     *
     * @return the string containing the contents of the heap.
     */
    @Override
    public String toString()
    {
        String str = "\n";
        for (int i = 1; i < currentSize + 1; i++)
        {
            str += "[" + (i) + "] " + array[i].toString() + "\n";
        }//end for(i)
        return str;
    }//end toString()
}//end MaxHeap
