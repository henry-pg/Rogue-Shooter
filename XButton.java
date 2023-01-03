import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class XButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class XButton extends Button
{
    private HighScoreScreen owner;
    private GreenfootSound click;
    
    /**
     * Constructor for XButton.
     * 
     * @param owner     The HighScoreScreen that this XButton is
     *                  responsible for removing
     */
    public XButton (HighScoreScreen owner)
    {
        this.owner = owner;
        click = new GreenfootSound ("shovel.wav");
        click.setVolume (70);
    }

    /**
     * If the XButton is clicked, it will remove the HighScoreScreen,
     * the text, and itself from the world.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            click.play();
            for (int i = 0; i < 5; i++)
                {
                    getWorld().showText(null, 375, 175 + i*80);
                    getWorld().showText(null, 650, 175 + i*80);
                }
            getWorld().removeObject(owner);
            getWorld().removeObject(this);
        }
    
    }
}
