import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class is responsible for sorting the high scores and printing
 * them to the screen when the player clicks on the high score icon.
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class HighScoreScreen extends Actor
{
    // important variables
    boolean ran = false;
    ArrayList scores = new ArrayList <Integer>();
    ArrayList names = new ArrayList <String>();

    /**
     * The High Score Screen will get all the high scores from the highscore.txt
     * file, then sort them and print them to the screen.
     */
    public void act()
    {
        // makes sure it only runs once
        if (!ran)
        {
            getWorld().addObject(new XButton(this), 750, 100);
            getHighScore();
            sortHighScores(scores, names);
            showHighScores();
            ran = true;
        }
    }

    /**
     * Retreives the scores and names from the text file and stores them
     * in two separate ArrayLists using a tokenizer.
     */
    public void getHighScore ()
    {
        Scanner s;
        StringTokenizer tokenizer;

        try
        {
            // create scanner
            s = new Scanner(new File("highscore.txt"));
            boolean moreLines = true;

            while(moreLines)
            {
                try
                {
                    // add names and scores using tokenizer
                    tokenizer = new StringTokenizer(s.nextLine(), ",");
                    names.add(tokenizer.nextToken());
                    scores.add(Integer.parseInt(tokenizer.nextToken()));
                } 
                catch (NoSuchElementException e)
                {
                    moreLines = false;
                }
            }
        } 
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    }

    /**
     * Goes through the ArrayList and sorts values from highest to lowest.
     * Whatever changes are made to the score ArrayList, the same changes
     * are made to the names ArrayList so that the scores match with the 
     * names.
     * 
     * @param scores    The ArrayList containing all of the scores
     * @param names     The ArrayList containing all of the names
     */
    public static void sortHighScores (ArrayList scores, ArrayList names)
    {
        // sorts the values from highest to lowest
        for (int i = 0; i < scores.size(); i++) {
            for (int j = 0; j < scores.size(); j++) {
                if ((Integer)scores.get(j) < (Integer)scores.get(i)) {
                    Integer temp = (Integer)scores.get(i);
                    scores.set(i, scores.get(j));
                    scores.set(j, temp);
                    
                    String temp2 = (String)names.get(i);
                    names.set(i, names.get(j));
                    names.set(j, temp2);
                }
            }
        }
    }
    
    /**
     * Prints the high scores to the screen at specific locations, from
     * highest to lowest.
     */
    public void showHighScores ()
    {
        // print the scores to the screen
        try {
            for (int i = 0; i < 5; i++)
            {
                getWorld().showText((String)names.get(i)+" - "+(Integer)scores.get(i), 375, 175 + i*80);
            }
            
            for (int i = 5; i < 10; i++)
            {
                getWorld().showText((String)names.get(i)+" - "+(Integer)scores.get(i), 650, 175 + (i-5)*80);
            }
        } catch (IndexOutOfBoundsException e) {
            // if not enough scores to display, do nothing
        }
    }
}
