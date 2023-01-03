import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is the base class for all pencils. Pencil is a player-controlled object whose main objective is to 
 * draw and maintain its territory. If you hold 'shift', it will activate the 'sprint' function which will
 * allow the player to move faster. However, there is a limited amount of sprint, so the player cannot always 
 * be in the 'sprint' phase. If the pencil is touching a border, it will teleport the player to the opposite side
 * If you hold the 'space' key, it will activate the 'sneak' ability which will prevent 
 * the pencil from teleporting when it touches a border. There are buffs and weapons that the pencil can pick up off the ground.
 * Buffs are temporary effects that boost a specific player's stat (depending on what they picked up). There is ink width boost, 
 * speed boost and attack speed boost. As for weapons, the pencil starts off with a handgun, but can pick up weapons that appear on the ground to switch them.
 * 
 * @author Johnathan Lam and Henry
 * @version June 14, 2022
 */
public abstract class Pencil extends Actor
{
    protected int speed;
    protected int maxSpeed;
    protected int maxSpeed2;
    protected int sprintSpeed2;
    protected int speedCount;
    protected static int startingAreaLength;
    protected int inkWidth; 
    protected int surfaceArea;
    protected boolean firstTime;
    protected static String direction = "";
    protected static int sprintCounter;
    protected static int sprintSpeed;
    protected int actCount;
    protected boolean cannotLeft, cannotRight, cannotUp, cannotDown;
    protected Weapon currentWeapon;
    protected int effectDuration;
    public static boolean isStunned;
    protected boolean isSlowed;
    protected boolean isPushed;
    protected int stunnedDuration;
    protected int pushedDuration;
    protected String eraserDirection;
    protected int idleWait;
    protected int idlePicNum;
    protected int yOffset;
    protected int xOffset;
    protected int xBoundaryOffset;
    protected int yBoundaryOffset;
    protected int upActCounter, downActCounter, leftActCounter, rightActCounter;
    protected boolean upACStart, downACStart, leftACStart, rightACStart;
    protected int inkWidth2;
    protected int widthCount;
    protected GreenfootSound writeBeg, writeMid;
    protected boolean tempFirstTime = true;
    //protected int 

    protected GreenfootSound[] write;
    protected int writeSoundsIndex;
    protected int actCounter;
    protected int writeSoundsIndex2;
    private static int xPos, yPos;
    protected boolean isMoving;

    /**
     * Main constructor for the Pencil class. This would be the blueprint for all of the eraser subclasses.
     * It would set the max speed the player would move at, the ink width, the starting surface area and the x y offsets.
     * 
     * @param maxSpeed      The speed the pencil will almost always move at. Only when it is sped up by the speed boost buff will their speed change via a multiple of maxSpeed.
     * @param inkWidth      The width of its drawing size, it draws in a square. This is the length / width for the drawing square.
     */
    public Pencil (int maxSpeed, int inkWidth)
    {
        this.maxSpeed = maxSpeed;
        this.startingAreaLength = 234;
        this.speed = this.maxSpeed;
        this.inkWidth = inkWidth;
        firstTime = false;
        //getImage().scale(80, 80);
        sprintSpeed = maxSpeed + 2; //this is temporary
        sprintCounter = 100;
        actCount = 0;
        inkWidth2 = inkWidth;
        cannotLeft = false;
        cannotRight = false;
        cannotUp = false;
        cannotDown = false;
        isSlowed = false;
        isPushed = false;
        effectDuration = 0;
        pushedDuration = 0;
        idleWait = 0;
        idlePicNum = 0;
        yOffset = 0;
        xOffset = 0;
        xBoundaryOffset = 0;
        yBoundaryOffset = 0;
        upActCounter = 0;
        downActCounter = 0;
        leftActCounter = 0;
        rightActCounter = 0;
        upACStart = false;
        downACStart = false;
        rightACStart = false;
        leftACStart = false;
        maxSpeed2 = maxSpeed;
        sprintSpeed2 = sprintSpeed;
        actCounter = 130;
        writeSoundsIndex = 0;
        writeSoundsIndex2 = 0;
        write = new GreenfootSound[20];
        for (int i = 0; i < write.length; i++)
        {
            write[i] = new GreenfootSound ("pencil4.wav");
            write[i].setVolume(50);
        }
        isMoving = false;
    }

