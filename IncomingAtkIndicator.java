import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An attack indicator used specifically for the Grower Eraser. Displays an incoming indicator of the Grower Erasers next attack. 
 * The indicator is added in as an object and removed when the attack is done.
 * 
 * @author William Tang 
 * @version June 22, 2022
 */
public class IncomingAtkIndicator extends Actor
{
    private GreenfootImage indicatorBox;
    private int actCounter;
    /**
     * Constructor for IncomingAtkIndicator. Takes in the size of the attack and draws a rectangle indicator(Square).
     * @param defaultWidth  The size of the box
     */
    public IncomingAtkIndicator(int defaultWidth){
        indicatorBox = new GreenfootImage(defaultWidth, defaultWidth);
        indicatorBox.setColor(Color.RED);
        indicatorBox.fill(); 
        indicatorBox.setColor(Color.YELLOW);
        indicatorBox.drawRect(0,0, defaultWidth, defaultWidth); 
        indicatorBox.setTransparency(70);
        setImage(indicatorBox); 
        actCounter = 100;
    }
    
    /**
     * Fades the indicator 
     */
    public void act()
    {
        if (actCounter > 0){
            actCounter--;
            if (actCounter < 60){ // fade image
                indicatorBox.setTransparency (actCounter / 2);
            }
        } 

    }
}