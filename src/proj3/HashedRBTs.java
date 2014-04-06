package proj3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author theghv
 * @version 1.0
 *          Date: 4/1/14
 *          Time: 1:49 AM
 */
public class HashedRBTs <E extends Comparable<? super E>>
{
    private ArrayList<RedBlackTree<Partial>> table;

    public HashedRBTs(int size)
    {
        table = new ArrayList<>(size);

        String str = "" +Character.MIN_VALUE;

        for(int i = 0; i < size; i++)
        {
            table.add(i, new RedBlackTree<>(new Partial(new Node(str, 0))));
        }
    }

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
//                    System.out.println(newNode);
                    if(str.charAt(0) - 65 >= 0 && str.charAt(0) - 65 <= 25)
                    {
                        index = str.charAt(0) - 65;

                        if(table.get(index).contains(new Partial(newNode)) != null)
                        {
                            table.get(index).contains(new Partial(newNode)).insertNodeIntoHeap(newNode);
                        }
                        else
                        {
                            table.get(index).insert(new Partial(newNode));
                        }
                    }
                    if(str.charAt(0) - 97 >=0 && str.charAt(0) - 97 <= 25)
                    {
                        index = str.charAt(0) - 71;

                        if(table.get(index).contains(new Partial(newNode)) != null)
                        {
                            table.get(index).contains(new Partial(newNode)).insertNodeIntoHeap(newNode);
                        }
                        else
                        {
                            table.get(index).insert(new Partial(newNode));
                        }
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void printHashCountResults()
    {
        try
        {
            for(RedBlackTree x : table)
            {
                if(x.getRoot() != null)
                {
                    String temp = x.getRoot().toString();
                    String str1 = "This tree starts with " + temp.substring(0, 17) + " --> The heap contains:" + temp.substring(17);
                    System.out.print(str1);
                }
                else
                {
                    System.out.println("This tree has no nodes.");
                }
            }
        }
        catch (NullPointerException n)
        {
            System.out.println("I dun goofed.");
            n.printStackTrace();
        }
    }

    public RedBlackTree retrieveHashedRBTat(int index)
    {
        return table.get(index);
    }


}
