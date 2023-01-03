import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The AttackSpeedBoost subclass that speeds up how fast the guns shoot.
 * It contains the sounds and the methods to speed up the fire rate.
 * 
 * @author Charis, Johnathan 
 * @version June 2022
 */
public class AttackSpeedBoost extends Buffs
{
    /**
     * The constructor for the attackspeed buff.
     * It calls on the super class's constructor.
     */
    public AttackSpeedBoost()
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
            Weapon w = (Weapon)getOneIntersectingObject(Weapon.class);
            if(stillInWorld && w != null)
            {
                w.attackSpeedUp();
                pickUp.play();
                getWorld().addObject(new AttackSpeedBoostIcon(), 750, 20); 
                getWorld().removeObject(this);
            }
        }
    }
}
