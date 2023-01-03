import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Refer to the Eraser super class. Summoner Eraser is a default-type, erasing-type eraser that erases wherever it walks
 * and summons 2 MinionErasers after a certain time has passed. The movement is the same as the QuickEraser (bouncing off of walls),
 * but slower.
 * 
 * @author Johnathan Lam 
 * @version June 9, 2022
 */
public class SummonerEraser extends Eraser
{
    private int summoningCooldown;
    private boolean spawnedMinions;
    private boolean spawningMinions;
    private int spawningTime;
    private int rotation;
    private int minAngle;
    private int maxAngle;
    private int idleWait, idlePicNum, summonWait, summonPicNum;
    private boolean summonAnimateDone;
    private GreenfootSound summon;

    /**
     * Constructor for SummonerEraser. The max speed determines how fast its default speed would be, while the 
     * erase width determines how big his erase area is.
     * 
     * @param maxSpeed      The speed the eraser will almost always move with. Only when they are sped up by the SpeedBoost eraser, will their speed change via a multiple of maxSpeed
     * @param eraseWidth    The width of its erasing size, it erases in a square. This is the length / width for the erasing square.
     */
    public SummonerEraser(int maxSpeed, int eraserWidth)
    {
        super (maxSpeed, eraserWidth, 80);  
        summoningCooldown = 400;
        spawningTime = 300;
        spawnedMinions = false;
        spawningMinions = false;
        //minAngle = 30;
        //maxAngle = 330;
        idleWait = 0;
        idlePicNum = 0;
        summonWait = 0;
        summonPicNum = 0;
        summonAnimateDone = false;
        rotation = minAngle + (int)(Math.random() * maxAngle);
        //if (rotation >= -5 && rotation <= 5)
        //{
        //    rotation += 20;
        //}
        
        minAngle = 15;
        maxAngle = 60;
        rotation = minAngle + (int)(Math.random() * maxAngle);
        int random = Greenfoot.getRandomNumber(4);
        if (random == 0)
        {
            rotation = 180 - rotation;
        }
        else if (random == 1)
        {
            rotation += 180;
        }
        else if (random == 2)
        {
            rotation = 360 - rotation;
        }
        
        summon = new GreenfootSound ("summon.wav");
        summon.setVolume(70);
    }
    
    /**
     * Summons Minions after the cooldown time has finished. It will stop while it is summoning. When it is walking, it will
     * behave like the QuickEraser, bouncing off of walls when it hits one.
     */
    public void act()
    {
        summoningCooldown--;
        
        // if the cooldown is finished, start summoning 
        if (summoningCooldown <= 0)
        {
            spawningMinions = true;
        }
        else
        {
            idleAnimate();
        }
        
        // plays animation first and then summons after the animation is finished playing
        if (spawningMinions)
        {
            summonAnimate();
            if (summonAnimateDone)
            {
                summon.play();
                spawnMinions(); 
                //summon.play();
                spawningMinions = false;
                spawningTime = 120;
                summoningCooldown = 400;
                summonAnimateDone = false;
            }
        }
        /*
        if (spawningMinions)
        {
            spawningTime--;
            
            // if the summoning time is finished, summon 2 Minions
            if (spawningTime <= 0)
            {
                // not done!!!!!!!
                summonAnimate();
                spawnMinions();   
                spawningMinions = false;
                spawningTime = 120;
                summoningCooldown = 600;
            }
        }
        */
        // walk
        super.act();
    }
    
    /**
     * Bounces off of walls when it hits a wall only if it is not spawning minions
     */
    public void walk()
    {
        if (!spawningMinions)
        {
            setRotation (rotation);
            move(speed);
            //rotation = getRotation();

            if (isAtEdge())//getY()<= 3 || getY() >= 550 || getX() == 0 || getX() == 899)
            {
                if (getY() == 0 || getY() == getWorld().getHeight()-1)
                {
                    rotation = 360 - getRotation();
                }

                if (getX()==0 || getX() == getWorld().getWidth()-1)
                {
                    rotation = 180 - getRotation();
                }
            }
            Overlay.erase(getX(), getY() + 10, eraseWidth);

            setRotation (0);
        }
    }
    
    /**
     * Spawns 2 minions in random locations
     */
    private void spawnMinions()
    {
        int minY = 61;
        int maxY = 520;
        int minX = 40;
        int maxX = 850;
        for (int i = 0; i < 2; i++)
        {
            int yCoord = minY + (int)(Math.random() * maxY);
            int xCoord = minX + (int)(Math.random() * maxX);
            ((PaperWorld)getWorld()).addObject (new MinionEraser(3, 20), xCoord, yCoord);
        }
    }
    
    /**
     * The idle animation for the Eraser
     */
    private void idleAnimate()
    {
        String[] picture = {"Idle New (1).png", "Idle New (2).png", "Idle New (3).png", "Idle New (4).png", "Idle New (5).png", "Idle New (6).png"};
        
        if (idleWait == 5)
        {
            setImage (picture[idlePicNum]);
            //image = getImage();
            //image.scale (60, 50);
            idlePicNum++;
            idleWait = 0;

            if (idlePicNum == 6)
            {
                idlePicNum = 0;
            }
        }

        idleWait++;
    }
    
    /**
     * The summoning animation for the Eraser
     */
    private void summonAnimate()
    {
        String[] picture = {"Sum Anim New (1).png", "Sum Anim New (2).png", "Sum Anim New (3).png", "Sum Anim New (4).png", "Sum Anim New (5).png", "Sum Anim New (6).png", "Sum Anim New (7).png", "Sum Anim New (8).png", "Sum Anim New (9).png", "Sum Anim New (10).png", "Sum Anim New (11).png", "Sum Anim New (12).png"};
        
        if (summonWait == 6)
        {
            setImage (picture[summonPicNum]);
            //image = getImage();
            //image.scale (60, 50);
            summonPicNum++;
            summonWait = 0;

            if (summonPicNum == 12)
            {
                summonPicNum = 0;
                summonAnimateDone = true;
            }
        }

        summonWait++;
    }
}