import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Hitbox for the pusher eraser
 * 
 * @author Henry
 * @version June 10
 */
public class PusherEraserHitbox extends Hitbox
{   
    /**
     * This constructor reuses the superclass' constructor
     */
    public PusherEraserHitbox(int radius, PusherEraser owner, Color c){
        super(radius, owner, c);
    }
    
    /** 
     * This act method reuses the superclass' act method.
     */
    public void act()
    {
        super.act();
    }
    
    /**
     * Getter method to return the owner of this hitbox.
     * 
     * @return PusherEraser returns the PusherEraser instance that owns this hitbox instance.
     */
    public PusherEraser getOwner(){
        return (PusherEraser)owner;   
    }
}
