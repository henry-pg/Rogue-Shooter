import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. Minion Eraser is a summonable-type, erasing-type enemy spawned by the SummonerEraser.
 * It is extremely fragile (has very low health) and will die after eating a certain amount of ink. The behaviour is the same as the NormalEraser - walking 
 * in random directions for set number of intervals, and will tp to the other side of the screen if it touches a border (like DoodleJump).
 * 
 * @author Johnathan Lam
 * @version June 11, 2022
 */
public class MinionEraser extends Eraser
{
    private int maxInkEat;
    private int inkAte;
    private boolean alive;
    private int actCounter;
    private boolean firstTime;
    private int numActsChange;
    private String direction = "";
    private int min;
    private int max;
    private int idleWait;
    private int idlePicNum;

    /**
     * Constructor for MinionEraser. The max speed determines how fast its default speed would be, while the 
     * erase width determines how big his erase area is.
     * 
     * @param maxSpeed      The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     * @param eraseWidth    The width of its erasing size, it erases in a square. This is the length / width for the erasing square.
     */
    public MinionEraser(int maxSpeed, int eraserWidth)
    {
        super (maxSpeed, eraserWidth, 1);    
        maxInkEat = 8000;
        alive = true;
        firstTime = true;
        min = 30;
        max = 60;
        inkAte = 0;
        idleWait = 0;
        idlePicNum = 0;
        numActsChange = min + (int)(Math.random() * max);
        //getImage().scale(60, 80);        
    }

    /**
     * Chooses a direction to move when it spawns in. It also checks whether it ate the max amount of ink
     * and will die if the condition is fulfilled. The Minion Eraser will also walk/behave like the NormalEraser, walking in random directions for a set amount of time
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

        // checks if it ate the max amount, then will die if it is true
        if (alive && inkAte >= maxInkEat)
        {
            alive = false;
            getWorld().removeObject(this);
        }

        
        
        idleAnimate();
        
        //walks
        if (alive)
        {
            actCounter++;
            super.act();
        }
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
            inkAte += Overlay.eraseGetValue(getX(), getY() + 13, eraseWidth);

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
        String[] picture = {"Minion idle (1).png", "Minion idle (2).png", "Minion idle (3).png"};

        if (idleWait == 10)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 3)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }
}
