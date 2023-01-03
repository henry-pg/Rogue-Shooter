import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the main Pencil class. It has the default stats except for an increased starting area.
 * 
 * @author Johnathan Lam
 * @version June 14, 2022
 */
public class GoldenPen extends Pencil
{
    private boolean firstTime;
    /**
     * Constructor for GoldenPen. 
     * It would set the max speed the player would move at, the ink width, the starting surface area and the x y offsets.
     * 
     * @param maxSpeed      The speed the pencil will almost always move at. Only when it is sped up by the speed boost buff will their speed change via a multiple of maxSpeed.
     * @param inkWidth      The width of its drawing size, it draws in a square. This is the length / width for the drawing square.
     */
    public GoldenPen (int maxSpeed, int inkWidth)
    {
        super (maxSpeed, inkWidth);
        //startingAreaLength = 400;
        firstTime = true;
        yOffset = 55;
        //xOffset = 10;
        //getImage().scale(24, 80);        
        setImage("King Idle (1).png");
    }
    
    /**
     * The default act method - only acts if the game has not declared a GAME OVER yet. The player spawns with
     * a handgun. This also checks whether it is in the radius of the SlowerEraser and the PusherEraser and will act accordingly.
     * This also allows the Pencil to move and draw on the page and have an idle animation.
     */
    public void act()
    {
        super.act();
        if (firstTime)
        {
            firstTime = false;
            startingAreaLength *= 1.23;
        }
    }

    /**
     * For the idle animation
     */
    public void idleAnimate()
    {
        String[] picture = {"King Idle (1).png", "King Idle (2).png", "King Idle (3).png", "King Idle (4).png"};
        
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