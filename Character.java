import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;

import javafx.geometry.Bounds;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;

/*
 *Class for all characters in the game to be extended off of.
 *Both enemies and the player (+ allies)
 */

public abstract class Character extends MovingEntity {

    private int health;
    private boolean isAlive = true;
    private boolean isSolid;
    private float speed = 10;

    public Character(int x, int y, String characterTexture, float width, float height) {
        super(x, y, 0, characterTexture, width, height, 0);
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void kill() {
        isAlive = false;
    }

    public void live(){
      isAlive = true;
    }

}
