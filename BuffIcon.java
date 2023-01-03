import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Parent class BuffIcon is used for inheritance of variables, constructor, actor and methods of the child classes. 
 * The class spawns in Icons to display at the top right of the screen and disappears in correlation to how long the buff lasts
 * 
 * @author (William Tang) 
 * @version (June 22, 2022)
 */
public abstract class BuffIcon extends Actor
{
    protected  GreenfootImage one;
    protected  GreenfootImage two;
    protected  GreenfootImage three;
    protected  GreenfootImage four;
    protected int idleWait;
    protected int idlePicNum;  

    protected boolean stillInWorld;
    protected int actCount;
    protected int lastingCount;
    protected int transparencyCount;
    protected int transparency;

    /**
     * Constructor for BuffIcon
     * Initializes Variables 
     */
    public BuffIcon(){
        transparency = 255;
        transparencyCount = 0;
        getImage().setTransparency(transparency);
        lastingCount = 0;

        scaleImages();
        setImage(one);
    }

    /**
     *The act method determines when the object is removed from the world and when the Icon will begin fading out.
     */
    public void act()
    {
        idleAnimate();
        
        if(lastingCount >= 100)
        {
            getWorld().removeObject(this);
        }

        else
        {
            lastingCount++;
            getImage().setTransparency(255);
            transparencyCount++;
            if(transparencyCount >= 50 && transparencyCount <100 && transparencyCount % 7 == 0)
            {
                getImage().setTransparency(0);
            }
       
        }
     
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
