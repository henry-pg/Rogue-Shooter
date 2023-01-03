import greenfoot.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import java.io.IOException;

/**
 * Placeholder is a unique blend of a top-down shooter game and a "hold the territory" game. In this game, the user plays as a pencil whose goal is to cover 
 * as much space on the screen as possible by walking over it to claim territory. There are five different types of pencil that the player can choose from, 
 * some of which having their own advantages and disadvantages to using them. These can be chosen on the main title screen.
 * 
 * IMPORTANT: When the game is over, the game has not crashed, execution is paused until a name is entered into a user input panel. However, if you take too long,
 * Greenfoot thinks that the execution is taking a long time. All functions resume after a name is correctly entered. 
 * 
 * <p> <ul> <li> The default pencil is the standard pencil that the player can use. Its movement speed and ink width are the standard. </li> </ul>
 * <ul> <li> The pen moves faster than the default pencil, but has a narrower ink width. </li> </ul>
 * <ul> <li> The marker moves slower than the default pencil, but has a wider ink width. </li> </ul>
 * <ul> <li> The quill and golden pencils function identically to the default pencil. They are merely cosmetic upgrades. </li> </ul>
 * 
 * <p> The player's area will constantly be attacked by erasers which have various abilities and characteristics which make them unique. Erasers are controlled
 * by the computer and can move either randomly, or according to a specific pattern or set of rules. There are three classes of eraser: The first eraser type 
 * directly removes the pencil's area by walking over it. The second type does not erase, but can boost the stats of nearby erasers. The third type attacks 
 * the player by applying specific debuffs or effects to the pencil when it gets near. The erasers are spawned in waves, which increases in number and difficulty
 * of eraser types over time. Overall, there are nine different types of eraser:
 * <p> <ul> <li> The normal eraser moves at a set speed and erases the area that it walks over. It changes direction at random intervals. </li> </ul>
 * <ul> <li> The quick eraser moves at a higher speed than the normal eraser and bounces off the edge of the world instead of changing direction randomly. </li> </ul>
 * <ul> <li> The intelligent eraser finds the nearest pixel of ink to it and walks towards it in order to erase it. </li> </ul>
 * <ul> <li> The summoner eraser bounces off walls and erases the area underneath it, but it also spawns two minion erasers at set intervals. </li> </ul>
 * <ul> <li> Minion erasers are summoned by the summoner eraser and have very low health. They will die after removing a set amount of ink. </li> </ul>
 * <ul> <li> The speed boost eraser increases the speed of any erasers within a certain radius around it, but does not erase ink. </li> </ul>
 * <ul> <li> The pusher eraser pushes the player back if they get within its radius, but does not remove ink. </li> </ul>
 * <ul> <li> The slower eraser slows the player down when they enter its radius, but does not remove ink. </li> </ul>
 * <ul> <li> The grower eraser is a boss-type eraser that spawns at the end of each wave of erasers. Its attack has a long animation and cooldown time, which
 * removes all the ink within a large chunk of the map. With each chunk it eats, it recovers some health and increases the size of the chunk it removes with
 * its next attack. After each attack, it will teleport to the player's location. If the player is on top of the grower eraser when it uses its bite attack,
 * the player will be stunned for a set number of acts. </li> </ul>
 * 
 * The player will be able to use a number of weapons in order to defeat erasers. These weapons will spawn after defeating each boss, and will fade out if
 * not picked up after a set amount of time. There are some ranged weapons which shoot projectiles, as well as melee weapons that require the player to get
 * close to their target. There are five types of weapon:
 * <p> <ul> <li> The handgun is the standard weapon the the player begins with. It shoots a single bullet that does medium damage with a cooldown after each shot. </li> </ul>
 * <ul> <li> The machine gun shoots bullets that deal less damage than the handgun, but at a much faster rate. </li> </ul>
 * <ul> <li> The shotgun shoots five pellets in a fan formation which each do less damage than the handgun. Its shooting cooldown is the same as the handgun. </li> </ul>
 * <ul> <li> The flamethrower spawns a fire object which deals increasing damage over time to every eraser that it touches. Erasers will be damaged every
 * few acts and the fire's damage is capped at a set number. </li> </ul>
 * <ul> <li> The paper clip saber does high melee damage to every eraser that it touches when it swings with a very fast cooldown (after the animation is finished) </li> </ul>
 * 
 * <p> In addition, there are buffs that the player can pick up to increase specific attributes. These buffs will spawn randomly into the world only if the
 * player is not currently under the influence of a buff and if there are no other buffs in the world. This ensures that the player can only pick up one buff
 * at a time. There are three types of buff:
 * <p> <ul> <li> Attack speed boost decreases the shooting cooldown when the player is holding a handgun, shotgun, or machine gun. </li> </ul>
 * <ul> <li> Ink width boost increases the area that is drawn underneath the player. </li> </ul>
 * <ul> <li> Speed boost increases the player's speed. </li> </ul>
 * 
 * <p> Ink drawing is controlled by an overlay class which contains a 2D boolean array which holds whether or not each pixel is coloured. There are methods within 
 * this class which help to determine which pixels are coloured in and other methods visually implement these changes.
 * 
 * <p> The game also has the ability to save the user's high scores onto a text file and display them to the user when they click on the high score button on
 * the title screen. The top ten scores on the user's device will be displayed. At the end of each game, the user will also be able to enter a name which will
 * be displayed alongside the score.
 * 
 * <p> Credits:
 * <p> <ul> <li> Pencil and eraser images and animations, buff icons, and paper clip saber image and animation: William Tang
 * <ul> <li> Title screen: Charis Chan
 * <li> <li> High score screen: Charis Chan, Ethan Lin
 * <ul> <li> Weapon images: Jestan (itch), https://jestan.itch.io/weapons-pack
 * <ul> <li> Fire animations: sanctumpixel (itch), https://sanctumpixel.itch.io/fire-column-pixel-art-effect
 * <ul> <li> Bullet image: Minecraftian47 (Pixel Gun Wiki), https://pgideas.fandom.com/wiki/Just_A_Bullet
 * <ul> <li> Machine gun bullet image: Pixel Art Gallery, http://pixelartmaker.com/art/63e15ad4a99ac13
 * <ul> <li> Shotgun pellet image: Pixel Art Gallery, http://pixelartmaker.com/art/0162920c1e1b161
 * <ul> <li> High score screen icon: Addison Foote (freevector.co), http://freevector.co/vector-icons/shapes/award-trophy-silhouette.html
 * <ul> <li> High score screen 'X' button: OnlineWebFonts, https://www.onlinewebfonts.com/icon/419410
 * <ul> <li> Pencil writing sound: ZapSplat, https://www.zapsplat.com/music/chalk-eraser-fast-rubs-on-blackboard-1/
 * <ul> <li> Page flip sound: All Sounds (YouTube), https://www.youtube.com/watch?v=ii4hh931-Pw
 * <ul> <li> Power up pick up sound: All Sounds (YouTube), https://www.youtube.com/watch?v=v94-AQxceGk&ab_channel=AllSounds
 * <ul> <li> Title screen music ("I Was Joking"): David Fesliyan (Fesliyan Studios), https://www.fesliyanstudios.com/royalty-free-music/download/i-was-joking/336
 * <ul> <li> Main game music ("Mastermind"): Steve Oxen (Fesliyan Studios), https://www.fesliyanstudios.com/royalty-free-music/download/mastermind/702
 * <ul> <li> End screen music ("Derp"): David Renda (Fesliyan Studios), https://www.fesliyanstudios.com/royalty-free-music/download/mastermind/702
 * <ul> <li> Secret Rickroll music ("Never Gonna Give You Up" lofi remix): KCFM (YouTube), https://www.youtube.com/watch?v=2xx_2XNxxfA
 * <ul> <li> Panda chomp sound: Lance Wiggle (YouTube), https://www.youtube.com/watch?v=eTNN8MtY4Io
 * <ul> <li> Shotgun shooting sound: Videvo, https://www.videvo.net/sound-effect/shotgun-franchi-spas-12/440323/
 * <ul> <li> Panda spawn, Handgun shooting sound: Envato Elements, https://mixkit.co/free-sound-effects/gun/
 * <ul> <li> Machine gun shooting sound: Videvo, https://www.videvo.net/sound-effect/we-ak47s-lb-04-hpx/261423/
 * <ul> <li> Flamethrower sound: SmartSound (ZapSplat), https://www.zapsplat.com/sound-effect-category/flame-thrower/
 * <ul> <li> Paper clip saber sound: SoundBoard, https://www.soundboard.com/sb/sound/931007
 * <ul> <li> Button click sound: Plants Vs. Zombies (PopCap Studos)
 * <ul> <li> Summoner summoning sound: Sound Library (YouTube), https://www.soundboard.com/sb/sound/931007
 * <ul> <li> Panda teleport sound: Nicholas Persaud (YouTube), https://www.youtube.com/watch?v=8sZnEYtn664
 * <ul> <lu> Panda stun sound: Full Sound Effects (YouTube), https://www.youtube.com/watch?v=IMOH8QnGBzU
 * <ul> <li> Statbar class: Jordan Cohen
 * <ul> <li> SimpleTimer: Neil Brown
 * 
 * @author Charis C, Ethan L, Henry P, Johnathan L, William T
 * @version June 2022
 * 
 */

