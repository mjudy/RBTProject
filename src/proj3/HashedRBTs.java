package proj3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Mark Judy <mjudy1@umbc.edu>
 * @version 1.0
 *          Date: 4/1/14
 *          Time: 1:49 AM
 */
public class HashedRBTs <E extends Comparable<? super E>>
{
    private ArrayList<RedBlackTree<Partial>> table;

    /**
     * Creates a table of ArrayLists containing RedBlackTrees. Initializes the header node of the RedBlackTree to a
     * minimum value to build the tree.
     *
     * @param size size of the table
     */
    public HashedRBTs(int size)
    {
        table = new ArrayList<>(size);

        String minStr = "" + Character.MIN_VALUE;

        for(int i = 0; i < size; i++)
        {
            table.add(i, new RedBlackTree<>(new Partial(new Node(minStr, 0))));
        }//end for(i)
    }//end HashedRBTs(size)

    /**
     * Reads a text input file to populate the table of RedBlackTrees. The trees are ordered alphabetically with
     * uppercase preceding lowercase.
     *
     * @param filename the name of the file to read
     */
    public void fileReader(String filename)
    {
        File file = new File(filename);
        String str;
        int index, frequency;

        try
        {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine())
            {
                str = scan.nextLine();
                str = str.replaceAll("Empty tree|Node|word=|frequency=|[\\W]", "");

                if (str.length() > 0)
                {
                    frequency = Integer.parseInt(str.replaceAll("[\\D]", ""));
                    str = str.replaceAll("[\\d]", "");
                    Node newNode = new Node(str, frequency);
                    if(str.charAt(0) - 65 >= 0 && str.charAt(0) - 65 <= 25)
                    {
                        index = str.charAt(0) - 65;

                        if(table.get(index).contains(new Partial(newNode)))
                        {
                            table.get(index).retrieveIfItContains(new Partial(newNode)).insertNodeIntoHeap(newNode);
                        }//end if
                        else
                        {
                            table.get(index).insert(new Partial(newNode));
                        }//end else
                    }//end if
                    else if(str.charAt(0) - 97 >=0 && str.charAt(0) - 97 <= 25)
                    {
                        index = str.charAt(0) - 71;

                        if(table.get(index).contains(new Partial(newNode)))
                        {
                            table.get(index).retrieveIfItContains(new Partial(newNode)).insertNodeIntoHeap(newNode);
                        }//end if
                        else
                        {
                            table.get(index).insert(new Partial(newNode));
                        }//end else
                    }//end elif
                }//end if
            }//end while(scan.hasNextLine())
        }//end try
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }//end catch(FileNotFoundException)
    }//end fileReader(filename)

    /**
     * Prints results for debugging.
     */
    public void printHashCountResults()
    {
        for(RedBlackTree x : table)
        {
            if(x.getRoot() != null)
            {
                x.printRoot();
            }//end if
            else
            {
                System.out.println("This tree has no nodes.");
            }//end else
        }//end for(RedBlackTree x)
    }//end printHashCountResults()

    /**
     * Returns the RedBlackTree at a given index in the table.
     *
     * @param index the index of the RedBlackTree to return.
     * @return a RedBlackTree from the table.
     */
    public RedBlackTree retrieveHashedRBTat(int index)
    {
        return table.get(index);
    }//end retrieveHashedRBTat(index)
}//end HashedRBTs