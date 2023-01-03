import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Projectile fired by handgun.
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class Bullet extends Projectile
{
    /**
     * Constructor for Bullet. Calls the superclass' constructor
     * 
     * @param rotation      The rotation of the weapon that fired it
     */
    public Bullet (int rotation)
    {
        super (34, rotation, 25);
        getImage().scale(30,20); 
        getImage().mirrorHorizontally(); 
    }
    
    /**
     * Act method for Bullet. Uses the superclass' act method
     */
    public void act()
    {
        super.act();
    }
}
