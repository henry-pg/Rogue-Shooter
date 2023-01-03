import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math;

/**
 * The standard weapon that the player begins with. Fires a single Bullet.
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class Handgun extends Weapon
{
    private GreenfootSound[] shoot;
    private int shootSoundsIndex;

    /**
     * Constructor for Handgun. Calls the superclass' constructor and
     * sets other important variables
     * 
     * @param owner     The player that the Handgun follows
     */
    public Handgun (Pencil owner)
    {
        super(owner, 0, 20, 30);
        shootSoundsIndex = 0;
        shoot = new GreenfootSound[20];
        for (int i = 0; i < shoot.length; i++)
        {
            shoot[i] = new GreenfootSound ("normalGun.wav");
            shoot[i].setVolume(75);
        }
    }

    /** 
     * Act method for Handgun. Uses the superclass' act method
     */
    public void act()
    {
        super.act();
    }

    /**
     * Shoot method for Handgun. Fires a single Bullet when mouse is clicked
     */
    public void shoot()
    {
        if(!Pencil.isStunned){
            try 
            {
                if (Greenfoot.mousePressed(null) && shootingTimer <= 0)
                {
                    // get x and y offsets using trigonometry
                    double xOffset = Math.cos(getRotationRadians()) * 23;
                    double yOffset = Math.sin(getRotationRadians()) * 23;

                    // add Bullet with rotation at offsets
                    if (getRotation() < 90)
                    {
                        getWorld().addObject(new Bullet (getRotation()), getX()+(int)xOffset, getY()+(int)yOffset);
                    }
                    else if (180>getRotation()&&getRotation()>=90)
                    {
                        getWorld().addObject(new Bullet (getRotation()), getX()-(int)yOffset, getY()+(int)xOffset);
                    }
                    else if (270>getRotation()&&getRotation()>=180)
                    {
                        getWorld().addObject(new Bullet (getRotation()), getX()-(int)xOffset, getY()-(int)yOffset);
                    }
                    else if (360>getRotation()&&getRotation()>=270)
                    {
                        getWorld().addObject(new Bullet (getRotation()), getX()+(int)yOffset, getY()-(int)xOffset);
                    }

                    // play shooting sound
                    shoot[shootSoundsIndex].play();
                    shootSoundsIndex++;
                    if (shootSoundsIndex > shoot.length - 1)
                    {
                        shootSoundsIndex = 0;
                    }

                    // reset shooting timer
                    shootingTimer = maxShootingTimer;
                }
            } 
            catch (NullPointerException e) 
            {
                // if no mouse present, do nothing
            }
        }
        shootingTimer--;
    }

    /**
     * Flips image if the mouse moves to the other side of the weapon
     */
    public void flipImage()
    {
        try 
        {
            // based on mouse's position, flip image
            if (mouse.getX()<this.getX())
            {
                setImage("handgunFlipped.png");
            }
            else if (mouse.getX()>=this.getX())
            {
                setImage("handgun.png");
            }
        } 
        catch (NullPointerException e) 
        {
            // if no mouse present, do nothing
        }
    }
}