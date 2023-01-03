import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. Grower Eraser is a boss-type, erasing-type enemy that grows with the amount of ink it eats.
 * Grower Eraser has a long attack cooldown, and while it's recharging, it's in its idle animation - standing still until
 * the attack can be used again. When the attack cooldown finished, it teleports to the player's location 
 * (from several acts back to give the player enough time to react). Once it teleports onto the location, it will eat a large
 * chunk of the map. Depending on the amount of ink it eats, it will recover more hp and recieve a bigger erasing chunk. This 
 * will be spawned into the game after every wave/round to signify a new wave/round is coming. With each passing wave/round, 
 * it's hp and attack cooldown will be upgraded - increasing in health and lowering the cooldown between attacks. Furthermore, 
 * if the player is caught in the jaws of this eraser's whilst it is eating, the player will be stunned. 
 * 
 * @author Johnathan Lam 
 * @version June 21, 2022
 */
public class GrowerEraser extends Eraser
{
    private int growth;
    private int maxGrowth;
    private int actCounter;
    private int healthUpdateCounter;
    private boolean isEating;
    private boolean canEat;
    private int xTP, yTP;
    private int defaultWidth;
    private int tpActCounter;
    private boolean tpCounterCounting;
    private boolean eatCounterCounting;
    private int eatActCounter;
    private int attackRate;
    private int idlePicNum, idleWait, teleOutPicNum, teleOutWait, teleInPicNum, teleInWait, eatPicNum, eatWait;
    private boolean tpBegDone, tpDone, eatingDone, started;
    private boolean incomingAtk;
    public static boolean stunned, stunAvailable; 
    private IncomingAtkIndicator Indicator; 

    private GreenfootSound warDrums;
    private GreenfootSound chomp;
    private GreenfootSound teleport;

    /**
     * Constructor for GrowerEraser, set the maxSpeed, the eraser width of its starting chomp, its max health and how quickly it attacks.
     * If you want it to attack quicker, set the attack rate to a lower number.
     * 
     * @param maxSpeed      The maximum value of its speed
     * @param eraserWidth   The size of its erasing width to determine its erasing area
     * @param maxHP         The maximum value of its health
     * @param attackRate    The lower the attackRate value, the quicker the cooldown between its attacks.
     */
    public GrowerEraser(int maxSpeed, int eraserWidth, int maxHP, int attackRate)
    {
        super (maxSpeed, eraserWidth, maxHP);
        this.attackRate = attackRate;
        defaultWidth = eraserWidth;
        maxGrowth = 10000;
        growth = 0;
        actCounter = (attackRate / 2) + 60;
        tpActCounter = 0;
        eatActCounter = 0;
        idlePicNum = 0;
        idleWait = 0;
        teleOutWait = 0;
        teleOutPicNum = 0;
        teleInPicNum = 0;
        teleInWait = 0;
        eatPicNum = 0;
        eatWait = 0;
        tpBegDone = false;
        tpDone = false;
        eatingDone = false;
        eatCounterCounting = false;
        tpCounterCounting = false;
        started = false;
        //getImage().scale(224, 253);
        warDrums = new GreenfootSound ("Drums of War.wav");
        warDrums.setVolume (35);
        chomp = new GreenfootSound ("chomp.wav");
        chomp.setVolume(80);
        teleport = new GreenfootSound ("teleport.wav");
        teleport.setVolume (70);
        //warDrums.play();
    }

    /**
     * Determines when the attack cooldown has finished, when to tp, when to eat and when to idle
     */
    public void act()
    {
        healthUpdateCounter++;
        if (firstTime)
        {
            healthStat = new StatBar (health, health, this, 44, 5, getImage().getHeight()/2 + 10, Color.GREEN, Color.RED, true);
            getWorld().addObject(healthStat, 0, 0);
            firstTime = false;
            warDrums.play();
        }
        //System.out.println (actCounter);

        // Attack cooldown
        if (actCounter <= attackRate && !started) //680
        {
            actCounter++;
            idleAnimate();
            //canEat = true;
            if (actCounter > attackRate)
            {
                actCounter = 0;
                canEat = true;
                started = true;
            }
            //started = true;
            //System.out.println (actCounter);
        }

        // if attack cooldown has finished
        if (canEat)
        {
            //isEating = true;
            setTPLocation();
            canEat = false;
            tpCounterCounting = true;
            incomingAtk = true; 
        }

        // tp when the attack cooldown has finished
        if (tpCounterCounting)
        {
            tpBegAnimate();
            //Indicate where he will teleport too
            if(incomingAtk){
                AttackIndicator();
                incomingAtk = false;
            }
            if (tpBegDone)
            {
                //tpActCounter = 0;
                tpCounterCounting = false;
                walk();
                tpDone = true;
                eatCounterCounting = true;
                tpBegDone = false;
                //removeAttackIndicator(); 
            }
        }

        // plays an animation for the end tp 
        if (tpDone)
        {
            tpEndAnimate();
        }

        // the acts before it can eat
        if (eatCounterCounting)
        {
            eatActCounter++;
        }

        // when the eat countdown has finished, eat
        if (eatActCounter >= 30)
        {
            //chomp.play();
            eatAnimate();
            if (eatingDone)
            {
                eatCounterCounting = false;
                eat();
                eatingDone = false;
                eatActCounter = 0;
                started = false;
            }
        }

        if (healthUpdateCounter % 10 == 0)
        {
            healthStat.update (health);
        }

        if (health <= 0)
        {
            PaperWorld p = (PaperWorld)getWorld();
            p.spawnWeapon();
            getWorld().removeObject (this);
        }
    }

