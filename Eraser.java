import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.lang.Math.*;

/**
 * This is the base of all eraser classes. There are 3 types of erasers - an eraser type that removes 
 * the territory of the pencil, another that boosts the stats of other nearby erasers and one that attacks the pencil directly.
 * They can be killed through the weapons the pencil utilizes. Their HP can be shown above their heads only when they are damaged.
 * 
 * @author Johnathan Lam 
 * @version June 16, 2022
 */
public abstract class Eraser extends Actor
{
    protected int speed;
    protected int maxSpeed;
    protected int eraseWidth;
    protected int maxHealth;
    protected int health;
    protected int effectDuration;
    protected boolean speedUp;
    protected StatBar healthStat;
    protected boolean firstTime;
    protected int actCounter;
    
    /**
     * Main constructor for the Eraser class. This would be the blueprint for all or almost all of the eraser subclasses.
     * It would set the max speed the eraser would move at, the eraser width and the max health it comes with.
     * 
     * @param maxSpeed      The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     * @param eraserWidth   The width of its erasing size, it erases in a square. This is the length / width for the erasing square.
     * @param maxHealth     The max health the eraser will spawn with in when it spawns in.
     */
    public Eraser (int maxSpeed, int eraseWidth, int maxHealth)
    {
        this.maxSpeed = maxSpeed;
        this.speed = maxSpeed;
        this.eraseWidth = eraseWidth;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        firstTime = true;
        
        speedUp = false;
        effectDuration = 0;
        actCounter = 0;
        
        healthStat = new StatBar (health, health, this, 44, 5, 38, Color.GREEN, Color.RED, true);
    }
    
    /**
     * The default act method - only walks
     */
    public void act()
    {
        if (firstTime)
        {
            healthStat = new StatBar (health, health, this, 44, 5, getImage().getHeight()/2 + 10, Color.GREEN, Color.RED, true);
            getWorld().addObject(healthStat, 0, 0);
            firstTime = false;
        }
        
        walk();
        effectDuration --;
        SpeedBoostEraserHitbox hitbox = (SpeedBoostEraserHitbox)getOneObjectAtOffset(0,0,SpeedBoostEraserHitbox.class);
        
        if (hitbox != null)
        {
            speedUp = true;
            effectDuration = 100;
        }
        
        if (speedUp && effectDuration > 0)
        {
            speed = maxSpeed + 2;
        }
        else if (effectDuration < 0 && speedUp)
        {
            speedUp = false;
            speed = maxSpeed;
        }
        
        if (actCounter % 10 == 0)
        {
            healthStat.update (health);
        }
        
        if (health <= 0)
        {
            getWorld().removeObject (this);
        }
    }
    
    /**
     * For every Eraser subclass, they should have a dedicated walk method to move around the world.
     */
    public abstract void walk();
    
    
    /**
     * Allows the erasers to take damage from the weapons of the pencil. If the health falls below or equals 0, it will die. 
     * 
     * @param damage    The amount of damage the weapon deals
     */
    public void takeDamage (int damage)
    {
        health -= damage;
    }
}
