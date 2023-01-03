import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
 
/**
 * ButtonTitleScreen is a button that, when clicked on, will bring the player to the title screen.
 * 
 * @author Johnathan Lam 
 * @version June 2022
 */
public class ButtonTitleScreen extends Actor
{
    private SimpleTimer timer = new SimpleTimer();
    private boolean timerStarted = false;
    private GreenfootSound pressed;
    private GreenfootSound pageFlip;
    
    /**
     * The constructor for the ButtonTitleScreen class. When it is clicked on, it will play a page flip sound.
     */
    public ButtonTitleScreen()
    {
        //pressed = new GreenfootSound ("shovel.wav");   
        //pressed.setVolume(65);
        pageFlip = new GreenfootSound ("pageFlip.wav");
        pageFlip.setVolume (80);
    }
 
    /**
     * Checks whether it is clicked on, and if it is, return to the title screen and stop the main game music
     */
    public void act()
    {
        if (Greenfoot.mousePressed(this))
        {
            //setImage ("TitleScreenButton-pressed.png");
            getImage().setTransparency(120);
            PaperWorld.stopMusic();
            timer.mark();
            //pressed.play();
            timerStarted = true;
            TitleWorld.firstTimeFalse();
            pageFlip.play();
        }
        else
        {
            //setImage ("TitleScreenButton.png");
            getImage().setTransparency(255);
        }
 
        if(timer.millisElapsed() > 180 && timerStarted)
        {
            Greenfoot.setWorld (new TitleWorld());
        }
    }
}