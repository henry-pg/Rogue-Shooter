import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Projectile shot by Shotgun
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class Pellet extends Projectile
{
    // declare distance variable
    private int distanceTravelled;

    /**
     * Constructor for pellet. Calls the superclass' constructor
     * 
     * @param rotation      The rotation of the weapon that fired it
     */
    public Pellet (int rotation)
    {
        super (20, rotation, 10);
        distanceTravelled = 0;
        getImage().scale(10,10);
    }

    /**
     * Act method for Pellet. Calls the superclass' act method, but removes self
     * from world if travelled a set distance
     */
    public void act()
    {
        super.act();
        distanceTravelled ++;
        // if travelled set distance, remove self
        if (distanceTravelled >= 45)
        {
            try {
                getWorld().removeObject(this);
            } catch (NullPointerException e) {
                // if Pellet already removed from world, do nothing
            }
        }
    }
}
