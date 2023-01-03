import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Includes all the weapons that are used by the Pencil to defeat erasers.
 * All weapons deal damage to erasers, either by shooting projectiles
 * or by melee damage.
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public abstract class Weapon extends Actor
{
    // declare variables
    protected MouseInfo mouse;
    protected int mouseX;
    protected int mouseY;
    protected int shootingTimer;
    protected int regularShootingTimer;
    protected int maxShootingTimer;
    protected Pencil player;
    protected int xOffset;
    protected int yOffset;
    protected int attackSpeed;
    protected int notPickedUpTimer;
    protected boolean firstTime;

    // abstract methods
    public abstract void flipImage();

    public abstract void shoot();

    /**
     * Constructor for Weapon. Sets important variables
     * 
     * @param owner             The player that the weapon will follow
     * @param xOffset           The number of pixels on the x-axis that the
     *                          weapon is shifted by relative to the player
     * @param yOffset           The number of pixels on the y-axis that the
     *                          weapon is shifted by relative to the player
     * @param maxShootingTimer  The cooldown for the weapon's shooting
     */
    public Weapon (Pencil owner, int xOffset, int yOffset, int maxShootingTimer)
    {
        // set variables
        player = owner;
        this.maxShootingTimer = maxShootingTimer;
        regularShootingTimer = maxShootingTimer;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        notPickedUpTimer = 0;
        firstTime = true;
    }

    /**
     * Act method for Weapon. If the Weapon had an owner, it will turn towards
     * the mouse, follow the player and shoot. If it doesn't have an owner, do
     * nothing until the player touches it.
     */
    public void act()
    {
        if (getImage().getTransparency() < 245 && firstTime)
        {
            getImage().setTransparency(getImage().getTransparency()+10);
            if (getImage().getTransparency() >= 255)
            {
                firstTime = false;
            }
        }
        else
        {
            if((((PaperWorld)(getWorld())).getWeaponBuffed()) == true)
            {
                attackSpeed++;
                if (attackSpeed >= 100)
                {
                    setOriginalAttackSpeed();
                    attackSpeed = 0;
                    ((PaperWorld)(getWorld())).setWeaponBuffedFalse();
                }
            }
            try 
            {
                // main action methods
                if (!PaperWorld.getGameOver())
                {
                    turnTowardsMouse();
                    flipImage();
                    followPlayer();
                    shoot();
                }
            } 
            catch (NullPointerException e) 
            {
                // do nothing until player touches
                try 
                {
                    if(!Pencil.isStunned){
                        // set player as owner
                        Pencil p = (Pencil)getOneIntersectingObject(Pencil.class);
                        player = p;
                        p.setWeapon(this);
                    }
                }
                catch (NullPointerException ex) 
                {
                    // removes the weapon after a set amount of time passes
                    notPickedUpTimer++;
                    getImage().setTransparency(255);
                    if (notPickedUpTimer >= 450 && notPickedUpTimer < 600 && notPickedUpTimer % 7 == 0) {
                        getImage().setTransparency(0);
                    }
                    if (notPickedUpTimer >= 600)
                    {
                        getWorld().removeObject(this);
                    }
                    // if no player present, do nothing
                }
            }
        }
    }

    /**
     * Gets the mouse's position and turn towards it
     */
    public void turnTowardsMouse()
    {
        try 
        {
            // if weapon attached to the player, turn towards the mouse
            if (player != null) 
            {
                mouse = Greenfoot.getMouseInfo();
                mouseX = mouse.getX();
                mouseY = mouse.getY();
                turnTowards(mouseX, mouseY);
            }
        } 
        catch (NullPointerException e) 
        {
            // if there is no mouse present, do nothing
        }       
    }

    /**
     * Gets the player's location and sets its own location to it with an x and
     * y offset
     */
    public void followPlayer()
    {
        // set location to the player's location with offset
        int playerX = player.getX() + xOffset;
        int playerY = player.getY() + yOffset;
        this.setLocation(playerX, playerY);
    }

    /**
     * Returns Weapon's current rotation in radians. Always returns the angle 
     * between 0 ad 90 degrees
     * 
     * @return      The current rotation of the Weapon in radians
     */
    public double getRotationRadians ()
    {
        // if rotation is greater than 90 degrees, subtract from rotation to make
        // it between 0 and 90 degrees
        double rotation;
        if (getRotation() < 90)
        {
            rotation = getRotation();
        }
        else if (180>getRotation()&&getRotation()>=90)
        {
            rotation = getRotation()-90;
        }
        else if (270>getRotation()&&getRotation()>=180)
        {
            rotation = getRotation()-180;
        }
        else if (360>getRotation()&&getRotation()>=270)
        {
            rotation = getRotation()-270;
        }
        else
        {
            rotation = 0;
        }

        // convert rotation to radians
        double radians = Math.toRadians(rotation);
        return radians;
    }

    /**
     * Method to reset maxShootingTimer after being buffed
     */
    public void setOriginalAttackSpeed ()
    {
        maxShootingTimer = regularShootingTimer;
    }

    /**
     * Method to speed up the weapon's attack speed when the attack
     * speed buff is picked up.
     */
    public void attackSpeedUp()
    {
        maxShootingTimer = (int)(maxShootingTimer / 1.5);
        attackSpeed = 0;
        ((PaperWorld)(getWorld())).setWeaponBuffed();
    }
}