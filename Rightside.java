import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Leftside is the subclass of Button.
 * It contains the method used to switch the idle pencil image towards the left.
 * 
 * @author Charis, Johnathan 
 * @version June 2022
 */
public class Rightside extends Button
{
    /**
     * The constructor for the Rightside button.
     * It calls on the super class's constructor.
     */
    public Rightside()
    {
        super();
    }

    /**
     * The act method runs when the code is running.
     * It switches the pencil image towards the right.
     */
    public void act()
    {
        // Add your action code here.
        if (Greenfoot.mousePressed(this))
        {
            buttons[buttonsSoundsIndex].play();
            buttonsSoundsIndex++;
            if (buttonsSoundsIndex > buttons.length - 1)
            {
                buttonsSoundsIndex = 0;
            }
            getImage().setTransparency(120);
            if(num < 4)
            {
                num++;
            }
            else if(num == 4)
            {
                num = 0;
            }
            if(num == 0)
            {
                removePencils();
                getWorld().addObject(new IdleNormalPencil(), 693, 348);
                getWorld().addObject (new DefaultPencilDes(), 693, 500);
            }
            else if(num == 1)
            {
                removePencils();
                getWorld().addObject(new IdleLeadPencil(), 693, 348);
                getWorld().addObject (new LeadPencilDes(), 693, 500);
            }
            else if(num == 2)
            {
                removePencils();
                getWorld().addObject(new IdleMarker(), 693, 348);
                getWorld().addObject (new MarkerDes(), 693, 500);
            }
            else if(num == 3)
            {
                removePencils();
                getWorld().addObject(new IdleKing(), 693, 348);
                getWorld().addObject (new GoldenPenDes(), 693, 500);
            }
            else if(num == 4)
            {
                removePencils();
                getWorld().addObject(new IdleQuill(), 693, 348);
                getWorld().addObject (new QuillDes(), 693, 500);
            }
        }
        else
        {
            getImage().setTransparency(255);            
        }
    }
}