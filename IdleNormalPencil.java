import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * IdleNormalPencil is the subclass that contains the idle animation for the default pencil on the TitleWorld.
 * 
 * @author Charis 
 * @version June 2022
 */
public class IdleNormalPencil extends IdlePencil
{
    private static GreenfootImage five;
    private static GreenfootImage six;
    private static GreenfootImage seven;
    private static GreenfootImage eight;
    private static GreenfootImage nine;
    private static GreenfootImage ten;
    private static GreenfootImage eleven;
    private static GreenfootImage twelve;
    
    /**
     * The main constructor for the IdleNormalPencil class
     * It calls on the super class's constructor.
     */
    public IdleNormalPencil()
    {
        super();
    }
    
    /**
     * The act method is called when the code is running.
     * It calls on the super class's act method.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    
    /**
     * A method used to scale the images and assign them to a variable.
     */
    public void scaleImages(){
        one = new GreenfootImage("Orig Idle (1).png");
        one.scale (48,160);
        
        two = new GreenfootImage("Orig Idle (2).png");
        two.scale (48,160);
        
        three = new GreenfootImage("Orig Idle (3).png");
        three.scale (48,160);
        
        four = new GreenfootImage("Orig Idle (4).png");
        four.scale (48,160);
        
        five = new GreenfootImage("Orig Idle (5).png");
        five.scale (48,160);
        
        six = new GreenfootImage("Orig Idle (6).png");
        six.scale (48,160);
        
        seven = new GreenfootImage("Orig Idle (7).png");
        seven.scale (48,160);
        
        eight = new GreenfootImage("Orig Idle (8).png");
        eight.scale (48,160);
        
        nine = new GreenfootImage("Orig Idle (9).png");
        nine.scale (48,160);
        
        ten = new GreenfootImage("Orig Idle (10).png");
        ten.scale (48,160);
        
        eleven = new GreenfootImage("Orig Idle (11).png");
        eleven.scale (48,160);
        
        twelve = new GreenfootImage("Orig Idle (12).png");
        twelve.scale (48,160);
    }
    
    /**
     * A method used to animate the image by changing the animation frame every few acts.
     */
    public void idleAnimate()
    {
        GreenfootImage[] picture = {one, two, three, four, five, six, seven, eight, nine, ten, eleven, twelve};
        
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
