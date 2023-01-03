import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * IdleEraser is the class that contains the idle animation for the eraser on the TitleWorld.
 * 
 * @author Charis 
 * @version June 2022
 */
public class IdleEraser extends Actor
{
    private static GreenfootImage one;
    private static GreenfootImage two;
    private static GreenfootImage three;
    private static GreenfootImage four;
    private int idleWait;
    private int idlePicNum;
    
    /**
     * The main constructor for the IdleEraser class
     * It scales the images and sets the first image as the first image of the animation frames.
     */
    public IdleEraser()
    {
        scaleImages();
        setImage(one);
    }
    
    /**
     * The act method is called when the code is running.
     */
    public void act()
    {
        // Add your action code here.
        idleAnimate();
    }
    
    /**
     * A method used to scale the images and assign them to a variable.
     */
    public void scaleImages(){
        one = new GreenfootImage("Normal Idle (1).png");
        one.scale (126,154);
        
        two = new GreenfootImage("Normal Idle (2).png");
        two.scale (126,154);
        
        three = new GreenfootImage("Normal Idle (3).png");
        three.scale (126,154);
        
        four = new GreenfootImage("Normal Idle (4).png");
        four.scale (126,154);
    }
    
    /**
     * A method used to animate the image by changing the animation frame every few acts.
     */
    public void idleAnimate()
    {
        GreenfootImage[] picture = {one, two, three, four};
        
        if (idleWait == 12)
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
