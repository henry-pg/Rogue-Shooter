import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. Intelligent Eraser is a default-type, erasing-type enemy that finds and erases to the closest ink. 
 * 
 * @author Johnathan Lam
 * @version June 9, 2022
 */
public class IntelligentEraser extends Eraser
{
    private int idleWait;
    private int idlePicNum;
    private static GreenfootImage one, two, three, four;
    
    /**
     * Constructor for IntelligentEraser
     */
    public IntelligentEraser (int maxSpeed, int eraseWidth)
    {
        super (maxSpeed, eraseWidth, 100);    
        idleWait = 0;
        idlePicNum = 0;
        scaleImages();
        setImage(one);
    }
    
    /**
     * Walks to the closest ink (and then erases it)
     */
    public void act()
    {
        idleAnimate();
        super.act();
    }
    
    /**
     * Finds the closest ink then walks to the said ink
     */
    public void walk()
    {
        int[] closestP = Overlay.findClosest(getX(), getY() + 47);
        //closestP[1] += 40;
        //System.out.println (closestP[0] + " " + closestP[1]);
        if (closestP[0] != -1)
        {
            if (closestP[0] < getX())
            {
                if (closestP[0] - speed > 0)
                {
                    setLocation (getX() - speed, getY());
                }
                else
                {
                    setLocation (getX() - 1, getY());
                }
            }
            else if (closestP[0] > getX())
            {
                if (closestP[0] + speed > 0)
                {
                    setLocation (getX() + speed, getY());
                }
                else
                {
                    setLocation (getX() + 1, getY());
                }
            }
        }

        if (closestP[1] != -1)
        {
            if (closestP[1] < getY() + 47)// + 40)
            {
                if (closestP[1] - speed > 0)
                {
                    setLocation (getX(), getY() - speed);
                }
                else
                {
                    setLocation (getX(), getY() - 1);
                }
            }
            else if (closestP[1] > getY() + 47)// + 40)
            {
                if (closestP[1] + speed > 0)
                {
                    setLocation (getX(), getY() + speed);
                }
                else
                {
                    setLocation (getX(), getY() + 1);
                }
            }
            Overlay.erase(getX(), getY() + 47, eraseWidth);
        }
    }
   
    /**
     * Idle animation for the eraser
     */
    private void idleAnimate()
    {
        GreenfootImage[] picture = {one, two, three, four};

        if (idleWait == 7)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 4)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }
    
    /**
     * Scales the images.
     */
    private static void scaleImages(){
        one = new GreenfootImage("Intelligent (1).png");
        one.scale (60,110);
        
        two = new GreenfootImage("Intelligent (2).png");
        two.scale (60,110);
        
        three = new GreenfootImage("Intelligent (3).png");
        three.scale (60,110);
        
        four = new GreenfootImage("Intelligent (4).png");
        four.scale (60,110);
    }
}