    /**
     * The default act method - only acts if the game has not declared a GAME OVER yet. The player spawns with
     * a handgun. This also checks whether it is in the radius of the SlowerEraser and the PusherEraser and will act accordingly.
     * This also allows the Pencil to move and draw on the page and have an idle animation.
     */
    public void act()
    {
        // if not game over
        if (!PaperWorld.getGameOver())
        {
            actCount++;
            effectDuration--;
            pushedDuration--;
            stunnedDuration--;

            xPos = getX();
            yPos = getY();

            // gives initial weapon
            if (!firstTime)
            {
                currentWeapon = new Handgun(this);
                getWorld().addObject(currentWeapon, getX(), getY());
                firstTime = true;
            }

            //if the pencil touches the SlowerEraser class
            SlowerEraserHitbox hitboxOne = (SlowerEraserHitbox)getOneObjectAtOffset(0,0,SlowerEraserHitbox.class);
            if (hitboxOne != null)
            {
                isSlowed = true;
                effectDuration = 120;
                getWorld().addObject(new SpeedDebuffIcon(), 700, 20); 
            }

            // if it is affecrted by slow, slow down, if the effect ends, normal speed
            if (effectDuration > 0 && isSlowed)
            {
                slowDown();

            }
            else if (effectDuration < 0 && isSlowed)
            {
                isSlowed = false;
                normalSpeed();
            }

            //if the pencil touches the PusherEraser class
            PusherEraserHitbox hitboxTwo = (PusherEraserHitbox)getOneObjectAtOffset(0,0,PusherEraserHitbox.class);
            if (hitboxTwo != null)
            {
                isPushed = true;
                pushedDuration = 50;
            }

            // pushes the pencil
            if (isPushed == true && pushedDuration > 0)
            {
                if (direction.equals("up") && getY() > 22)
                {
                    setLocation(getX(), getY() + 2);
                }
                else if (direction.equals("down") && getY() < 560)
                {
                    setLocation(getX(), getY() - 2);
                }
                else if (direction.equals("right") && getX() < 890){
                    setLocation(getX() - 2, getY());
                }
                else if (direction.equals("left") && getX() > 14){
                    setLocation(getX() + 2, getY());
                }
            }
            else if (pushedDuration < 0 && isPushed == true){
                isPushed = false;
            }

            //walk
            if (!isPushed){
                Overlay.write(getX() + xOffset, getY() + yOffset, inkWidth);
                walk(speed);
            }
            
            if(GrowerEraser.stunned == true){
                GrowerEraser.stunAvailable = true;
            }else{
                GrowerEraser.stunAvailable = false;
            }

            //if the pencil touches the GrowerEraser during his stun he 
            if (GrowerEraser.stunned == true){
                isStunned = true; 
                stunnedDuration = 120; 
                GrowerEraser.stunned = false; 
                getWorld().addObject(new Stun(this), 0, 0); 
            }

            if (stunnedDuration <= 0 && isStunned == true)
            {
                isStunned = false;
                walk(speed);             
            }

            idleAnimate();
            if(!isStunned){
                if((((PaperWorld)(getWorld())).getPlayerBuffed()) == true)
                {
                    //boosted by speed
                    speedCount++;
                    if (speedCount >= 100)
                    {
                        setOriginalSpeed();
                        speedCount = 0;
                        ((PaperWorld)(getWorld())).setPlayerBuffedFalse();
                    }

                    widthCount++;
                    if (widthCount >= 100)
                    {
                        setOriginalInkWidth();
                        widthCount = 0;
                        ((PaperWorld)(getWorld())).setPlayerBuffedFalse();
                    }
                }
            }
        }
    }

    /**
     * sets the speed to the default (for unsprint)
     */
    public void normalSpeed()
    {
        speed = maxSpeed;
    }

    /**
     * sets the speed to a slower speed
     */
    public void slowDown()
    {
        speed = maxSpeed - 1;
    }

