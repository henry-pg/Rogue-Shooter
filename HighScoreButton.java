import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Clicking this button will open the High Score Screen.
 * 
 * @author Ethan Lin
 * @version June 2022
 */
public class HighScoreButton extends Button
{
    private GreenfootSound click;
    
    public HighScoreButton()
    {
        super();
        click = new GreenfootSound ("shovel.wav");
        click.setVolume (70);
    }
    
    /**
     * When the HighScoreButton is clicked, it will add a new 
     * HighScoreScreen to the world.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            click.play();
            getImage().setTransparency(120);
            getWorld().addObject(new HighScoreScreen(), 450, 300);
        } else {
            getImage().setTransparency(255);
        }
    }
}
