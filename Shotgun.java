import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Shotgun fires 5 pellets at slightly different angles which spread out over
 * a large area
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class Shotgun extends Weapon
{
    private GreenfootSound[] shoot;
    private int shootSoundsIndex;

    /**
     * Constructor for Shotgun. Calls the superclass' constructor and
     * sets other important variables
     * 
     * @param owner     The player that the Shotgun follows
     */
    public Shotgun (Pencil owner)
    {
        super (owner, 0, 20, 80);
        shootSoundsIndex = 0;
        shoot = new GreenfootSound[20];
        for (int i = 0; i < shoot.length; i++)
        {
            shoot[i] = new GreenfootSound ("shotty.wav");
            shoot[i].setVolume(65);
        }
    }

    /**
     * Act method for Shotgun. Uses the superclass' act method
     */
    public void act()
    {
        super.act();
    }

    /**
     * Shoot method for Shotgun. Fires 5 pellets with slightly varying rotations
     * 
     */
    public void shoot()
    {
        if(!Pencil.isStunned){
            try 
            {
                if (Greenfoot.mousePressed(null) && shootingTimer <= 0)
                {
                    // get x and y offsets using trigonometry
                    double xOffset = Math.cos(getRotationRadians()) * 25;
                    double yOffset = Math.sin(getRotationRadians()) * 25;

                    // add 5 pellets with slightly varying rotations at offset
                    if (getRotation() < 90)
                    {
                        getWorld().addObject(new Pellet (getRotation()), getX()+(int)xOffset, getY()+(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()-5), getX()+(int)xOffset, getY()+(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()-10), getX()+(int)xOffset, getY()+(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()+5), getX()+(int)xOffset, getY()+(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()+10), getX()+(int)xOffset, getY()+(int)yOffset);
                    }
                    else if (180>getRotation()&&getRotation()>=90)
                    {
                        getWorld().addObject(new Pellet (getRotation()), getX()-(int)yOffset, getY()+(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()-5), getX()-(int)yOffset, getY()+(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()-10), getX()-(int)yOffset, getY()+(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()+5), getX()-(int)yOffset, getY()+(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()+10), getX()-(int)yOffset, getY()+(int)xOffset);
                    }
                    else if (270>getRotation()&&getRotation()>=180)
                    {
                        getWorld().addObject(new Pellet (getRotation()), getX()-(int)xOffset, getY()-(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()-5), getX()-(int)xOffset, getY()-(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()-10), getX()-(int)xOffset, getY()-(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()+5), getX()-(int)xOffset, getY()-(int)yOffset);
                        getWorld().addObject(new Pellet (getRotation()+10), getX()-(int)xOffset, getY()-(int)yOffset);
                    }
                    else if (360>getRotation()&&getRotation()>=270)
                    {
                        getWorld().addObject(new Pellet (getRotation()), getX()+(int)yOffset, getY()-(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()-5), getX()+(int)yOffset, getY()-(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()-10), getX()+(int)yOffset, getY()-(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()+5), getX()+(int)yOffset, getY()-(int)xOffset);
                        getWorld().addObject(new Pellet (getRotation()+10), getX()+(int)yOffset, getY()-(int)xOffset);
                    }

                    // play shotgun sound
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
                setImage("shotgunFlipped.png");
            }
            else if (mouse.getX()>=this.getX())
            {
                setImage("shotgun.png");
            }
        } 
        catch (NullPointerException e) 
        {
            // if no mouse present, do nothing
        }
    }
}