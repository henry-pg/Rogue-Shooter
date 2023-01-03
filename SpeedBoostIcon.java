import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animates and displays image for the SpeedBoost buff icon
 * 
 * @author (William) 
 * @version (June 23, 2022)
 */
public class SpeedBoostIcon extends BuffIcon
{
    /**
     * Calls the parent class constructor template
     * Initializes Variables 
     */
    public SpeedBoostIcon(){
        super(); 
       
    }
    
    /**
     *The act method determines when the object is removed from the world and when the Icon will begin fading out.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * scaleImages() is a method to initialize images to variables and scale them if needed
     */
    public void scaleImages(){
        one = new GreenfootImage("Speed Buff (1).png");
       
        two = new GreenfootImage("Speed Buff (2).png");
        
        three = new GreenfootImage("Speed Buff (3).png");
       
        four = new GreenfootImage("Speed Buff (4).png");
    
    }
    
    /**
     * idleAnimate() is a method used to iterate through images in an array and animate them.
     */
    public void idleAnimate()
    {
        GreenfootImage[] picture = {one, two, three, four};
        
        if (idleWait == 7)
        {
            setImage (picture[idlePicNum]);
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
