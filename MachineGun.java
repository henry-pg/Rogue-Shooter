import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Machine Gun shoots bullets that deal slightly less damage than the 
 * Handgun, but at a much higher rate.
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class MachineGun extends Weapon
{
    // declare variable
    private boolean keyHeld;

    private GreenfootSound[] shoot;
    private int shootSoundsIndex;

    /**
     * Constructor for MachineGun. Calls the superclass' constructor and sets
     * other important variables
     * 
     * @param owner     The player that the MachineGun follows
     */
    public MachineGun (Pencil owner)
    {
        super (owner, 0, 20, 8);
        keyHeld = false;

        shootSoundsIndex = 0;
        shoot = new GreenfootSound[20];
        for (int i = 0; i < shoot.length; i++)
        {
            shoot[i] = new GreenfootSound ("machinegun.wav");
            shoot[i].setVolume(80);
        }
    }

    /**
     * Act method for MachineGun. Uses the superclass' act method
     */
    public void act()
    {
        super.act();
    }

    /**
     * Shoots Machine Gun Bullets at a much higher rate.
     */
    public void shoot()
    {
        if(!Pencil.isStunned){
            try 
            {
                // When mouse is first pressed, set keyHeld variable to true
                if (Greenfoot.mousePressed(null) && !keyHeld)
                {
                    keyHeld = true;
                }
                else if (!Greenfoot.mouseClicked(null) && keyHeld && shootingTimer <= 0)
                {
                    double xOffset = Math.cos(getRotationRadians()) * 23;
                    double yOffset = Math.sin(getRotationRadians()) * 23;

                    if (getRotation() < 90)
                    {
                        getWorld().addObject(new MachineGunBullet (getRotation()), getX()+(int)xOffset, getY()+(int)yOffset);
                    }
                    else if (180>getRotation()&&getRotation()>=90)
                    {
                        getWorld().addObject(new MachineGunBullet (getRotation()), getX()-(int)yOffset, getY()+(int)xOffset);
                    }
                    else if (270>getRotation()&&getRotation()>=180)
                    {
                        getWorld().addObject(new MachineGunBullet (getRotation()), getX()-(int)xOffset, getY()-(int)yOffset);
                    }
                    else if (360>getRotation()&&getRotation()>=270)
                    {
                        getWorld().addObject(new MachineGunBullet (getRotation()), getX()+(int)yOffset, getY()-(int)xOffset);
                    }

                    shoot[shootSoundsIndex].play();
                    shootSoundsIndex++;
                    if (shootSoundsIndex > shoot.length - 1)
                    {
                        shootSoundsIndex = 0;
                    }

                    shootingTimer = maxShootingTimer;
                }
                else if (Greenfoot.mouseClicked(null) && keyHeld)
                {
                    keyHeld = false;
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
                setImage("machineGunFlipped.png");
            }
            else if (mouse.getX()>=this.getX())
            {
                setImage("machineGun.png");
            }
        } 
        catch (NullPointerException e) 
        {
            // if no mouse present, do nothing
        }
    }
}