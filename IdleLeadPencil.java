import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * IdleLeadPencil is the subclass that contains the idle animation for the lead pencil on the TitleWorld.
 * 
 * @author Charis 
 * @version June 2022
 */
public class IdleLeadPencil extends IdlePencil
{
    private static GreenfootImage five;
    private static GreenfootImage six;
    private static GreenfootImage seven;
    
    /**
     * The main constructor for the IdleLeadPencil class
     * It calls on the super class's constructor.
     */
    public IdleLeadPencil()
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
        one = new GreenfootImage("Idle Led (1).png");
        one.scale (48,160);
        
        two = new GreenfootImage("Idle Led (2).png");
        two.scale (48,160);
        
        three = new GreenfootImage("Idle Led (3).png");
        three.scale (48,160);
        
        four = new GreenfootImage("Idle Led (4).png");
        four.scale (48,160);
        
        five = new GreenfootImage("Idle Led (5).png");
        five.scale (48,160);
        
        six = new GreenfootImage("Idle Led (6).png");
        six.scale (48,160);
        
        seven = new GreenfootImage("Idle Led (7).png");
        seven.scale (48,160);
    }
    
    /**
     * A method used to animate the image by changing the animation frame every few acts.
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
}
