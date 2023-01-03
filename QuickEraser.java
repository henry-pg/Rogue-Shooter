import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. QuickEraser is a default-type, erasing-type eraser that has a quick movement speed and 
 * bounces off of walls like an idle DVD animation. The angle it starts off of is assigned in the beginning.
 * 
 * @author Johnathan Lam
 * @version June 9, 2022
 */
public class QuickEraser extends Eraser
{
    //private boolean firstTime;
    private int rotation;
    private int minAngle;
    private int maxAngle;
    private int idleWait;
    private int idlePicNum;

    /**
     * Constructor for QuickEraser. The max speed determines how fast its default speed would be, while the 
     * erase width determines how big his erase area is.
     * 
     * @param maxSpeed      The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     * @param eraseWidth    The width of its erasing size, it erases in a square. This is the length / width for the erasing square.
     */
    public QuickEraser (int maxSpeed, int eraseWidth)
    {
        super (maxSpeed, eraseWidth, 100);    
        //firstTime = true;
        //getImage().scale(60, 80);  
        /*
        minAngle = 30;
        maxAngle = 330;
        rotation = minAngle + (int)(Math.random() * maxAngle);

        if (rotation == 0 || rotation == 90 || rotation == 180 || rotation == 270 || rotation == 360)
        {
        rotation += 35;
        }
         */
        //firstTime = true;
        minAngle = 10;
        maxAngle = 70;
        rotation = minAngle + (int)(Math.random() * maxAngle);
        int random = Greenfoot.getRandomNumber(4);
        if (random == 0)
        {
            rotation = 180 - rotation;
        }
        else if (random == 1)
        {
            rotation += 180;
        }
        else if (random == 2)
        {
            rotation = 360 - rotation;
        }

        idleWait = 0;
        idlePicNum = 0;
    }

    /**
     * Bounces off of walls when it walks (and erases whereever it walks)
     */
    public void act()
    {
        
        idleAnimate();
        super.act();
    }

    /**
     * Bounces off of walls when it hits a wall
     */
    public void walk()
    {
        // sets the eraser to the real rotation to allow it to move in that direction
        setRotation (rotation);
        //System.out.println(rotation);
        move(speed);
        //rotation = getRotation();
        /*
        if (isAtEdge())
        {
        if (rotation > 0)
        {

        }
        else if (rotation < 0)
        {

        }
        //System.out
        rotation = 180 - getRotation();
        System.out.println(rotation);
        }
         */
        // if it hits a border, bounce
        if (isAtEdge())//getY()<= 3 || getY() >= 550 || getX() == 0 || getX() == 899)
        {
            if (getY() == 0 || getY() == getWorld().getHeight()-1)
            {
                rotation = 360 - getRotation();
            }

            if (getX()==0 || getX() == getWorld().getWidth()-1)
            {
                rotation = 180 - getRotation();
            }
            /*
            if (direction.equals("tr"))
            {
            rotation = 90 + getRotation();
            direction = "tl";
            }
            else if (direction.equals("tl"))
            {
            rotation = 180 - getRotation();
            direction = "bl";
            }
            else if (direction.equals("br"))
            {
            rotation = 180 - getRotation();    
            direction = "tr";
            }
            else if (direction.equals("bl"))
            {
            rotation = getRotation();
            direction = "br";
            }
             */
        }
        Overlay.erase(getX(), getY() + 30, eraseWidth);

        // after it finishes moving, it will set the rotation back to 0 - its default angle
        setRotation (0);
        //System.out.println(direction);
    }

    /**
     * Idle animation for the eraser
     */
    private void idleAnimate()
    {
        String[] picture = {"Fast  (1).png", "Fast  (2).png", "Fast  (3).png", "Fast  (4).png"};

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