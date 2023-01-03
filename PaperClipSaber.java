import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * PaperClipSaber is a melee weapon that deals high damage to all erasers that
 * it touches
 * 
 * @author Ethan Lin
 * @version June 2022
 */

public class PaperClipSaber extends Weapon
{
    // declare variables
    private int actCounter;
    private int attackStage;
    private boolean attacking;
    private boolean rightSide;
    private int damage;
    private GreenfootSound[] swing;
    private int swingSoundsIndex;

    /**
     * Constructor for PaperClipSaber. Calls the superclass' constructor and
     * sets other important variables
     * 
     * @param owner     The player that the PaperClipSaber follows
     */
    public PaperClipSaber (Pencil owner)
    {
        super (owner, 10, 10, 20);
        actCounter = 0;
        attackStage = 1;
        attacking = false;
        rightSide = true;
        damage = 50;
        swingSoundsIndex = 0;
        swing = new GreenfootSound[20];
        for (int i = 0; i < swing.length; i++)
        {
            swing[i] = new GreenfootSound ("energySwing.wav");
            swing[i].setVolume(70);
        }
    }

    /**
     * Act - do whatever the Lightsaber wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        try 
        {
            if (!PaperWorld.getGameOver())
            {
                // same action methods as superclass
                turnTowardsMouse();
                flipImage();
                followPlayer();

                try 
                {
                    // if the mouse is clicked and not currently attacking, start
                    // attacking animation
                    if (Greenfoot.mousePressed(null) && !attacking)
                    {
                        attacking = true;
                    }
                    else if (attacking)
                    {
                        shoot();
                    }
                } 
                catch (NullPointerException e) 
                {
                    // if no mouse present, do nothing
                }
            }
        } 
        catch (NullPointerException e) 
        {
            // if there is no owner, do nothing until player touches
            try 
            {
                // set pencil as owner
                Pencil p = (Pencil)getOneIntersectingObject(Pencil.class);
                player = p;
                getImage().scale(22, 60);
                p.setWeapon(this);
            } 
            catch (NullPointerException ex) 
            {
                // removes the weapon after a set amount of time passes
                notPickedUpTimer++;
                getImage().setTransparency(255);
                if (notPickedUpTimer >= 450 && notPickedUpTimer < 600 && notPickedUpTimer % 7 == 0) {
                    getImage().setTransparency(0);
                }
                if (notPickedUpTimer >= 600)
                {
                    getWorld().removeObject(this);
                }
                // if no player present, do nothing
            }
        }
        actCounter++;
    }

    /**
     * Shoot method for PaperClipSaber. When mouse is clicked, goes through 
     * all of the attacking animations and deals damage to the eraser
     */
    public void shoot()
    {
        if(!Pencil.isStunned){
            // goes to next frame of animation every 2 acts
            if (actCounter%2 == 0) 
            {
                // set image to next frame
                setImage("Sword (" + attackStage + ").png");
                attackStage++;

                // when animation first starts, deal damage to eraser
                if (attackStage == 4)
                {
                    swing[swingSoundsIndex].play();
                    swingSoundsIndex++;
                    if (swingSoundsIndex > swing.length - 1)
                    {
                        swingSoundsIndex = 0;
                    }
                    ArrayList <Eraser> e = new ArrayList<Eraser>(getIntersectingObjects(Eraser.class));
                    if (e.size()!=0)
                    {
                        for (Eraser eraser:e)
                        {
                            eraser.takeDamage(damage);
                        }
                    }
                }

                // when final frame of animation reached, reset image and set
                // attacking boolean to false
                if (attackStage == 14)
                {
                    attackStage = 1;
                    setImage("Sword (1).png");
                    attacking = false;
                }

                shootingTimer--;
            }
        }
    }

    /**
     * Flips image if the mouse moves to the other side of the weapon
     */
    public void flipImage()
    {
        try 
        {
            // if mouse is on the right side of the weapon, set rightSide to true
            if (mouse.getX()>this.getX()+20)
            {
                rightSide = true;
            }
            else if (mouse.getX()<=this.getX()-20)
            {
                rightSide = false;
            }
        } 
        catch (NullPointerException e) 
        {
            // if no mouse present, do nothing
        }

        // based on the location of the mouse, set image and offset if not
        // in the process of attacking
        if (rightSide && !attacking)
        {
            xOffset = 20;
            setImage("Sword (1).png");
        } 
        else if (!rightSide && !attacking)
        {
            xOffset = -20;
            setImage("Sword (1) Flipped.png");
        }
    }
}