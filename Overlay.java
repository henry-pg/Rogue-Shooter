import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This is the class that controls and draws all of the pixels involved with simulating writing and erasing for the 
 * game. This method also calculates the total surface area that the pencil has drawn for on the screen. Generally, the 
 * way that this class works is there is a 2D boolean array that stores that state of all the pixels in the overlay. If 
 * the pixel is transparent, the value is false. If the pixel is coloured, the value is true. There are write and erase 
 * methods to change the values in this 2D boolean array based on the pencil and eraser classes. Then there are methods that actually
 * read the 2D array and visually implement the changes. Since this task is relatively demanding, the methods that actually 
 * implement the visual changes are split into two: left and right. One implements the visual changes on the right side of the screen
 * and the other implements the visual changes on the left side. Every other act, one of the two methods are called to reduce
 * the use of resources. 
 * 
 * @author Henry and Johnathan
 * @version (June 5)
 */
public class Overlay extends Actor {
    // variables 
    private static GreenfootImage overlayImage;
    private static int blackCount;
    private double surfaceAreaPercent;
    private static boolean[][] colorGrid;
    private static double surfaceArea;
    private static final double totalPixels = 900 * 600;
    private int surfaceAreaCount;
    private int actCount;

    private static double surfaceAreaPercentS;

    private int px;
    private int py;
    private int pw;
    private String dir;

    private static int overlayHeight;
    private static int overlayWidth;

    private static int black;
    private static int transparent;

    private boolean firstTime = true;

    /**
     * Constructor for Overlay class
     */
    public Overlay() {
        overlayImage = new GreenfootImage("transparent.png");
        colorGrid = new boolean[600][900];
        surfaceAreaCount = 10;

        overlayHeight = getImage().getHeight();
        overlayWidth = getImage().getWidth();

        black = packagePixel(70, 70, 70, 255);
        transparent = packagePixel(0, 0, 0, 0);

        blackCount = 0;

        for (int i = 0; i < getBufferedImage().getHeight(); i++) {

            for (int j = 0; j < getBufferedImage().getWidth(); j++) {
                colorGrid[i][j] = false;
            }
        }

    }

    /**
     * Act method for the overlay class. The act method controls all of the surface area calculations and draws/updates
     * the overlay image.
     */
    public void act() {
        actCount++;
        if (firstTime) {
            firstTime = false;
            write(Pencil.xPosition(), Pencil.yPosition() + 29, Pencil.getStartingAreaLength());

        }
        surfaceAreaCount--;

        if (surfaceAreaCount == 0) {
            calculateSurfaceArea();
            surfaceAreaCount = 40;
        }

        setImage(overlayImage);

        if (actCount % 2 == 0) {
            overlayDrawLeft();
        } else {
            overlayDrawRight();
        }

        surfaceAreaPercentS = surfaceAreaPercent;
    }

    /**
     * This is a Mr. Cohen method that returns a buffered image from a GreenfootImage.
     */
    public static BufferedImage getBufferedImage() {
        return overlayImage.getAwtImage();
    }

    /**
     * This is a method that updates the overlayImage with the given parameter image.
     * 
     * @param image GreenfootImage that is to be set as the new current overlayImage.
     */
    public static void updateOverlay(GreenfootImage image) { // need to set a current image
        overlayImage = new GreenfootImage(image);
    }

