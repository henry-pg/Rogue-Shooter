import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Spawns a Fire object that deals constant damage to erasers over time
 * 
 * @author Ethan Lin 
 * @version June 2022
 */

public class Flamethrower extends Weapon
{
    // declare important variables
    private boolean keyHeld = false;
    Fire fire;
    private MouseInfo mouse;
    private GreenfootSound[] shoot;
    private int shootSoundsIndex;
    private int actCounter;
    private int shootSoundsIndex2;

    /**
     * Constructor for Flamethrower. Calls the superclass' constructor
     * and sets other important variables
     * 
     * @param owner     The player that the Flamethrower follows
     */
    public Flamethrower (Pencil owner)
    {
        super (owner, 0, 20, 80);
        actCounter = 130;
        shootSoundsIndex = 0;
        shootSoundsIndex2 = 0;
        shoot = new GreenfootSound[20];
        for (int i = 0; i < shoot.length; i++)
        {
            shoot[i] = new GreenfootSound ("flame.mp3");
            shoot[i].setVolume(70);
        }
    }

    /**
     * Act method for Flamethrower. Uses the superclass' act method
     */
    public void act()
    {
        super.act();

        // rescales the image when the player picks it up
        if (player!=null)
        {
            getImage().scale(36, 18);
        }
    }

    /**
     * Shoot method for Flamethrower. When mouse is clicked, spawns a Fire object
     * that follows the Flamethrower and deals damage to enemies until the mouse
     * is released.
     */
    public void shoot()
    {
        if(!Pencil.isStunned){
            try 
            {
                // when mouse is first clicked, add a new Fire object to the world
                // and set its location to the location of the Flamethrower
                if (Greenfoot.mousePressed(null) && !keyHeld )
                {
                    fire = new Fire (getRotation()+90);
                    getWorld().addObject(fire, getX(), getY());

                    double xOffset = Math.cos(getRotationRadians()) * 53;
                    double yOffset = Math.sin(getRotationRadians()) * 53;

                    setFireLocation(xOffset, yOffset);
                    keyHeld = true;
                    actCounter++;
                    if (actCounter >= 56)
                    {
                        actCounter = 0;
                        shootSoundsIndex2 = shootSoundsIndex;
                        shoot[shootSoundsIndex].play();
                        shootSoundsIndex++;
                        if (shootSoundsIndex > shoot.length - 1)
                        {
                            shootSoundsIndex = 0;
                        }
                    }
                }
                else if (!Greenfoot.mouseClicked(null) && keyHeld )
                {
                    // if mouse is held, continue to update Fire's location based on
                    // location of Flamethrower
                    double xOffset = Math.cos(getRotationRadians()) * 53;
                    double yOffset = Math.sin(getRotationRadians()) * 53;

                    setFireLocation(xOffset, yOffset);
                    actCounter++;
                    if (actCounter >= 56)
                    {
                        actCounter = 0;
                        shootSoundsIndex2 = shootSoundsIndex;
                        shoot[shootSoundsIndex].play();
                        shootSoundsIndex++;
                        if (shootSoundsIndex > shoot.length - 1)
                        {
                            shootSoundsIndex = 0;
                        }
                    }
                }
                else if (Greenfoot.mouseClicked(null) && keyHeld)
                {
                    // when mouse is released, removes the Fire object and sets
                    // keyHeld to false
                    getWorld().removeObject(fire);
                    keyHeld = false;
                    shoot[shootSoundsIndex2].stop();
                    actCounter = 100;
                }
            } 
            catch (NullPointerException e) 
            {
                // if no mouse present, do nothing
            }
        }else{
            getWorld().removeObject(fire);
            keyHeld = false; 
            shoot[shootSoundsIndex2].stop();
            actCounter = 100;
        }
        shootingTimer--;
    }

    /** 
     * Method to set the location of Fire object to the location of the 
     * Flamethrower at an offsey
     */    
    private void setFireLocation (double xOffset, double yOffset)
    {
        // sets location of fire
        if (getRotation() < 90)
        {
            fire.setLocation(getX()+(int)xOffset, getY()+(int)yOffset);
        }
        else if (180>getRotation()&&getRotation()>=90)
        {
            fire.setLocation(getX()-(int)yOffset, getY()+(int)xOffset);
        }
        else if (270>getRotation()&&getRotation()>=180)
        {
            fire.setLocation(getX()-(int)xOffset, getY()-(int)yOffset);
        }
        else if (360>getRotation()&&getRotation()>=270)
        {
            fire.setLocation(getX()+(int)yOffset, getY()-(int)xOffset);
        }
        fire.setRotation(getRotation() + 90);
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
                setImage("flamethrowerFlipped.png");
            }
            else if (mouse.getX()>=this.getX())
            {
                setImage("flamethrower.png");
            }
        } 
        catch (NullPointerException e) 
        {
            // if no mouse present, do nothing
        }
    }

    /**
     * Returns the Fire object that it spawns when attacking
     * 
     * @return fire     The Fire object that is spawned
     */
    public Fire getFire(){
        return fire;
    }

}