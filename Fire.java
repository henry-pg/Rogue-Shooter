import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Object spawned by Flamethrower which deals constant, increasing damage over
 * timer to all Erasers that it touches
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class Fire extends Actor
{
    // declare important variables
    private String imageName;
    private String image;
    private int fireStage;
    private boolean firstTime;
    private int actCounter;
    private int damageCounter;

    /**
     * Constructor for Fire. Sets important variables.
     * 
     * @param rotation      The rotation of the Flamethrower
     */
    public Fire (int rotation)
    {
        imageName = "fire_column_medium_";
        image = "";
        fireStage = 1;
        firstTime = true;
        actCounter = 0;
        setRotation (rotation);
        
    }

    /**
     * Act method for Fire. Goes through animations and deals constant damage
     * to Erasers
     */
    public void act()
    {
        // when first spawned in, go through initial animations
        if (firstTime && actCounter%10 == 0)
        {
            image = imageName + fireStage + ".png";
            setImage(image);
            fireStage++;
            damageCounter = 0;
            // when reaches final starting animation, move on to main animations
            if (fireStage == 3)
            {
                firstTime = false;
            }
        }
        else
        {
            // deal damage to Erasers every ten acts
            if (actCounter%10 == 0)
            {
                animate();
                ArrayList<Eraser> e = new ArrayList<Eraser>(getIntersectingObjects(Eraser.class));
                if (e.size() != 0)
                {
                    // deal damage to all erasers touching
                    if (damageCounter <= 18)
                    {
                        damageCounter++;
                    }
                    for (Eraser eraser: e){
                        eraser.takeDamage(damageCounter);
                    }
                }
            }
        }
        actCounter ++;
    }

    /**
     * Method to repeat main Fire animations
     */
    public void animate ()
    {
        image = imageName + fireStage + ".png";
        setImage(image);
        fireStage ++;
        // reset animation
        if (fireStage == 10)
        {
            fireStage = 4;
        }
    }
}
