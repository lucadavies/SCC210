import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

//import javafx.geometry.BoundingBox;

import java.lang.Object;
import java.util.stream.Collectors;

import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

/*
 *Class for the user controlled player
 */
public class Player extends Character {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private static final int PLAYER_SPEED = 5;
    private int lives = 3;
    static float speedChange;
    private Sound gunFireSFX;


    private static ArrayList<Bullet> firedBullets = new ArrayList<>();

    //booleans for pickUps.
    private boolean sLaserGunPickedUp = false;
    private boolean speedUp = false;
    private boolean frozenStone = false;

    //how many bullets are in the chamber
    private int chamber = 1;

    //how many bullets the player can shoot before it needs resetting
    private int chamberLimit = 1;

    private static Player player = new Player(); //creating the singleton instance


    /*
     *Class for the user controlled player.
     *
     *This is a singleton class (private constructor)
     *to ensure that only one instance is created.
     */
    private Player() {
        super(500, 480, WIDTH, HEIGHT, "art/player/player.png", 1);
        super.setSpriteWithinSheet(0, 0);
        SoundBuffer tempBuf = new SoundBuffer();
        try {
            tempBuf.loadFromFile(Paths.get("audio/gunshot.wav"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        gunFireSFX = new Sound(tempBuf);
    }

    public void reset() {
        firedBullets.clear();
        setCoordnts(500, 480);
        health = 1;
    }

    //method to get the single instance of the player
    public static Player getPlayerInstance() {
        return player;
    }

    @Override
    public void moveLeft() {
        x -= (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveUp() {
        y -= (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveRight() {
        x += (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveDown() {
        y += (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    public void shootBulletLeft() {
        if (chamber >= 1) {
            Bullet b = new Bullet(x, y, (sLaserGunPickedUp ? "art/pickups/superBullet.png" : "art/bullet.png"), 10, "left");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
            gunFireSFX.play();
        }
    }

    public void shootBulletRight() {
        if (chamber >= 1) {
            Bullet b = new Bullet(x, y, (sLaserGunPickedUp ? "art/pickups/superBullet.png" : "art/bullet.png"), 10, "right");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
            gunFireSFX.play();
        }
    }

    public void shootBulletUp() {
        if (chamber >= 1) {
            Bullet b = new Bullet(x, y, (sLaserGunPickedUp ? "art/pickups/superBulletUpsideDown.png" : "art/bullet.png"), 10, "up");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
            gunFireSFX.play();
        }
    }

    public void shootBulletDown() {
        if (chamber >= 1) {
            Bullet b = new Bullet(x, y, (sLaserGunPickedUp ? "art/pickups/superBulletUpsideDown.png" : "art/bullet.png"), 10, "down");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
            gunFireSFX.play();
        }
    }

    //loads the chamber
    public void loadChamber() {
        if (chamber < chamberLimit) {
            chamber++;
            gunFireSFX.setPitch((float)(rnd.nextFloat() * 0.5 + 1));
        }
    }

    public void removeUsedBullets() {
        firedBullets = (ArrayList<Bullet>) firedBullets.stream().filter(b -> !b.isUsed()).collect(Collectors.toList());
    }

    public boolean getFrozenStone() {
        return frozenStone;
    }

    public void setFrozenStone() {
        frozenStone = true;
    }

    public void setFrozenStoneFalse() {
        frozenStone = false;
    }

    public void standingStill() {
        super.setSpriteWithinSheet(0, 1);
    }

    public ArrayList<Bullet> getFiredBullets() {
        return firedBullets;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordnts(int xloc, int yloc) {
        x = xloc;
        y = yloc;
    }

    public void setSuperLaserGunPickedUp() {
        sLaserGunPickedUp = true;
    }

    public void setSuperLaserGunFalse() {
        sLaserGunPickedUp = false;
    }

    public void setSpeedUpTrue() {
        speedUp = true;
    }

    public void setSpeedUpFalse() {
        speedUp = false;
    }

    public boolean getSpeedUp() {
        return speedUp;
    }

    public void setChamber(int x) {
        chamber = x;
        chamberLimit = x;
    }

    public int getChamber(){
      return chamber;
    }

    public boolean hit() {
        lives--;
        return super.hit();
    }
    public int getLives() {
        return lives;
    }
}
