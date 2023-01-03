import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * IdlePencil is the super class that contains the idle animation for the pencils on the TitleWorld.
 * 
 * @author Charis 
 * @version June 2022
 */
public abstract class IdlePencil extends Actor
{
    //frames
    protected static GreenfootImage one;
    protected static GreenfootImage two;
    protected static GreenfootImage three;
    protected static GreenfootImage four;
    protected int idleWait;
    protected int idlePicNum;

    /**
     * The main constructor for the IdlePencil class
     * It scales the images and sets the first image as the first image of the animation frames.
     */
    public IdlePencil()
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
     * scaleImages() is a method for each child class to initialize images to variables and scale them if needed
     */
    public abstract void scaleImages();

    /**
     * idleAnimate() is a method used to iterate through images in an array and animate them.
     */
    public abstract void idleAnimate();
}
