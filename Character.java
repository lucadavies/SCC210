import java.util.*;
import java.lang.Object;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;

/*
 *Class for all characters in the game to be extended off of.
 *Both enemies and the player (+ allies)
 */

public abstract class Character extends MovingEntity {

    int health;
    private boolean isAlive = true;

    public Character(int x, int y, int width, int height, String characterTexture, int health) {
        super(x, y, width, height, characterTexture);
        this.health = health;
    }

    public boolean isAlive() {
        return this.isAlive;
    }
    public void setAlive(boolean isAlive){ this.isAlive=isAlive;}

    /**
     * Character take hit, decreases health by one. If health is decreased to zero, Character is no longer alive
     * @return true is Character has been killed, else returns false
     */
    public boolean hit() {
        health--;
        if (health == 0) {
            isAlive = false;
            return true;
        } else {
            return false;
        }
    }

}
