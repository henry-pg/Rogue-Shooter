import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. PusherEraser is a default-type, attacking-type eraser that pushes the player away if they get too close. 
 * It moves like the DefaultEraser (a random direction for specified times, and if it touches a border it will teleport ot the opposite side). As it is
 * an attacking-type eraser, it will not erase whereever it walks.
 * When the player is pushed, it will move in a specified direction for some tiles without drawing any ink on the ground. There is an aura around
 * the PusherEraser to indicate its range. This aura follows the Eraser wherever it goes.
 * 
 * @author Henry Peng
 * @version June 17, 2022
 */
public class PusherEraser extends Eraser
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
    private PusherEraserHitbox p;
    private int idleWait;
    private int idlePicNum;
    
    //frames
    private static GreenfootImage one;
    private static GreenfootImage two;
    private static GreenfootImage three;
    private static GreenfootImage four;
    private static GreenfootImage five;
    private static GreenfootImage six;
    private static GreenfootImage seven;
    private static GreenfootImage eight;
    private static GreenfootImage nine;
    private static GreenfootImage ten;

    /**
     * The constructor for PusherEraser. The max speed deterines how fast its default speed would be. This eraser
     * will not erase, so an ink width is not needed.
     * 
     * @param maxSpeed  The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     */
    public PusherEraser(int maxSpeed)
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
        p = new PusherEraserHitbox(70, this, PaperWorld.RED);
        scaleImages();
        setImage(one);
    }

    /**
     * It will move in a random direction for specified times and calls on its idle animation
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
            getWorld().addObject(p, getX(), getY());
            
        }

        idleAnimate();
        
        
        
        
        actCounter++;
        
        if (health <= 0){
            getWorld().removeObject(p);
        }
        // walks
        super.act();
        
    }

    /**
     * Walks in random directions for a set amount of time, then changes direction (or keeps going in the same direction)
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
            //Overlay.erase(getX(), getY() + 30, eraseWidth);
            
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
     * Gets the direction of its movement
     * 
     * @return String   Returns the direction of its current movement
     */
    public String getDir()
    {
        return direction;        
    }
    
    /**
     * Idle animation for the eraser
     */
    private void idleAnimate()
    {
        String[] picture = {"Pusher Enemy (1).png", "Pusher Enemy (2).png", "Pusher Enemy (3).png", "Pusher Enemy (4).png", "Pusher Enemy (5).png", "Pusher Enemy (6).png"};

        if (idleWait == 7)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 6)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }

    /**
     * The animation for attacking
     */
    private void attackAnimate()
    {
        GreenfootImage[] picture = {one, two, three, four, five, six, seven, eight, nine, ten}; 

        
        
        if (attackWait == 3)
        {
            setImage (picture[attackPicNum]);
            //image = getImage();
            //image.scale (60, 50);
            attackPicNum++;
            attackWait = 0;

            if (attackPicNum == 10)
            {
                attackDone = true;
                attackPicNum = 0;
            }
        }
        

        
    }
    
    // scales the images for its idle animation
    private static void scaleImages()
    {
        one = new GreenfootImage("Pusher (1).png");
        one.scale (40,60);
        
        two = new GreenfootImage("Pusher (2).png");
        two.scale (40,60);
        
        three = new GreenfootImage("Pusher (3).png");
        three.scale (40,60);
        
        four = new GreenfootImage("Pusher (4).png");
        four.scale (40,60);
        
        five = new GreenfootImage("Pusher (5).png");
        five.scale (40,60);
        
        six = new GreenfootImage("Pusher (6).png");
        six.scale (40,60);
        
        seven = new GreenfootImage("Pusher (7).png");
        seven.scale (40,60);
        
        eight = new GreenfootImage("Pusher (8).png");
        eight.scale (40,60);
        
        nine = new GreenfootImage("Pusher (9).png");
        nine.scale (40,60);
        
        ten = new GreenfootImage("Pusher (10).png");
        ten.scale (40,60);
    }
}