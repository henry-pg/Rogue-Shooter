import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The InkWidthBoost subclass that increases the ink width size.
 * It contains the sounds and the methods to increase the ink width size.
 * 
 * @author Charis, Johnathan 
 * @version June 2022
 */
public class InkWidthBoost extends Buffs
{
    /**
     * The constructor for the InkWidthBoost buff.
     * It calls on the super class's constructor.
     */
    public InkWidthBoost()
    {
        super();
    }

    /**
     * The act method that calls on the super class's constructor.
     * It also gives the object a hitbox before removing the object after a certain amount of acts.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
        if(!isInvisible && getLastingCount() < 240)
        {
            Pencil p = (Pencil)getOneIntersectingObject(Pencil.class);
            if(stillInWorld && p != null)
            {
                p.increaseInkWidth();
                pickUp.play();
                getWorld().addObject(new InkWidthBoostIcon(), 800, 20); 
                getWorld().removeObject(this);
            }
        }
    }
}
