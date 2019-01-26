/*
 *Class for all characters in the game to be based on.
 *Both enemies and the player (+ allies)
 */

import java.util.*;
import java.lang.Object;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

public class Pickup extends MovingEntity {

    private float PICKUP_HEIGHT = 10;
    private float PICKUP_WIDTH = 10;
    private float xLoc = 0;
    private float yLoc = 0;
    private String PICKUP_IMAGE;
    private CollisionBox pickUpHitbox = new CollisionBox(xLoc, yLoc, PICKUP_WIDTH, PICKUP_HEIGHT);

    //Creates a list of pickUps.
    public enum pickUpType {
        smallLaserGun,
        superLaserGun,
        bomb,
        misile,
        oldMirror,
        boots,
        alienMess,
        vaccumCleaner,
        frozenStone,
        fastStopWatch,
        slowStopWatch,
        angleWings,
        doubleBullet,
        tripleBullet,
        superBullet
    }

    //constructor for pickUps.
    public Pickup(float x, float y, float w, float h, pickUpType pickup, String d) {
        super((int) x, (int) y, 0, d, w, h, 0);
        super.setSpriteWithinSheet(1, 1);
        PICKUP_HEIGHT = h;
        PICKUP_WIDTH = w;
        xLoc = x;
        yLoc = y;
        pickUpHitbox.setPosition(xLoc, yLoc);

        switch (pickup) {
            case smallLaserGun:
                d = "art/smallGun.png";
                break;
            case superLaserGun:
                d = "art/superLaserGun.png";
                break;
            case bomb:
                d = "art/Bomb.png";
                break;
            case misile:
                d = "art/Missile.png";
                break;
            case oldMirror:
                d = "art/oldMirror.png";
                break;
            case boots:
                d = "art/boots.png";
                break;
            case alienMess:
                d = "art/alienMess.png";
                break;
            case vaccumCleaner:
                d = "art/vaccumCleaner.png";
                break;
            case frozenStone:
                d = "art/frozen.png";
                break;
            case fastStopWatch:
                d = "art/fastStopWatch.png";
            case slowStopWatch:
                d = "art/slowStopWatch.png";
                break;
            case angleWings:
                d = "art/angleWings.png";
                break;
            case doubleBullet:
                d = "art/doubleBullet.png";
                break;
            case tripleBullet:
                d = "art/tripleBullet.png";
                break;
            case superBullet:
                d = "art/superBullet.png";
                break;

        }

    }


    public float getPickupx() {
        return xLoc;
    }

    public float getPickupy() {
        return yLoc;
    }

    //Method which removes pick up from the screen.
    public void removePickUp(pickUpType pickUp) {
        pickUp = null;
    }


}

