import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Button is the superclass of the buttons.
 * It contains the sound and methods most buttons use.
 * 
 * @author Charis, Johnathan 
 * @version June 2022
 */
public abstract class Button extends Actor
{
    private boolean firstTime;
    protected static int num;
    protected ArrayList<IdlePencil> pencils;
    protected GreenfootSound[] buttons;
    protected int buttonsSoundsIndex;
    
    /**
     * The main constructor for the Button class.
     * It contains the sounds for the buttons and initializes a few variables.
     */
    public Button()
    {
        num = 0;
        firstTime = true;
        buttonsSoundsIndex = 0;
        buttons = new GreenfootSound[20];
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new GreenfootSound ("seedlift.wav");
            buttons[i].setVolume(75);
        }

    }
    
    /**
     * Act method that runs when the code is running.
     * Adds the first idle pencil and its description
     */
    public void act()
    {        
        if(firstTime)
        {
            getWorld().addObject(new IdleNormalPencil(), 693, 348);
            getWorld().addObject (new DefaultPencilDes(), 693, 500);
            firstTime = false;
        }
    }
    
    /**
     * Getter method to get the num variable.
     */
    public static int getNum()
    {
        return num;
    }
    
    /**
     * Method to remove all the pencils from the IdlePencil class from the world.
     */
    public void removePencils()
    {
        pencils = (ArrayList)getWorld().getObjects(IdlePencil.class);
        for (IdlePencil p : pencils)
        {
            getWorld().removeObject(p);
        }
    }
}