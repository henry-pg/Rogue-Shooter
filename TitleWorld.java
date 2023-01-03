import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * TitleWorld is the subclass of the world that the code first calls on when it runs.
 * It contains the starting music/sounds and some text that informs the user how to move to the next screen.
 * 
 * @author Charis, Johnathan 
 * @version June 2022
 */
public class TitleWorld extends World {
    private boolean button;
    private boolean button2;
    private boolean button3;
    private boolean button4;
    private boolean pressed;
    private boolean eraser;
    private ArrayList < IdlePencil > pencils;
    private GreenfootSound titleMusic;

    private static boolean firstTime = true;
    private boolean firstTime2;
    private GreenfootSound pageFlip;

    /**
     * Constructor for objects of class TitleWorld.
     * 
     */
    public TitleWorld() {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 600, 1);
        firstTime2 = true;
        button = false;
        button2 = false;
        button3 = false;
        button4 = false;
        pressed = false;
        eraser = false;
        titleMusic = new GreenfootSound("I Was Joking.wav");
        titleMusic.setVolume(40);
        if (!firstTime) {
            titleMusic.playLoop();
        }

        pageFlip = new GreenfootSound("pageFlip.wav");
        pageFlip.setVolume(80);
    }

    public static void firstTimeFalse() {
        firstTime = false;
    }

    /**
     * Stars music when code is running
     */
    public void started() {
        titleMusic.playLoop();
    }

    /**
     * Stops music when code is paused
     */
    public void stopped() {
        titleMusic.pause();
    }

    /**
     * Act method is called when the code is running.
     * Button booleans are set to true to prevent the world from adding buttons indefinitely.
     * Changes the world when spacebar is pressed.
     */
    public void act() {
        if (firstTime2) {
            firstTime2 = false;
            firstTime = true;
        }
        if (!button) {
            addObject(new Play(), 246, 473);
            button = true;
        }

        if (!button2) {
            addObject(new Leftside(), 581, 348);
            button2 = true;
        }

        if (!button3) {
            addObject(new Rightside(), 789, 348);
            button3 = true;
        }

        if (!button4) {
            addObject(new HighScoreButton(), 820, 60);
            button4 = true;
        }

        if (!eraser) {
            addObject(new IdleEraser(), 100, 95);
            eraser = true;
        }

        if (!pressed) {
            if (Greenfoot.isKeyDown("space")) {
                Greenfoot.setWorld(new PaperWorld(Button.getNum()));
                pressed = true;
                titleMusic.stop();
                pageFlip.play();
            }
        }
    }
}