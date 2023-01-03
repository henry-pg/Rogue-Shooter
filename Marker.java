import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the main Pencil class. It has the default stats except for an increased ink width and a decreased max speed.
 * 
 * @author Johnathan Lam
 * @version June 14, 2022
 */
public class Marker extends Pencil
{
    // frames
    private static GreenfootImage one;
    private static GreenfootImage two;
    private static GreenfootImage three;
    private static GreenfootImage four;
    
    /**
     * Constructor for Marker. 
     * It would set the max speed the player would move at, the ink width, the starting surface area and the x y offsets.
     * 
     * @param maxSpeed      The speed the pencil will almost always move at. Only when it is sped up by the speed boost buff will their speed change via a multiple of maxSpeed.
     * @param inkWidth      The width of its drawing size, it draws in a square. This is the length / width for the drawing square.
     */
    public Marker (int maxSpeed, int inkWidth)
    {
        super ((int) (maxSpeed / 1.2), (int)(inkWidth * 1.25));
        yOffset = 38;
        xOffset = 1;
        scaleImages();
        setImage(one);
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
        GreenfootImage[] picture = {one, two, three, four};
        
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
     * For resizing the idle animations to a proper size.
     */
    private static void scaleImages()
    {
        one = new GreenfootImage("Marker idle (1).png");
        one.scale (24,80);
        
        two = new GreenfootImage("Marker idle (2).png");
        two.scale (24,80);
        
        three = new GreenfootImage("Marker idle (3).png");
        three.scale (24,80);
        
        four = new GreenfootImage("Marker idle (4).png");
        four.scale (24,80);
    }
}