public class PaperWorld extends World {
    //initialize variables and values

    private Pencil p;
    private Overlay o;
    private int weaponType;
    private int buffType;
    private int xCoord;
    private int yCoord;
    private int actCount;
    private static DecimalFormat decimalFormatter = new DecimalFormat("0.00");
    private int enemycount;
    private int bossCountDown;
    private int randomEnemySpawnRate;
    private int setEnemySpawnRate;
    private int masterRandomEnemySpawnRate;
    private int masterSetEnemySpawnRate;
    private int setBuffEnemySpawnRate;
    private int masterSetBuffEnemySpawnRate;
    private int setBuffEnemySpawnRateDecreaseAmount;
    private int setEnemySpawnRateDecreaseAmount;
    private int numOfEnemiesThatCanErase;
    private int numOfEnemiesThatCannotErase;
    private int score;
    private int phase;

    public static Color RED = new Color(204, 0, 0, 40);
    public static Color BLUE = new Color(0, 255, 255, 40);
    public static Color GREEN = new Color(0, 255, 0, 40);

    private static boolean gameOver;
    private boolean firstTime;
    private boolean enoughActsPassed;

    private int bossSpeed;
    private int bossEraserWidth;
    private int bossHealth;
    private int bossAttackRate;

    private int bossSpeedIncrement;
    private int bossEraserWidthIncrement;
    private int bossHealthIncrement;
    private int bossAttackRateDecrement;