    /**
     * This eraser's form of movement - teleportation
     */
    public void walk()
    {
        setLocation (xTP, yTP);
    }

    /**
     * This calls the IncomingAtkIndicator class and adds it to the world to display an area that will be attacked
     */
    private void AttackIndicator(){
        getWorld().addObject(new IncomingAtkIndicator(eraseWidth), xTP, yTP+30); 
    }

    /**
     * This removes the IncomingAtkIndicator object
     */
    private void removeAttackIndicator(){
        getWorld().removeObjects(getWorld().getObjects(IncomingAtkIndicator.class)); 
    }

    /**
     * Gets the pencil's x y coords
     */
    private void setTPLocation()
    {
        xTP = Pencil.xPosition();
        yTP = Pencil.yPosition();
    }

    /**
     * Eats a big chunk of the map and grows and recovers its hp corresponding to how much ink it ate
     */
    private void eat()
    {
        int inkAte = Overlay.eraseGetValue(getX(), getY() + 30, eraseWidth);
        growth += inkAte;
        health += (int) (inkAte / 500);
        if (health > maxHealth)
        {
            health = maxHealth;
        }

        if (growth >= 30000 && growth < 60000)
        {
            eraseWidth = defaultWidth + 50;
        }
        else if (growth >= 60000 && growth < 90000)
        {
            eraseWidth = defaultWidth + 75;
        }
        else if (growth >= 90000 && growth < 120000)
        {
            eraseWidth = defaultWidth + 100;
        }
        else if (growth >= 120000 && growth < 150000)
        {
            eraseWidth = defaultWidth + 115;
        }
        else if (growth >= 150000 && growth < 180000)
        {
            eraseWidth = defaultWidth + 130;
        }
        else if (growth >= 180000 && growth < 210000)
        {
            eraseWidth = defaultWidth + 140;
        }
        else if (growth >= 2100000 && growth < 240000)
        {
            eraseWidth = defaultWidth + 150;
        }
        //System.out.println(growth);
    }

    /**
     * Idle animation for the eraser
     */
    private void idleAnimate()
    {
        String[] picture = {"Fatty Idle (1).png", "Fatty Idle (2).png", "Fatty Idle (3).png", "Fatty Idle (4).png"};

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
     * The animation for the beginning of the tp
     */
    private void tpBegAnimate()
    {
        String[] picture = {"Fatty Tele away (1).png", "Fatty Tele away (2).png", "Fatty Tele away (3).png", "Fatty Tele away (4).png", "Fatty Tele away (5).png", "Fatty Tele away (6).png", "Fatty Tele away (7).png", "Fatty Tele away (8).png", "Fatty Tele away (9).png", "Fatty Tele away (10).png", "Fatty Tele away (11).png", "Fatty Tele away (12).png", "Fatty Tele away (13).png", "Fatty Tele away (14).png", "Fatty Tele away (15).png", "Fatty Tele away (16).png"};

        if (teleOutWait == 4)
        {
            if (teleOutPicNum == 14)
            {
                teleport.play();
            }
            setImage (picture[teleOutPicNum]);
            //image = getImage();
            //image.scale (60, 50);
            teleOutPicNum++;
            teleOutWait = 0;

            if (teleOutPicNum == 16)
            {
                tpBegDone = true;
                teleOutPicNum = 0;
            }
        }

        teleOutWait++;
    }

    /**
     * The animation for the end of the tp
     */
    private void tpEndAnimate()
    {
        String[] picture = {"Fatty Tele in (1).png", "Fatty Tele in (2).png", "Fatty Tele in (3).png", "Fatty Tele in (4).png", "Fatty Tele in (5).png", "Fatty Tele in (6).png", "Fatty Tele in (7).png"};

        if (teleInWait == 4)
        {
            setImage (picture[teleInPicNum]);
            //image = getImage();
            //image.scale (60, 50);
            teleInPicNum++;
            teleInWait = 0;

            if (teleInPicNum == 7)
            {
                teleInPicNum = 0;
                tpDone = false;
            }
        }

        teleInWait++;
    }

    /**
     * The animation for eating
     */
    private void eatAnimate()
    {
        String[] picture = {"Fatty (1).png", "Fatty (2).png", "Fatty (3).png", "Fatty (4).png", "Fatty (5).png", "Fatty (6).png", "Fatty (7).png", "Fatty (8).png", "Fatty (9).png", "Fatty (10).png", "Fatty (11).png", "Fatty (12).png", "Fatty (13).png", "Fatty (14).png", "Fatty (15).png" };

        if (eatWait == 3)
        {
            setImage (picture[eatPicNum]);
            //image = getImage();
            //image.scale (60, 50);
            eatPicNum++;
            eatWait = 0;
            if (eatPicNum == 8)
            {
                chomp.play();
                if(stunAvailable){//If the pencil is touching the Grower during its chomp it will be stunned
                    stunned = true; 
                    removeAttackIndicator(); 
                }  
            }
            if (eatPicNum == 15)
            {
                eatingDone = true;
                eatPicNum = 0;
            }
        }

        eatWait++;
    }
}