    /**
     * This is a method that based on location, essentially erases drawn pixels by making updates to the colorGrid array.
     * 
     * @param xPixel The x location.
     * @param yPixel The y Location.
     * @param eraseWidth The width to be erased.
     */
    public static void erase(int xPixel, int yPixel, int eraseWidth) {
        //This for loop updates the colorGrid based on the inputted x and y coordinates and the eraseWidth. It also updates the 
        //amount of pixels that is coloured.
        for (int i = yPixel - eraseWidth / 2; i < yPixel + eraseWidth / 2; i++) {
            for (int j = xPixel - eraseWidth / 2; j < xPixel + eraseWidth / 2; j++) {
                try {
                    if (colorGrid[i][j] == false) {
                        continue;
                    } else {
                        colorGrid[i][j] = false;
                        if (blackCount > 0) {
                            blackCount--;
                        }
                    }

                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }

    /**
     * This is a method that returns the amount of drawn pixels that have been erased. This is used by some Eraser subclasses 
     * for their behaviour. 
     * 
     * @param xPixel The x location.
     * @param yPixel The y Location.
     * @param eraseWidth The width to be erased.
     * @return int A number of erased pixels are returned
     */
    public static int eraseGetValue(int xPixel, int yPixel, int eraseWidth) {
        int inkErased = 0;

        //This for loop checks how many drawn pixels have been erased based on the x location, y location and eraser width inputted.
        for (int i = yPixel - eraseWidth / 2; i < yPixel + eraseWidth / 2; i++) {
            for (int j = xPixel - eraseWidth / 2; j < xPixel + eraseWidth / 2; j++) {
                try {
                    if (colorGrid[i][j] == false) {
                        continue;
                    } else {
                        colorGrid[i][j] = false;
                        inkErased++;
                        if (blackCount > 0) {
                            blackCount--;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }

        return inkErased;
    }

    /**
     * This method calculates the percentage of pixels that are coloured in.
     * 
     * @return double this is a percent of the pixels that are coloured in.
     */
    public double calculateSurfaceArea() {
        surfaceAreaPercent = (blackCount / totalPixels) * 100;
        surfaceAreaPercent = (double) Math.round(surfaceAreaPercent * 100) / 100;

        return surfaceAreaPercent;
    }

    /**
     * This is a getter method that returns the surface area.
     * 
     * @return double A double percent of surface area.
     */
    public static double getSurfaceArea() {
        return surfaceAreaPercentS;
    }

    /**
     * This is a method that returns the closest coloured pixel coordinate relative to an x coordinate and y coordinate.
     * 
     * @param xCoord Location of x coordinate.
     * @param yCoord Location of y coordinate.
     */
    public static int[] findClosest(int xCoord, int yCoord) {
        //initializing variables
        BufferedImage bi = deepCopy(getBufferedImage());
        int width = bi.getWidth();
        int height = bi.getHeight();
        int closestX = -1;
        int closestY = -1;
        double minDistance = 100000000;
        boolean isBlackPixel;

        //this for loop iterates through all the pixels and uses pythagoras theorum to calculate the distances between the coloured pixels and the inputted location
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (colorGrid[i][j]) {
                    if (Math.sqrt(Math.pow(j - xCoord, 2) + Math.pow(i - yCoord, 2)) < minDistance) {
                        minDistance = Math.sqrt(Math.pow(j - xCoord, 2) + Math.pow(i - yCoord, 2));
                        closestX = j;
                        closestY = i;
                    }
                }
            }
        }

        //the closest location is saved
        int[] closest = new int[] {
            closestX,
            closestY
        };

        return closest;
    }


    /**
     * This is a method that based on location, writes/ colours in pixels.
     * 
     * @param xPixel The x location of the pixel to be coloured.
     * @param yPixel The y location of the pixel to be coloured.
     * @param inkWidth How many pixels wide/tall to cover.
     */
    public static void write(int xPixel, int yPixel, int inkWidth) {
        for (int i = yPixel - inkWidth / 2; i < yPixel + inkWidth / 2; i++) {
            for (int j = xPixel - inkWidth / 2; j < xPixel + inkWidth / 2; j++) {
                try {
                    if (colorGrid[i][j]) {
                        continue;
                    } else {
                        colorGrid[i][j] = true;

                        if (blackCount < totalPixels) {
                            blackCount++;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
            }
        }
    }

    /**
     * This method draws the left side of the overlay, and updates the overlay image.
     */
    public void overlayDrawLeft() {
        BufferedImage bi = deepCopy(getBufferedImage());

        for (int i = 0; i < (overlayHeight); i++) {
            for (int j = 0; j < (overlayWidth / 2); j++) {
                if (colorGrid[i][j]) {
                    bi.setRGB(j, i, black);
                } else {
                    bi.setRGB(j, i, transparent);
                }
            }
        }

        updateOverlay(createGreenfootImageFromBI(bi));
    }

    /**
     * This method draws the right side of the overlay, and updates the overlay image.
     */
    public void overlayDrawRight() {
        BufferedImage bi = deepCopy(getBufferedImage());

        for (int i = 0; i < overlayHeight; i++) {
            for (int j = (overlayWidth / 2); j < overlayWidth; j++) {
                if (colorGrid[i][j]) {
                    bi.setRGB(j, i, black);
                } else {
                    bi.setRGB(j, i, transparent);
                }
            }
        }

        updateOverlay(createGreenfootImageFromBI(bi));
    }

    /**
     * This method 'resets' the overlay by erasing all the drawn pixels and return the overlay to having all of its pixels being transparent.
     */
    public void reset() {
        BufferedImage bi = deepCopy(getBufferedImage());
        int newColour = packagePixel(0, 0, 0, 0);
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                bi.setRGB(j, i, newColour);
            }
        }
        updateOverlay(createGreenfootImageFromBI(bi));
    }


    /**
     * Mr. Cohen's method to return a deep copy of a bufferedimage.
     */
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     * 
     * @param newBi The BufferedImage to convert.
     * 
     * @return GreenfootImage   A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI(BufferedImage newBi) {
        GreenfootImage returnImage = new GreenfootImage(newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D) backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        return returnImage;
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel(int rgbaValue) {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >> 8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel(int r, int g, int b, int a) {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }







}