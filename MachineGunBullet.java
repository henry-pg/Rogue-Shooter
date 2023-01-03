import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Projectile fired by Machine Gun
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class MachineGunBullet extends Projectile
{
    /**
     * Constructor for Machine Gun Bullet. Calls the superclass' constructor
     * 
     * @param rotation      The rotation of the weapon that fired it
     */
    public MachineGunBullet (int rotation)
    {
        super (15, rotation, 15);
        getImage().scale(10,5); 
        getImage().mirrorHorizontally(); 
    }
    
    /**
     * Act method for Machine Gun Bullet. Calls the superclass' act method
     */
    public void act()
    {
        super.act();
    }
}