    private int masterBossCountDown;

    private boolean buffed;
    private boolean playerBuffed;
    private boolean weaponBuffed;

    private static GreenfootSound music;
    private boolean firstTimeEnd;

    private int num;
    private static GreenfootSound endMusic;
    private static GreenfootSound secretEndMusic;

    /**
     * Constructor for objects of class MyWorld.
     * 
     * @param num This integer represents the pencil that the player chose to play as.
     */
    public PaperWorld(int num) {
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(900, 600, 1);

        this.num = num;

        DefaultPencil p2 = new DefaultPencil(3, 29);
        Marker p3 = new Marker(3, 29);
        GoldenPen p4 = new GoldenPen(3, 29);
        LeadPencil p5 = new LeadPencil(3, 29);
        Quill p6 = new Quill(3, 29);

        if (num == 0) {
            addObject(p2, 450, 300);
        } else if (num == 1) {
            addObject(p5, 450, 300);
        } else if (num == 2) {
            addObject(p3, 450, 300);
        } else if (num == 3) {
            addObject(p4, 450, 300);
        } else if (num == 4) {
            addObject(p6, 450, 300);
        }

        o = new Overlay();
        addObject(o, 450, 300);

        gameOver = false;

        setActOrder(Pencil.class, Eraser.class, Overlay.class);
        setPaintOrder(ButtonTitleScreen.class, GameOverScreen.class, StatBar.class, Buffs.class, Fire.class, Weapon.class, Stun.class, Pencil.class, Eraser.class, Projectile.class, IncomingAtkIndicator.class, Hitbox.class, Overlay.class);

        actCount = 0;
        firstTime = true;
        enoughActsPassed = false;

        buffed = false;
        playerBuffed = false;
        weaponBuffed = false;

        masterBossCountDown = 3000;
        bossCountDown = masterBossCountDown;
        masterSetEnemySpawnRate = 550;
        setEnemySpawnRate = masterSetEnemySpawnRate;
        setEnemySpawnRateDecreaseAmount = 10;
        phase = 0;
        masterSetBuffEnemySpawnRate = 800;
        setBuffEnemySpawnRate = masterSetBuffEnemySpawnRate;
        setBuffEnemySpawnRateDecreaseAmount = 10;

        numOfEnemiesThatCannotErase = 0;
        numOfEnemiesThatCanErase = 0;

        bossSpeed = 5;
        bossEraserWidth = 120;
        bossHealth = 350;
        bossAttackRate = 300;

        bossSpeedIncrement = 2;
        bossEraserWidthIncrement = 15;
        bossHealthIncrement = 50;
        bossAttackRateDecrement = 20;

        music = new GreenfootSound("Mastermind UNDERSCORE.wav");
        music.setVolume(50);
        music.playLoop();
        firstTimeEnd = true;

        endMusic = new GreenfootSound("endMusic.wav");
        endMusic.setVolume(25);

        secretEndMusic = new GreenfootSound("Lofi Rickroll.wav");
        secretEndMusic.setVolume(50);
    }

