import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the main Pencil class. It has the default stats except for an increased max speed and a smaller ink width.
 * 
 * @author Johnathan Lam
 * @version June 14, 2022
 */
public class LeadPencil extends Pencil
{   
    //frames
    private static GreenfootImage one;
    private static GreenfootImage two;
    private static GreenfootImage three;
    private static GreenfootImage four;
    private static GreenfootImage five;
    private static GreenfootImage six;
    private static GreenfootImage seven;
    
    /**
     * Constructor for LeadPencil. 
     * It would set the max speed the player would move at, the ink width, the starting surface area and the x y offsets.
     * 
     * @param maxSpeed      The speed the pencil will almost always move at. Only when it is sped up by the speed boost buff will their speed change via a multiple of maxSpeed.
     * @param inkWidth      The width of its drawing size, it draws in a square. This is the length / width for the drawing square.
     */
    public LeadPencil (int maxSpeed, int inkWidth)
    {
        super ((int) (maxSpeed * 1.3), (int) (inkWidth / 1.25));
        yOffset = 38;
        xOffset = -3;
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
        GreenfootImage[] picture = {one, two, three, four, five, six, seven};
        
        if (idleWait == 5)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 7)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }
    
    /**
     * For resizing the idle animations to a proper size.
     */
    private static void scaleImages(){
        one = new GreenfootImage("Idle Led (1).png");
        one.scale (24,80);
        
        two = new GreenfootImage("Idle Led (2).png");
        two.scale (24,80);
        
        three = new GreenfootImage("Idle Led (3).png");
        three.scale (24,80);
        
        four = new GreenfootImage("Idle Led (4).png");
        four.scale (24,80);
        
        five = new GreenfootImage("Idle Led (5).png");
        five.scale (24,80);
        
        six = new GreenfootImage("Idle Led (6).png");
        six.scale (24,80);
        
        seven = new GreenfootImage("Idle Led (7).png");
        seven.scale (24,80);
    }
}