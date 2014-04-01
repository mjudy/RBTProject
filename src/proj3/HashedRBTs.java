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
    private ArrayList<RedBlackTree<Node>> table;

    public HashedRBTs(int size)
    {
        table = new ArrayList<>(size);

        for(int i = 0; i < size; i++)
        {
            RedBlackTree<Node> tree = new RedBlackTree<>();
            table.add(i, tree);
        }
    }

    public void fileReader(String filename)
    {
        File file = new File(filename);
        String str;
        int index;

        try
        {
            Scanner scan = new Scanner(file);
            while (scan.hasNext())
            {
                str = scan.next();
                str = str.replaceAll("[\\W]|[0-9]", "");

                if (str.length() > 0)
                {
                    if(str.charAt(0) - 65 >= 0 && str.charAt(0) <= 25)
                    {
                        index = str.charAt(0) - 65;
                        table.get(index).insert(new Node(str));
                    }
                    if(str.charAt(0) - 97 >=0 && str.charAt(0) - 97 <= 25)
                    {
                        index = str.charAt(0) - 71;
                        table.get(index).insert(new Node(str));
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
