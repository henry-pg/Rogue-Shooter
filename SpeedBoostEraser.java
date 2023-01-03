import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootSound;

/**
 * Refer to the Eraser super class. SpeedBoostEraser is a default-type, supporting-type eraser that boosts the speeds of any eraser that goes near it. 
 * It moves like the DefaultEraser (a random direction for specified times, and if it touches a border it will teleport ot the opposite side). As it is
 * a supporting-type eraser, it will not erase whereever it walks.
 * When an eraser walks into its radius, it will move quicker for some time after getting out of the aura. There is an aura around
 * the SpeedBoostEraser to indicate its range. This aura follows the Eraser wherever it goes.
 * 
 * @author Henry Peng
 * @version June 17, 2022
 */
public class SpeedBoostEraser extends Eraser
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
    private SpeedBoostEraserHitbox s;
    private int idleWait;
    private int idlePicNum;
    private int boostWait;
    private int boostPicNum;

    /**
     * Constructor for SpeedBoostEraser. The max speed deterines how fast its default speed would be. This eraser
     * will not erase, so an ink width is not needed.
     * 
     * @param maxSpeed  The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     */
    public SpeedBoostEraser(int maxSpeed)
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
        boostWait = 0;
        boostPicNum = 0;
        numActsChange = min + (int)(Math.random() * max);
        s = new SpeedBoostEraserHitbox(120, this, PaperWorld.GREEN);

    }

    /**
     * It will move in a random direction for specified times and calls on its attack animation
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
            healthStat = new StatBar (health, health, this, 44, 5, getImage().getHeight()/2 + 10, Color.GREEN, Color.RED, true);
            getWorld().addObject(healthStat, 0, 0);

        }

        boostAnimate();

        if (health <= 0){
            getWorld().removeObject(s);
        }

        actCounter++;

        // walks
        walk();

        if (actCounter % 10 == 0)
        {
            healthStat.update (health);
        }

        if (health <= 0)
        {
            getWorld().removeObject (this);
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
        String[] picture = {"Speed Anim (1).png", "Speed Anim (2).png", "Speed Anim (3).png", "Speed Anim (4).png"};

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

    /**
     * Boosting animation for the eraser
     */
    private void boostAnimate()
    {
        String[] picture = {"SpeedBoost anim (1).png", "SpeedBoost anim (2).png", "SpeedBoost anim (3).png", "SpeedBoost anim (4).png", "SpeedBoost anim (5).png", "SpeedBoost anim (6).png", "SpeedBoost anim (7).png", "SpeedBoost anim (8).png", "SpeedBoost anim (9).png", "SpeedBoost anim (10).png", "SpeedBoost anim (11).png", "SpeedBoost anim (12).png", "SpeedBoost anim (13).png", "SpeedBoost anim (14).png", "SpeedBoost anim (15).png", "SpeedBoost anim (16).png", "SpeedBoost anim (17).png", "SpeedBoost anim (18).png", "SpeedBoost anim (19).png"};

        if (boostWait == 7)
        {
            setImage (picture[boostPicNum]);
            //image = getImage();
            //image.scale (60, 50);
            boostPicNum++;
            boostWait = 0;

            if (boostPicNum == 19)
            {
                boostPicNum = 0;
            }
        }

        boostWait++;
    }
}