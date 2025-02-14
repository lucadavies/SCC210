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

public class Pickup extends Entity {

    private static final int HEIGHT = 40;
    private static final int WIDTH = 40;
    private float xLoc = 0;
    private float yLoc = 0;
    private String PICKUP_IMAGE;
    private boolean pickedUp = false;
    private pickUpType type;
    //private pickUpType type;

    //Creates a list of pickUps.
    public enum pickUpType {
        LASER_GUN,
        SUPER_LASER_GUN,
        BOMB,
        MISSILE,
        MIRROR,
        BOOTS,
        ALIEN_MESS,
        NUKE,
        FREEZE,
        FAST_CLOCK,
        SLOW_CLOCK,
        ANGEL_WINGS,
        DOUBLE_SHOT,
        TRIPLE_SHOT,
        SUPER_SHOT,
        OMNI_SHOT
    }

    //constructor for pickUps.
    public Pickup(int x, int y, pickUpType pickup) {
        super(x, y, WIDTH, HEIGHT, "res/art/pickups/" + pickup + ".png");
        xLoc = x;
        yLoc = y;
        type = pickup;
    }


    public float getPickupx() {
        return xLoc;
    }

    public float getPickupy() {
        return yLoc;
    }

    public void setPickedUp() {
        pickedUp = true;
    }

    public void notPickedUp() {
        pickedUp = false;
    }

    public boolean hasPickedUp() {
        return pickedUp;
    }

    public pickUpType getType() {
        return type;
    }

    public void setPosition(int xPos, int yPos) {
        x = xPos;
        y = yPos;
        getHitBox().setPosition(x, y);
    }

    //Method which removes pick up from the screen.
    public void removePickUp(pickUpType pickUp) {
        pickUp = null;
    }
}
