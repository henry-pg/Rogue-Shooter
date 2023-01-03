import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. Normal Eraser is a default-type, erasing-type enemy that walks in random directions for specified 
 * times. If it touches a border it will teleport to the opposite side.
 * 
 * @author Johnathan Lam
 * @version June 13, 2022
 */
public class NormalEraser extends Eraser
{
    private int actCounter;
    private boolean firstTime;
    private int numActsChange;
    private String direction = "";
    private int min;
    private int max;
    private int idleWait;
    private int idlePicNum;

    /**
     * Constructor for NormalEraser. The max speed determines how fast its default speed would be, while the 
     * erase width determines how big his erase area is.
     * 
     * @param maxSpeed      The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     * @param eraseWidth    The width of its erasing size, it erases in a square. This is the length / width for the erasing square.
     */
    public NormalEraser (int maxSpeed, int eraseWidth)
    {
        super (maxSpeed, eraseWidth, 100);    
        firstTime = true;
        min = 30;
        max = 60;
        idleWait = 0;
        idlePicNum = 0;
        numActsChange = min + (int)(Math.random() * max);
        //getImage().scale(60, 80);        
    }
    
    /**
     * Chooses a direction to move when it spawns in. It will also walk in random directions for a set amount of time
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
        }
        
        idleAnimate();
        
        
        actCounter++;
        
        //walks
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
        String[] picture = {"Normal Idle (1).png", "Normal Idle (2).png", "Normal Idle (3).png", "Normal Idle (4).png"};

        if (idleWait == 7)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 4)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }
}
