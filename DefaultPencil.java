import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the main Pencil class. It has the default stats.
 * 
 * @author Johnathan Lam
 * @version June 14, 2022
 */
public class DefaultPencil extends Pencil
{
    /**
     * Constructor for DefaultPencil. 
     * It would set the max speed the player would move at, the ink width, the starting surface area and the x y offsets.
     * 
     * @param maxSpeed      The speed the pencil will almost always move at. Only when it is sped up by the speed boost buff will their speed change via a multiple of maxSpeed.
     * @param inkWidth      The width of its drawing size, it draws in a square. This is the length / width for the drawing square.
     */
    public DefaultPencil(int maxSpeed, int inkWidth)
    {
        super (maxSpeed, inkWidth);
        // to draw where the lead hits the paper
        yOffset = 34;
        xOffset = 4;
        setImage("Orig Idle (1).png");
    }
    
    /**
     * The default act method - only acts if the game has not declared a GAME OVER yet. The player spawns with
     * a handgun. This also checks whether it is in the radius of the SlowerEraser and the PusherEraser and will act accordingly.
     * This also allows the Pencil to move and draw on the page and have an idle animation.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * For the idle animation
     */
    public void idleAnimate()
    {
        
        String[] picture = {"Orig Idle (1).png", "Orig Idle (2).png", "Orig Idle (3).png", "Orig Idle (4).png", "Orig Idle (5).png", "Orig Idle (6).png", "Orig Idle (7).png", "Orig Idle (8).png", "Orig Idle (9).png", "Orig Idle (10).png", "Orig Idle (11).png", "Orig Idle (12).png", };
        
        if (idleWait == 4)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 12)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
        
    }
}