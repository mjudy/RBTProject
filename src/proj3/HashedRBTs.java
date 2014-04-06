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

        for(int i = 0; i < size; i++)
        {
//            RedBlackTree<Partial> tree = new RedBlackTree<>();
            table.add(i, null);
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
                str = str.replaceAll("Node|word=|frequency=|[\\W]", "");                
                frequency = Integer.parseInt(str.replaceAll("[\\D]", ""));
                str = str.replaceAll("[\\d]", "");

                if (str.length() > 0)
                {
                    Node newNode = new Node(str, frequency);
                    System.out.println(newNode);
                    if(str.charAt(0) - 65 >= 0 && str.charAt(0) - 65 <= 25)
                    {
                        index = str.charAt(0) - 65;

                        if(table.get(index) == null)
                        {
//                            System.out.println("B");
                            RedBlackTree<Partial> tree = new RedBlackTree<>(new Partial(newNode));
                            table.add(index, tree);
                        }
                        else if( table.get(index).contains(new Partial(newNode)))
                        {
//                              System.out.println("A");
                            table.get(index).getElement(new Partial(newNode)).insertNodeIntoHeap(newNode);
                        }
                        else
                        {
                            table.get(index).insert(new Partial(newNode));
                        }
                    }
                    if(str.charAt(0) - 97 >=0 && str.charAt(0) - 97 <= 25)
                    {
                        index = str.charAt(0) - 71;

                        if(!table.get(index).isEmpty() && table.get(index).contains(new Partial(newNode)))
                        {
//                            System.out.println("C");
                            table.get(index).getElement(new Partial(newNode)).insertNodeIntoHeap(newNode);
                        }
                        else if(table.get(index).isEmpty())
                        {
//                            System.out.println("D");
                            table.get(index).insert(new Partial(newNode));
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
//        for(RedBlackTree<Partial> x : table)
//        {
//            System.out.println("This tree starts with: " + x.getRoot() + " --> The heap contains: ");
//            x.getRoot().printImmediateOptions();
//        }
    }

    public RedBlackTree retrieveHashedRBTat(int index)
    {
        return table.get(index);
    }


}
