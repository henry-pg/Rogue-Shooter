import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * GameOverScreen is an image that overlays on top of the main game screen when the player loses.
 * 
 * @author Johnathan Lam 
 * @version June 2022
 */
public class GameOverScreen extends Actor
{
    private boolean button1;
    
    /**
     * The constructor for the GameOverScreen class. 
     */
    public GameOverScreen()
    {
        button1 = false;
    }
 
    /**
     * Checks if there is a ButtonTitleScreen in the world, if there is not, then spawn one in
     */
    public void act()
    {
        //PaperWorld.stopMusic();
        if(!button1)
        {
            getWorld().addObject(new ButtonTitleScreen(), 450, 330);
            //PaperWorld.stopMusic();
            button1 = true;
        }
    }
}