import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Stun actor to display over the Pencil actor when stunned. The class will play an animation and sound when stunned.
 * 
 * @author William
 * @version June 22, 2022 
 */
public class Stun extends Actor
{
    private int idleWait;
    private int idlePicNum; 
    private int yOffset; 
    private int actCounter;
    private Pencil owner; 
    private GreenfootSound Stun;
    /**
     * Constructor for Stun
     */
    public Stun(Pencil owner){
        this.owner = owner; 
        yOffset = owner.getImage().getHeight()/2+5; 
        actCounter = 120;
        
        Stun = new GreenfootSound ("stun.wav");
    }
    
    /**
     * Sets the location of the actor and calls the stunAnimate method to play the stun animation
     */
    public void act()
    {
        actCounter--;
        Stun.play(); 
        stunAnimate();
        setLocation(owner.getX(), owner.getY()-yOffset);
        if(actCounter == 0)
        {
            getWorld().removeObject(this);
            Stun.stop();
        }
    }
    
    /**
     * For the stun Animation
     */
    public void stunAnimate()
    {
        
        String[] picture = {"Stun (1).png", "Stun (2).png", "Stun (3).png" };
        
        if (idleWait == 4)
        {
            setImage (picture[idlePicNum]);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 3)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
        
    }
}
