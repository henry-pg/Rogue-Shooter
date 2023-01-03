import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Animates and displays image for the SpeedDebuff buff icon
 * 
 * @author (William) 
 * @version (June 23, 2022)
 */
public class SpeedDebuffIcon extends BuffIcon
{
    /**
     * Calls the parent class constructor template
     * Initializes Variables 
     */
    public SpeedDebuffIcon(){
        super(); 
       
    }
    /**
     *The act method determines when the object is removed from the world and when the Icon will begin fading out.
     */
    public void act()
    {
        super.act();
    }
    public void scaleImages(){
        one = new GreenfootImage("Slow Debuff (1).png");
       
        two = new GreenfootImage("Slow Debuff (2).png");
        
        three = new GreenfootImage("Slow Debuff (3).png");
       
        four = new GreenfootImage("Slow Debuff (4).png");
    
    }
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
