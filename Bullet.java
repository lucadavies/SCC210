import java.lang.*;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
/*
 *Class for all characters in the game to be based on.
 *Both enemies and the player (+ allies)
 */

public class Bullet extends MovingEntity {

    private float BULLET_WIDTH = 5;
    private float BULLET_HEIGHT = 5;
    private String BULLET_COLOUR;
    private float BULLET_SPEED;
    private String direction;
    private boolean used = false;

    private int SCREEN_WIDTH = 500;
    private int SCREEN_HEIGHT = 500;

    public boolean sBulletPickedUp = false;


    private RectangleShape bullet = new RectangleShape(new Vector2f(BULLET_WIDTH, BULLET_HEIGHT));


    public Bullet(float x, float y, float w, float h, String c, float s, String d) {
        super((int) x, (int) y, 0, c, w, w, 0);
        this.x = x;
        this.y = y;
        BULLET_WIDTH = w;
        BULLET_HEIGHT = h;
        BULLET_COLOUR = c;
        BULLET_SPEED = s;
        direction = d;
        bullet.setPosition(x, y);
    }

    @Override
    public void move() {
        if (direction.equals("left")) {
            x -= BULLET_SPEED;
        } else if (direction.equals("right")) {
            x += BULLET_SPEED;
        } else if (direction.equals("up")) {
            y -= BULLET_SPEED;
        } else if (direction.equals("down")) {
            y += BULLET_SPEED;
        } else {
            used = true;
        }
        getHitBox().setPosition(x, y);
    }

    public boolean isColliding(Entity e) {
        return getHitBox().isColliding(e);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public RectangleShape getBullet() {
        return bullet;
    }

    public float getBulletx() {
        return x;
    }

    public float getBullety() {
        return y;
    }


}
