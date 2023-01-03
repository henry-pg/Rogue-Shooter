import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The InkWidthBoost subclass that increases the pencil's speed.
 * It contains the sounds and the methods to increase the speed.
 * 
 * @author Charis, Johnathan 
 * @version June 2022
 */
public class SpeedBoost extends Buffs
{
    private GreenfootSound zoom; 
    private GreenfootSound Allen;
    private int randomNumber;
    /**
     * The constructor for the InkWidthBoost buff.
     * It calls on the super class's constructor.
     * It also contains sound effects and a rare sound effect that plays when the buff is picked up.
     */
    public SpeedBoost()
    {
        super();
        zoom = new GreenfootSound ("Zoomy.wav");
        Allen = new GreenfootSound("Pencil Allen.wav");
        Allen.setVolume(50); 
        zoom.setVolume(50);
        randomNumber = Greenfoot.getRandomNumber(100);
        
    }

    /**
     * The act method that calls on the super class's constructor.
     * It also gives the object a hitbox before removing the object after a certain amount of acts.
     */
    public void act()
    {
        super.act();
        if(!isInvisible && getLastingCount() < 240)
        {
            Pencil p = (Pencil)getOneIntersectingObject(Pencil.class);
            if(stillInWorld && p != null)
            {
                p.speedUp();
                pickUp.play();
                zoom.play();
                if(randomNumber == 1){
                    Allen.play();
                }
                getWorld().addObject(new SpeedBoostIcon(), 850, 20); 
                getWorld().removeObject(this);
            }
        }
    }
}