    /**
     * Method to play music when simulation is played.
     */
    public void started() {
        if (!gameOver) {
            music.playLoop();
        } else {
            if (phase >= 7) {
                secretEndMusic.playLoop();
            } else {
                endMusic.playLoop();
            }
        }
    }

    /**
     * Method to stop music when simulation is paused.
     */
    public void stopped() {
        if (!gameOver) {
            music.pause();
        } else {
            if (phase >= 7) {
                secretEndMusic.pause();
            } else {

                endMusic.pause();
            }
        }
    }

    /**
     * Method to stop music .
     */
    public static void stopMusic() {
        music.stop();
        endMusic.stop();
        secretEndMusic.stop();

    }

    /**
     * Act method. 
     */
    public void act() {
        String surfaceArea;
        actCount++;

        //initializes some values
        if (firstTime) {
            gameOver = false;
            firstTime = false;
            showText("Surface Area: 10.00", 110, 10);
            showText("Sprint: " + p.getSprintCount(), 300, 10);
            showText("Score: " + actCount, 440, 10);
        }

        if (actCount >= 15) {
            enoughActsPassed = true;
        }

        //displays necessary game information 
        if (enoughActsPassed) {
            //display text
            if (num == 3) {
                surfaceArea = decimalFormatter.format(o.calculateSurfaceArea() - 0.15);
                if (Overlay.getSurfaceArea() - 0.15 < 10 && !gameOver) {
                    gameOver = true;
                }
            } else {
                surfaceArea = decimalFormatter.format(o.calculateSurfaceArea() - 0.14);
                if (Overlay.getSurfaceArea() - 0.14 < 10 && !gameOver) {
                    gameOver = true;
                }
            }
            showText("Surface Area: " + surfaceArea, 110, 10);
            showText("Sprint: " + p.getSprintCount(), 300, 10);
            if (!gameOver) {
                showText("Score: " + actCount, 440, 10);
            }
        }

        //Spawns erasers if game is not over.
        if (!gameOver) {
            eraserSpawner();
            spawnBuff();
        }
        //Other wise, game over logic and procedures are implemented such as the highscore screen and ending music.
        else if (gameOver && firstTimeEnd) {
            JFrame frame = new JFrame();
            String name = JOptionPane.showInputDialog(frame, "Game over. Enter your name:");

            try {
                FileWriter highscoreUpdater = new FileWriter("highscore.txt", true);
                PrintWriter output = new PrintWriter(highscoreUpdater);
                output.println(name + "," + actCount);
                output.close();
            } catch (IOException e) {
                System.out.println("Error in saving highscore: " + e);
            }

            firstTimeEnd = false;
            addObject(new GameOverScreen(), 450, 300);
            music.stop();
            if (phase >= 7) {
                secretEndMusic.playLoop();
            } else {
                endMusic.playLoop();
            }
        }

    }

