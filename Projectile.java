import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Includes all types of bullet fired from weapons.
 * 
 * @author Ethan Lin 
 * @version June 2022
 */

public class Projectile extends Actor
{
    // declare variables
    protected int damage;
    protected int speed;
    protected boolean inWorld;

    /**
     * Constructor for Projectile. Sets important variables
     * 
     * @param damage        The damage of the projectile
     * @param rotation      The rotation of the weapon firing the projectile
     * @param speed         The speed that the projectile moves at
     */
    public Projectile (int damage, int rotation, int speed)
    {
        // set variables
        this.damage = damage;
        this.speed = speed;
        setRotation(rotation);
        inWorld = true;
    }

    /**
     * Act method for Projectile. Projectile will move until it hits an eraser 
     * or touches the edge of the world
     */
    public void act()
    {
        move(speed);
        checkHitEraser();
        if (inWorld)
        {
            checkEdge();
        }
    }

    /**
     * Method to check if touching an eraser. If it is, deal damage to the eraser
     * and remove self from world
     */
    public void checkHitEraser()
    {
        // if touching eraser, deal damage to eraser and remove self
        Eraser e = (Eraser)getOneObjectAtOffset((int)speed + getImage().getWidth()/2, getImage().getHeight()/4, Eraser.class);
        if (e != null)
        {
            e.takeDamage(damage);
            getWorld().removeObject(this);
            inWorld = false;
        }
    }

    /**
     * Method to check if touching the edge of the world. If touching, remove
     * self from world
     */
    public void checkEdge()
    {
        // if exits world borders, remove self
        if (this.getX() <= 1 || this.getX() >= 899 || this.getY() <= 1 || this.getY() >=599)
        {
            getWorld().removeObject(this);
            inWorld = false;
        }
    }
}
