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
            RedBlackTree<Partial> tree = new RedBlackTree<>();
            table.add(i, tree);
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
                    System.out.println(str + " " + frequency);
                    if(str.charAt(0) - 65 >= 0 && str.charAt(0) <= 25)
                    {
                        index = str.charAt(0) - 65;
                        table.get(index).insert(new Partial(new Node(str, frequency)));
                    }
                    if(str.charAt(0) - 97 >=0 && str.charAt(0) - 97 <= 25)
                    {
                        index = str.charAt(0) - 71;
                        table.get(index).insert(new Partial(new Node(str, frequency)));
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

    }

    public RedBlackTree retrieveHashedRBTat(int index)
    {
        return new RedBlackTree();
    }


}
