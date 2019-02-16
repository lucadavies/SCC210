import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;
import java.util.stream.Collectors;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

/*
 *Class for the user controlled player
 */
public class Player extends Character {

    private static final float PLAYER_WIDTH = 60;
    private static final float PLAYER_HEIGHT = 60;
    private static final float PLAYER_SPEED = 5;
    static float speedChange;


    private static ArrayList<Bullet> firedBullets = new ArrayList<>();


    //some bools which could be useful after introducing pickups/abilities
    private boolean isSolid;
    private boolean isInvincible;
    private boolean isDead;

    //booleans for pickUps.
    private boolean sLaserGunPickedUp = false;
    private boolean speedUp = false;
    private boolean frozenStone = false;

    //how many bullets are int the chamber
    private int chamber = 1;

    //how many bullets the player can shoot before it needs resetting
    private int chamberLimit = 1;


    //hitbox
    public CollisionBox playerHitBox = new CollisionBox(200, 200, PLAYER_WIDTH, PLAYER_HEIGHT);


    private static Player player = new Player(); //creating the singleton instance


    /*
     *Class for the user controlled player.
     *
     *This is a singleton class (private constructor)
     *to ensure that only one instance is created.
     */
    private Player() {
        super(100, 100, "art/player.png", PLAYER_WIDTH, PLAYER_HEIGHT);
        super.setSpriteWithinSheet(1, 1);


    }


    //method to get the single instance of the player
    public static Player getPlayerInstance() {
        return player;
    }

    @Override
    public void moveLeft() {
        super.setSpriteWithinSheet(0, 2);
        x -= (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveUp() {
        super.setSpriteWithinSheet(0, 2);
        y -= (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveRight() {
        super.setSpriteWithinSheet(0, 2);
        x += (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }


    @Override
    public void moveDown() {
        super.setSpriteWithinSheet(0, 2);
        y += (speedUp ? speedChange : PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }


    public void shootBulletLeft() {
        if (chamber >= 1) {
            Bullet b = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBullet.png" : "art/bullet.png"), 10, "left");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
        }
    }

    public void shootBulletRight() {
        if (chamber >= 1) {
            Bullet b = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBullet.png" : "art/bullet.png"), 10, "right");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
        }
    }

    public void shootBulletUp() {
        if (chamber >= 1) {
            Bullet b = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBulletUpsideDown.png" : "art/bullet.png"), 10, "up");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
        }
    }

    public void shootBulletDown() {
        if (chamber >= 1) {
            Bullet b = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBulletUpsideDown.png" : "art/bullet.png"), 10, "down");
            b.setMap(level);
            firedBullets.add(b);
            chamber--;
        }
    }

    //loads the chamber
    public void loadChamber() {
        if (chamber < chamberLimit) {
            chamber++;
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
        super.setSpriteWithinSheet(1, 1);
    }

    public ArrayList<Bullet> getFiredBullets() {
        return firedBullets;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isDead() {
        return isDead;
    }

    public float getX() {
        return x;
    }

    public float getY() {
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

    public float getSpeedChange() {
        return speedChange;
    }
}
