import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Buffs is the superclass of the buffs.
 * It contains the sounds and methods used
 * 
 * @author Charis, Johnathan
 * @version June 2022
 */
public class Buffs extends Actor {
    protected boolean isInvisible;
    protected boolean stillInWorld;
    private int actCount;
    private int lastingCount;
    private int transparencyCount;
    private int transparency;
    protected GreenfootSound pickUp;

    /**
     * The main constructor for the Buffs class.
     * It contains several act counters, sounds, and image transparencys.
     */
    public Buffs() {
        transparency = 0;
        transparencyCount = 0;
        getImage().setTransparency(transparency);
        isInvisible = true;
        stillInWorld = false;
        actCount = 0;
        lastingCount = 0;
        pickUp = new GreenfootSound("pickup1.wav");
        pickUp.setVolume(80);
    }

    /**
     * The act method runs when the code is running.
     * It counts the acts to add a hitboxes and removes objects after a certain amount of acts.
     */
    public void act() {
        // Add your action code here.    
        actCount++;
        if (isInvisible) {
            if (actCount % 4 == 0 && transparency <= 240) {
                getImage().setTransparency(transparency += 15);
            } else if (transparency == 255) {
                isInvisible = false;
                stillInWorld = true;
            }

            if (stillInWorld && lastingCount >= 240) {
                stillInWorld = false;
                getWorld().removeObject(this);
            }
        } else {
            lastingCount++;
            getImage().setTransparency(255);
            transparencyCount++;
            //makes the image flash eevry 7 acts
            if (transparencyCount >= 100 && transparencyCount < 240 && transparencyCount % 7 == 0) {
                getImage().setTransparency(0);
            }
            if (stillInWorld && lastingCount >= 240) {
                stillInWorld = false;
                ((PaperWorld)(getWorld())).setPlayerBuffedFalse();
                ((PaperWorld)(getWorld())).setWeaponBuffedFalse();
                getWorld().removeObject(this);
            }
        }
    }

    /**
     * A method to get the lastingCount.
     */
    public int getLastingCount() {
        return lastingCount;
    }
}