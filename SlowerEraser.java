import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. SlowerEraser is a default-type, attacking-type eraser that slows the player's speed down if they get too close. 
 * It moves like the DefaultEraser (a random direction for specified times, and if it touches a border it will teleport ot the opposite side). As it is
 * an attacking-type eraser, it will not erase whereever it walks.
 * When the player walks into its aura (or radius), the pencil's speed will be slowed for a few seconds after moving out of the slowing aura. There is an aura around
 * the SlowerEraser to indicate its range. This aura follows the Eraser wherever it goes.
 * 
 * @author Henry Peng 
 * @version June 17, 2022
 */
public class SlowerEraser extends Eraser
{
    private int attackWait;
    private int attackPicNum;
    private boolean attackDone;
    private int actCounter;
    private boolean firstTime;
    private int numActsChange;
    private String direction = "";
    private int min;
    private int max;
    private SlowerEraserHitbox s;
    private int idleWait;
    private int idlePicNum;
    
    /**
     * Constructor for SlowerEraser. The max speed deterines how fast its default speed would be. This eraser
     * will not erase, so an ink width is not needed.
     * 
     * @param maxSpeed  The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     */
    public SlowerEraser(int maxSpeed)
    {
        super (maxSpeed, 0, 100);    
        actCounter = 0;
        attackWait = 0;
        attackPicNum = 0;
        attackDone = false;
        firstTime = true;
        min = 60;
        max = 100;
        idleWait = 0;
        idlePicNum = 0;
        numActsChange = min + (int)(Math.random() * max);
        s = new SlowerEraserHitbox(120, this, PaperWorld.BLUE);
    }

    /**
     * It will move in a random direction for a specified time then change directions and calls upon its idle animation.
     */
    public void act()
    {
        // chooses a random location to walk in
        if (firstTime)
        {
            firstTime = false;
            int random = Greenfoot.getRandomNumber (4);
            if (random == 0)
            {
                direction = "up";
            }
            else if (random == 1)
            {
                direction = "down";
            }
            else if (random == 2)
            {
                direction = "right";
            }
            else if (random == 3)
            {
                direction = "left";
            }
            getWorld().addObject(s, getX(), getY());
            
        }
        
        if (health <= 0){
            getWorld().removeObject(s);
        }
        
        
        actCounter++;
        idleAnimate();
        
        // walks
        super.act();
        
    }

    /**
     * Same as the DefaultEraser's behaviour, it will walk in a specified direction for a set time, then change direction and time between changing in a random
     * number generator. It will also teleport to the opposite border when it touches a border.
     */
    public void walk()
    {
        // walks in specified direction
        if (actCounter < numActsChange && !(getY() == 0 && direction.equals("up")) && !(getY() == 599 && direction.equals("down")) && !(getX() == 0 && direction.equals ("left")) && !(getX() == 899 && direction.equals ("right")))
        {
            if (direction.equals("up"))
            {
                setLocation(getX(), getY() - speed);
                
                if (getY() < 22)
                {
                    setLocation(getX(), 560);
                }
            }
            else if (direction.equals("down"))
            {
                setLocation(getX(), getY() + speed);
                
                if (getY() > 595)
                {
                    setLocation(getX(), 23);
                }
            }
            else if (direction.equals("left"))
            {
                setLocation(getX() - speed, getY());
                
                if (getX() < 14)
                {
                    setLocation(890, getY());
                }
            }
            else if (direction.equals("right"))
            {
                setLocation (getX() + speed, getY());
                
                if (getX() > 890)
                {
                    setLocation(14, getY());
                }
            }
            Overlay.erase(getX(), getY() + 30, eraseWidth);
            
        }
        // chooses a new direction to go to
        else
        {
            // determines how long to walk in that direction
            numActsChange = min + (int)(Math.random() * max);
            actCounter = 0;
            int random = Greenfoot.getRandomNumber (4);
            if (random == 0)
            {
                direction = "left";
            }
            else if (random == 1)
            {
                direction = "right";
            }
            else if (random == 2)
            {
                direction = "up";
            }
            else if (random == 3)
            {
                direction = "down";
            }
        }
    }
    
    /**
     * Idle animation for the eraser
     */
    private void idleAnimate()
    {
        String[] picture = {"slow anim  (1).png", "slow anim  (2).png", "slow anim  (3).png", "slow anim  (4).png", "slow anim  (5).png"};

        if (idleWait == 10)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 5)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }
}