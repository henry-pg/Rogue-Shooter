import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * IdleMarker is the subclass that contains the idle animation for the marker on the TitleWorld.
 * 
 * @author Charis 
 * @version June 2022
 */
public class IdleMarker extends IdlePencil
{
    /**
     * The main constructor for the IdleMarker class
     * It calls on the super class's constructor.
     */
    public IdleMarker()
    {
        super();
    }
    
    /**
     * The act method is called when the code is running.
     * It calls on the super class's act method.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    
    /**
     * A method used to scale the images and assign them to a variable.
     */
    public void scaleImages(){
        one = new GreenfootImage("Marker idle (1).png");
        one.scale (48,160);
        
        two = new GreenfootImage("Marker idle (2).png");
        two.scale (48,160);
        
        three = new GreenfootImage("Marker idle (3).png");
        three.scale (48,160);
        
        four = new GreenfootImage("Marker idle (4).png");
        four.scale (48,160);
    }
    
    /**
     * A method used to animate the image by changing the animation frame every few acts.
     */
    public void idleAnimate()
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
}