    /**
     * Method to return overlay object.
     * 
     * @return Overlay returns the overlay object used in the game.
     */
    public Overlay getOverlay() {
        return o;
    }

    /**
     * Method to spawn erasers in the game. The way the spawning logic works is the erasers spawn in phases that are defined by the number of bosses that have spawned. Every new boss 
     * that spawns progresses the phase by 1. As the phases increase, the frequency of eraser spawning will increase and more difficult eraser enemies are introduced into the game. 
     */
    public void eraserSpawner() {
        //Initialize values. 
        int randomEnemy;
        bossCountDown--;
        setEnemySpawnRate--;
        setBuffEnemySpawnRate--;

        //Makes the erasers spawn faster and faster as the number of acts progress.
        if (actCount % 500 == 0 && masterSetEnemySpawnRate > 100) {
            masterSetEnemySpawnRate -= setEnemySpawnRateDecreaseAmount;
            masterSetBuffEnemySpawnRate -= setBuffEnemySpawnRateDecreaseAmount;
        }

        //code to contorl the different phases and spawn erasers. 
        if (phase == 0) {
            if (setEnemySpawnRate == 0) {
                randomEnemy = Greenfoot.getRandomNumber(2);

                if (randomEnemy == 0) {
                    spawnNormalEraser();
                } else if (randomEnemy == 1) {
                    spawnQuickEraser();
                }
                setEnemySpawnRate = masterSetEnemySpawnRate;
            }

            if (setBuffEnemySpawnRate == 0) {
                spawnPusherEraser();
                setBuffEnemySpawnRate = masterSetBuffEnemySpawnRate;
            }

            if (bossCountDown == 0) {
                spawnGrowerEraser(bossSpeed, bossEraserWidth, bossHealth, bossAttackRate);

                bossCountDown = masterBossCountDown;
                bossSpeed += bossSpeedIncrement;
                bossEraserWidth += bossEraserWidthIncrement;
                bossHealth += bossHealthIncrement;
                bossAttackRate -= bossAttackRateDecrement;;

                phase++;
            }
        } else if (phase == 1) {
            if (setEnemySpawnRate == 0) {
                randomEnemy = Greenfoot.getRandomNumber(4);

                if (randomEnemy == 0) {
                    spawnNormalEraser();
                } else if (randomEnemy == 1) {
                    spawnQuickEraser();
                } else if (randomEnemy == 2) {
                    spawnIntelligentEraser();
                } else if (randomEnemy == 3) {
                    spawnSummonerEraser();
                }
                setEnemySpawnRate = masterSetEnemySpawnRate;
            }

            if (setBuffEnemySpawnRate == 0) {
                spawnPusherEraser();
                setBuffEnemySpawnRate = masterSetBuffEnemySpawnRate;
            }

            if (bossCountDown == 0) {
                spawnGrowerEraser(bossSpeed, bossEraserWidth, bossHealth, bossAttackRate);

                bossCountDown = masterBossCountDown;
                bossSpeed += bossSpeedIncrement;
                bossEraserWidth += bossEraserWidthIncrement;
                bossHealth += bossHealthIncrement;
                bossAttackRate -= bossAttackRateDecrement;;

                phase++;
            }
        } else if (phase == 2) {
            if (setEnemySpawnRate == 0) {
                randomEnemy = Greenfoot.getRandomNumber(4);

                if (randomEnemy == 0) {
                    spawnNormalEraser();
                } else if (randomEnemy == 1) {
                    spawnQuickEraser();
                } else if (randomEnemy == 2) {
                    spawnIntelligentEraser();
                } else if (randomEnemy == 3) {
                    spawnSummonerEraser();
                }
                setEnemySpawnRate = masterSetEnemySpawnRate;
            }

            if (setBuffEnemySpawnRate == 0) {
                randomEnemy = Greenfoot.getRandomNumber(3);

                if (randomEnemy == 0) {
                    spawnPusherEraser();
                } else if (randomEnemy == 1) {
                    spawnSpeedBoostEraser();
                } else if (randomEnemy == 2) {
                    spawnSlowerEraser();
                }
                setBuffEnemySpawnRate = masterSetBuffEnemySpawnRate;
            }

            if (bossCountDown == 0) {
                spawnGrowerEraser(bossSpeed, bossEraserWidth, bossHealth, bossAttackRate);

                bossCountDown = masterBossCountDown;
                bossSpeed += bossSpeedIncrement;
                bossEraserWidth += bossEraserWidthIncrement;
                bossHealth += bossHealthIncrement;
                bossAttackRate -= bossAttackRateDecrement;;

                phase++;
            }
        } else if (phase > 2) {
            if (setEnemySpawnRate == 0) {
                randomEnemy = Greenfoot.getRandomNumber(5);

                if (randomEnemy == 0) {
                    spawnNormalEraser();
                } else if (randomEnemy == 1) {
                    spawnQuickEraser();
                } else if (randomEnemy == 3) {
                    spawnIntelligentEraser();
                } else if (randomEnemy == 4) {
                    spawnSummonerEraser();
                }
                setEnemySpawnRate = masterSetEnemySpawnRate;
            }

            if (Greenfoot.getRandomNumber(masterSetEnemySpawnRate + 200) == 0) {
                randomEnemy = Greenfoot.getRandomNumber(5);

                if (randomEnemy == 0) {
                    spawnNormalEraser();
                } else if (randomEnemy == 1) {
                    spawnQuickEraser();
                } else if (randomEnemy == 3) {
                    spawnIntelligentEraser();
                } else if (randomEnemy == 4) {
                    spawnSummonerEraser();
                }
                setEnemySpawnRate = masterSetEnemySpawnRate;
            }

            if (setBuffEnemySpawnRate == 0) {
                randomEnemy = Greenfoot.getRandomNumber(3);

                if (randomEnemy == 0) {
                    spawnPusherEraser();
                } else if (randomEnemy == 1) {
                    spawnSpeedBoostEraser();
                } else if (randomEnemy == 2) {
                    spawnSlowerEraser();
                }
                setBuffEnemySpawnRate = masterSetBuffEnemySpawnRate;
            }

            if (bossCountDown == 0) {
                spawnGrowerEraser(bossSpeed, bossEraserWidth, bossHealth, bossAttackRate);

                bossCountDown = masterBossCountDown;
                bossSpeed += bossSpeedIncrement;
                bossEraserWidth += bossEraserWidthIncrement;
                bossHealth += bossHealthIncrement;

                if (bossAttackRate > 50) {
                    bossAttackRate -= bossAttackRateDecrement;
                }

                phase++;
            }
        }

    }

