import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is a simple hitbox class that creates a circular hitbox using arguments that can customize the colour, size and owner of the hitbox.
 * 
 * @author Henry 
 * @version June 10
 */
public abstract class Hitbox extends Actor
{
    //initialize values
    protected Eraser owner;
    
    /**
     * This is the constructor for the hitbox. 
     * 
     * @param radius This is an integer that represents the radius of the hitbox.
     * @param owner This is to represent the instance of an eraser that this hitbox would be attached to.
     * @param c This represents the colour of the hitbox.
     */
    public Hitbox(int radius, Eraser owner, Color c){
        //intializing values
        this.owner = owner;
        int diameter = radius*2;
        
        //This creates the actual image for the hitbox.
        GreenfootImage image = new  GreenfootImage(diameter, diameter);
        image.setColor(c);
        image.drawOval(0, 0, diameter, diameter);
        image.fillOval(0, 0, diameter, diameter);
        
        this.setImage(image);
        
        
    }

    /**
     * Act method for this class. 
     */
    public void act()
    {
        //The location of the hitbox is set to the location of its owner every act.
        if (owner != null)
        {
            setLocation(owner.getX(), owner.getY());
        }
        else
        {
            getWorld().removeObject(this);
        }
    }
    
    
}