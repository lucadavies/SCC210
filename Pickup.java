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

    private static final float PICKUP_HEIGHT = 40;
    private static final float PICKUP_WIDTH = 40;
    private float xLoc = 0;
    private float yLoc = 0;
    private String PICKUP_IMAGE;
    private boolean pickedUp = false;
    private CollisionBox pickUpHitbox = new CollisionBox(xLoc, yLoc, PICKUP_WIDTH, PICKUP_HEIGHT);
    pickUpType pickup1;
    //private pickUpType pickup1;

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
        superBullet,
        allDirectionsCapsule
    }

    //constructor for pickUps.
    public Pickup(float x, float y, float w, float h, pickUpType pickup) {
        super((int) x, (int) y, 0, "art/pickups/" + pickup + ".png", PICKUP_WIDTH, PICKUP_HEIGHT, 0);
        super.setSpriteWithinSheet(0, 0);
        xLoc = x;
        yLoc = y;
        pickup1 = pickup;



        pickUpHitbox.setPosition(xLoc, yLoc);

    }


    public float getPickupx() {
        return xLoc;
    }

    public float getPickupy() {
        return yLoc;
    }

    public void setPickedUp(){
      pickedUp = true;
    }

    public boolean hasPickedUp(){
      return pickedUp;
    }

    public pickUpType getPickup(){
       return pickup1;
    }

    public void setPosition(float xPos, float yPos){
      x = xPos;
      y = yPos;
      pickUpHitbox.setPosition(x,y);
    }

    //Method which removes pick up from the screen.
    public void removePickUp(pickUpType pickUp) {
        pickUp = null;
    }

    public CollisionBox getHitBox(){
      return pickUpHitbox;
    }


}