    /**
     * Method to return if the game is over. 
     * 
     * @return boolean returns boolean of whether or not game is over. 
     */
    public static boolean getGameOver() {
        return gameOver;
    }

    /**
     * Method to spawn a NormalEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnNormalEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new NormalEraser(3, 20), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new NormalEraser(3, 20), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new NormalEraser(3, 20), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new NormalEraser(3, 20), 890, randomY);
        }
    }

    /**
     * Method to spawn an IntelligentEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnIntelligentEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new IntelligentEraser(2, 30), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new IntelligentEraser(2, 30), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new IntelligentEraser(2, 30), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new IntelligentEraser(2, 30), 890, randomY);
        }
    }

    /**
     * Method to spawn a QuickEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnQuickEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new QuickEraser(6, 20), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new QuickEraser(6, 20), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new QuickEraser(6, 20), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new QuickEraser(6, 20), 890, randomY);
        }
    }

    /**
     * Method to spawn a SlowerEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnSlowerEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new SlowerEraser(4), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new SlowerEraser(4), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new SlowerEraser(4), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new SlowerEraser(4), 890, randomY);
        }
    }

    /**
     * Method to spawn a SummonerEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnSummonerEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new SummonerEraser(2, 20), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new SummonerEraser(2, 20), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new SummonerEraser(2, 20), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new SummonerEraser(2, 20), 890, randomY);
        }
    }

    /**
     * Method to spawn a PusherEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnPusherEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new PusherEraser(3), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new PusherEraser(3), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new PusherEraser(3), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new PusherEraser(3), 890, randomY);
        }
    }

    /**
     * Method to spawn a SpeedBoostEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnSpeedBoostEraser() {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new SpeedBoostEraser(5), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new SpeedBoostEraser(5), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new SpeedBoostEraser(5), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new SpeedBoostEraser(5), 890, randomY);
        }
    }

    /**
     * Method to spawn a GrowerEraser at a random coordinate on one of the four sides of the world borders. 
     */
    public void spawnGrowerEraser(int speed, int eraserWidth, int hp, int attackRate) {
        int randomX = Greenfoot.getRandomNumber(900);
        int randomY = Greenfoot.getRandomNumber(600);

        int randomLocation = Greenfoot.getRandomNumber(4);
        if (randomLocation == 0) {
            addObject(new GrowerEraser(speed, eraserWidth, hp, attackRate), randomX, 20);
        } else if (randomLocation == 1) {
            addObject(new GrowerEraser(speed, eraserWidth, hp, attackRate), randomX, 588);
        } else if (randomLocation == 2) {
            addObject(new GrowerEraser(speed, eraserWidth, hp, attackRate), 0, randomY);
        } else if (randomLocation == 3) {
            addObject(new GrowerEraser(speed, eraserWidth, hp, attackRate), 890, randomY);
        }
    }