    /**
     * sets the speed to the original speed
     */
    public void setOriginalSpeed()
    {
        maxSpeed = maxSpeed2;
        sprintSpeed = sprintSpeed2;
    }

    /**
     * increases the speed 
     */
    public void speedUp()
    {
        maxSpeed = (int) (1.5 * speed);
        sprintSpeed = (int) (1.2 * sprintSpeed);
        speedCount = 0;
        ((PaperWorld)(getWorld())).setPlayerBuffed();
    }

    /**
     * How every pencil will move. Moves with WASD, 'shift' for faster speeds and 'space' for turning off border teleportation.
     */
    public void walk(int speed)
    {
        //System.out.println(sprintCounter);
        if(!isStunned){
            if (isMoving && Greenfoot.isKeyDown ("shift") && sprintCounter > 0)
            {
                sprintCounter --;
            }
            else if (!isMoving && Greenfoot.isKeyDown("shift"))
            {
                if (sprintCounter < 100 && actCount % 10 == 0)
                {
                    sprintCounter ++;
                }
            }

            if (isMoving)
            {
                actCounter++;
                if (actCounter >= 100)
                {
                    actCounter = 0;
                    writeSoundsIndex2 = writeSoundsIndex;
                    write[writeSoundsIndex].play();
                    writeSoundsIndex++;
                    if (writeSoundsIndex > write.length - 1)
                    {
                        writeSoundsIndex = 0;
                    }
                }
            }
            else
            {
                actCounter = 200;
                write[writeSoundsIndex2].stop();
            }

            if (!Greenfoot.isKeyDown("space"))
            {
                if (getX() < 14)
                {
                    //setLocation(890, getY());
                }

                if (getX() > 890)
                {
                    //setLocation(14, getY());
                }

                if (getY() > 560)
                {
                    //setLocation(getX(), 23);
                }

                if (getY() < 22)
                {
                    //setLocation(getX(), 560);
                }

                if (Greenfoot.isKeyDown("shift") && sprintCounter > 0)
                {
                    sprint();
                    if (Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("D"))
                    {
                        if (Greenfoot.isKeyDown("W"))
                        {
                            if (getY() - speed >= 22)
                            {
                                setLocation (getX(), getY() - speed);
                            }
                            else
                            {
                                setLocation (getX(), 22);
                                upACStart = true;
                                //setLocation(getX(), 560);
                            }

                            if (upACStart)
                            {
                                upActCounter++;
                            }

                            if (upACStart && upActCounter >= 2)
                            {
                                upACStart = false;
                                upActCounter = 0;
                                setLocation(getX(), 560);
                            }
                            direction = "up";
                            //Overlay.write(getX(), getY(), inkWidth, "up");
                            //Overlay.write(getX(), getY(), inkWidth);
                        }

                        if (Greenfoot.isKeyDown("S"))
                        {
                            //setLocation (getX(), getY() + speed);
                            if (getY() + speed <= 560)
                            {
                                setLocation (getX(), getY() + speed);
                            }
                            else
                            {
                                setLocation (getX(), 560);
                                downACStart = true;
                            }

                            if (downACStart)
                            {
                                downActCounter++;
                            }

                            if (downACStart && downActCounter >= 2)
                            {
                                downACStart = false;
                                downActCounter = 0;
                                setLocation(getX(), 22);
                            }
                            direction = "down";
                            //Overlay.write(getX(), getY(), inkWidth, "down");
                            //Overlay.write(getX(), getY());
                        }

                        if (Greenfoot.isKeyDown("D"))
                        {
                            if (getX() + speed <= 890)
                            {
                                setLocation (getX() + speed, getY());
                            }
                            else
                            {
                                setLocation (890, getY());
                                setLocation(14, getY());
                            }
                            direction = "right";
                            //Overlay.write(getX(), getY(), inkWidth, "right");
                            //Overlay.write(getX(), getY());
                        }

                        if (Greenfoot.isKeyDown("A"))
                        {
                            if (getX() - speed >= 14)
                            {
                                setLocation (getX() - speed, getY());
                            }
                            else
                            {
                                setLocation (14, getY());
                                setLocation(890, getY());
                            }
                            //setLocation (getX() - speed, getY());
                            direction = "left";
                            //Overlay.write(getX(), getY(), inkWidth, "left");
                            //Overlay.write(getX(), getY());
                        }
                        isMoving = true;
                    }
                    else
                    {
                        isMoving = false;
                    }
                }
                else
                {
                    normalSpeed();
                    if (sprintCounter < 100 && actCount % 10 == 0)
                    {
                        sprintCounter ++;
                    }
                    if (Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("D"))
                    {
                        if (Greenfoot.isKeyDown("W"))
                        {
                            if (getY() - speed >= 22)
                            {
                                setLocation (getX(), getY() - speed);
                            }
                            else
                            {
                                setLocation (getX(), 22);
                                upACStart = true;
                                //setLocation(getX(), 560);
                            }

                            if (upACStart)
                            {
                                upActCounter++;
                            }

                            if (upACStart && upActCounter >= 2)
                            {
                                upACStart = false;
                                upActCounter = 0;
                                setLocation(getX(), 560);
                            }
                            direction = "up";
                            //Overlay.write(getX(), getY(), inkWidth, "up");
                            //Overlay.write(getX(), getY());
                        }

                        if (Greenfoot.isKeyDown("S"))
                        {
                            if (getY() + speed <= 560)
                            {
                                setLocation (getX(), getY() + speed);
                            }
                            else
                            {
                                setLocation (getX(), 560);
                                downACStart = true;
                            }

                            if (downACStart)
                            {
                                downActCounter++;
                            }

                            if (downACStart && downActCounter >= 2)
                            {
                                downACStart = false;
                                downActCounter = 0;
                                setLocation(getX(), 22);
                            }
                            direction = "down";
                        }

                        if (Greenfoot.isKeyDown("D"))
                        {
                            if (getX() + speed <= 890)
                            {
                                setLocation (getX() + speed, getY());
                            }
                            else
                            {
                                setLocation (890, getY());
                                setLocation(14, getY());
                            }
                            direction = "right";
                        }

                        if (Greenfoot.isKeyDown("A"))
                        {
                            if (getX() - speed >= 14)
                            {
                                setLocation (getX() - speed, getY());
                            }
                            else
                            {
                                setLocation (14, getY());
                                setLocation(890, getY());
                            }
                            direction = "left";
                        }
                        isMoving = true;
                    }
                    else
                    {
                        isMoving = false;
                    }
                }
            }
            else
            {
                if (getX() < 14)
                {
                    cannotLeft = true;
                }

                if (getX() > 890)
                {
                    cannotRight = true;
                }

                if (getY() < 22)
                {
                    cannotUp = true;
                }

                if (getY() > 560)
                {
                    cannotDown = true;
                }

                if (getX() >= 14)
                {
                    cannotLeft = false;
                }

                if (getX() <= 890)
                {
                    cannotRight = false;
                }

                if (getY() >= 22)
                {
                    cannotUp = false;
                }

                if (getY() <= 560)
                {
                    cannotDown = false;
                }

                if (Greenfoot.isKeyDown("shift") && sprintCounter > 0)
                {
                    sprint();

                    if (Greenfoot.isKeyDown("W") && !cannotUp)
                    {
                        if (getY() - speed >= 22)
                        {
                            setLocation (getX(), getY() - speed);
                        }
                        else
                        {
                            setLocation (getX(), 22);
                        }
                        direction = "up";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "up");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }

                    if (Greenfoot.isKeyDown("S") && !cannotDown)
                    {
                        if (getY() + speed <= 560)
                        {
                            setLocation (getX(), getY() + speed);
                        }
                        else
                        {
                            setLocation (getX(), 560);
                            //setLocation(getX(), 22);
                        }
                        //setLocation (getX(), getY() + speed);
                        direction = "down";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "down");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }

                    if (Greenfoot.isKeyDown("D") && !cannotRight)
                    {
                        //setLocation (getX() + speed, getY());
                        if (getX() + speed <= 890)
                        {
                            setLocation (getX() + speed, getY());
                        }
                        else
                        {
                            setLocation (890, getY());
                            //setLocation(23, getY());
                        }
                        direction = "right";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "right");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }

                    if (Greenfoot.isKeyDown("A") && !cannotLeft)
                    {
                        //setLocation (getX() - speed, getY());
                        if (getX() - speed >= 14)
                        {
                            setLocation (getX() - speed, getY());
                        }
                        else
                        {
                            setLocation (14, getY());
                            //setLocation(890, getY());
                        }
                        direction = "left";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "left");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }
                }
                else
                {
                    normalSpeed();
                    if (sprintCounter < 100 && actCount % 12 == 0){
                        sprintCounter ++;
                    }

                    if (Greenfoot.isKeyDown("W") && !cannotUp)
                    {
                        if (getY() - speed >= 22)
                        {
                            setLocation (getX(), getY() - speed);
                        }
                        else
                        {
                            setLocation (getX(), 22);
                        }
                        direction = "up";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "up");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }

                    if (Greenfoot.isKeyDown("S") && !cannotDown)
                    {
                        //setLocation (getX(), getY() + speed);
                        if (getY() + speed <= 560)
                        {
                            setLocation (getX(), getY() + speed);
                        }
                        else
                        {
                            setLocation (getX(), 560);
                            //setLocation(getX(), 22);
                        }
                        direction = "down";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "down");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }

                    if (Greenfoot.isKeyDown("D") && !cannotRight)
                    {
                        //setLocation (getX() + speed, getY());
                        if (getX() + speed <= 890)
                        {
                            setLocation (getX() + speed, getY());
                        }
                        else
                        {
                            setLocation (890, getY());
                            //setLocation(23, getY());
                        }
                        direction = "right";
                        isMoving = true;
                        //Overlay.write(getX(), getY(), inkWidth, "right");
                        //Overlay.write(getX(), getY());
                    }
                    else{
                        isMoving = false;
                    }

                    if (Greenfoot.isKeyDown("A") && !cannotLeft)
                    {
                        if (getX() - speed >= 14)
                        {
                            setLocation (getX() - speed, getY());
                        }
                        else
                        {
                            setLocation (14, getY());
                        }
                        direction = "left";
                        isMoving = true;
                    }
                    else{
                        isMoving = false;
                    }
                }
            }
        }
    }

    /**
     * Returns the x position of the player
     * 
     * @return int      Returns the x-coord of the player
     */
    public static int xPosition()
    {
        return xPos;
    }

    /**
     * Returns the y position of the player
     * 
     * @return int      Returns the y-coord of the player
     */
    public static int yPosition()
    {
        return yPos;
    }

    /**
     * Returns the direction of the player
     * 
     * @return String   Returns the direction the player is moving
     */
    public static String getDirection()
    {
        return direction;
    }

    /**
     * Returns the length of the starting area square
     * 
     * @return int      Returns the starting area length.
     */
    public static int getStartingAreaLength()
    {
        return startingAreaLength;
    }

    /**
     * Changes the Pencil's speed to a sprinting (increased) speed
     */
    public void sprint()
    {
        speed = sprintSpeed;
    }

    /**
     * Returns the sprint count
     * 
     * @return int  Returns how much sprint is left.
     */
    public static int getSprintCount()
    {
        return sprintCounter;
    }

    /**
     * Sets the weapon to the weapon the player picked up
     */
    public void setWeapon (Weapon newWeapon)
    {
        try
        {
            Flamethrower f = (Flamethrower)currentWeapon;
            getWorld().removeObject(f.getFire());
        } catch (ClassCastException e)
        {
            // if not a flamethrower, do nothing
        }
        getWorld().removeObject(currentWeapon); 
        currentWeapon = newWeapon;
    }
    
    /**
     * Increases the Pencil's ink width when it picks up the ink width buff
     */
    public void increaseInkWidth()
    {
        inkWidth = (int) (inkWidth * 2.5);
        widthCount = 0;
        ((PaperWorld)(getWorld())).setPlayerBuffed();
    }

    /**
     * Changes the ink width back into the default after the ink width buff runs out
     */
    public void setOriginalInkWidth()
    {
        inkWidth = inkWidth2;
    }

    /**
     * For the idle animation
     */
    public abstract void idleAnimate();
}