    /**
     * Method to spawn a random weapon at a random coordinate in the world when called. 
     */
    public void spawnWeapon() {

        xCoord = Greenfoot.getRandomNumber(900);
        yCoord = Greenfoot.getRandomNumber(600);
        weaponType = Greenfoot.getRandomNumber(5) + 2;
        Weapon weapon;
        if (weaponType ==2) {
            weapon = new Shotgun(null);
            weapon.getImage().scale(90, 18);
            addObject(weapon, xCoord, yCoord + 75);
            weapon.getImage().setTransparency(0);
        }  if (weaponType == 3) {
            weapon = new MachineGun(null);
            weapon.getImage().scale(85, 25);
            addObject(weapon, xCoord, yCoord - 75);
            weapon.getImage().setTransparency(0);
        }  if (weaponType == 4) {
            weapon = new Flamethrower(null);
            weapon.getImage().scale(72, 36);
            addObject(weapon, xCoord, yCoord);
            weapon.getImage().setTransparency(0);
        }  if (weaponType == 5) {
            weapon = new PaperClipSaber(null);
            weapon.getImage().scale(66, 180);
            addObject(weapon, xCoord, yCoord + 150);
            weapon.getImage().setTransparency(0);
        }
    }

    /**
     * Method to spawn a random buff at a random coordinate in the world based on luck.
     */
    public void spawnBuff() {
        xCoord = Greenfoot.getRandomNumber(600) + 100;
        yCoord = Greenfoot.getRandomNumber(400) + 100;
        buffType = Greenfoot.getRandomNumber(1000);
        if (!buffed && !playerBuffed && !weaponBuffed) {
            if (buffType == 1) {
                addObject(new SpeedBoost(), xCoord, yCoord);
                buffed = true;
            } else if (buffType == 2) {
                addObject(new InkWidthBoost(), xCoord, yCoord);
                buffed = true;
            } else if (buffType == 3) {
                addObject(new AttackSpeedBoost(), xCoord, yCoord);
                buffed = true;
            }
        }
    }

    /**
     * Setter method to set playerBuffed boolean.
     */
    public void setPlayerBuffed() {
        playerBuffed = true;
    }

    /**
     * Setter method to set weaponBuffed boolean.
     */
    public void setWeaponBuffed() {
        weaponBuffed = true;
    }

    /**
     * Setter method to set playerBuffed and buffed boolean.
     */
    public void setPlayerBuffedFalse() {
        playerBuffed = false;
        buffed = false;
    }

    /**
     * Setter method to set weaponBuffed and buffed boolean
     */
    public void setWeaponBuffedFalse() {
        weaponBuffed = false;
        buffed = false;
    }

    /**
     * Getter Method to return playerBuffed boolean.
     * 
     * @return boolean Returns playerBuffed boolean.
     */
    public boolean getPlayerBuffed() {
        return playerBuffed;
    }

    /**
     * getter method to return weaponBuffed boolean. 
     * 
     * @return boolean Returns weaponBuffed boolean.
     */
    public boolean getWeaponBuffed() {
        return weaponBuffed;
    }
}