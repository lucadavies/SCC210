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


    public void moveBulletLeft() {
        //System.out.println("bullet x:"+x);

        getHitBox().setPosition(x, y);
    }

    public void moveBulletRight() {


        getHitBox().setPosition(x, y);
    }

    public void moveBulletUp() {

        getHitBox().setPosition(x, y);
    }

    public void moveBulletDown() {

        getHitBox().setPosition(x, y);
    }

    @Override
    public void move() {
        if (direction.equals("left") && !getHitBox().colliding()) {
            x -= BULLET_SPEED;
        }
        else if (direction.equals("right") && !getHitBox().colliding()) {
            x += BULLET_SPEED;
        }
        else if (direction.equals("up") && !getHitBox().colliding()) {
            y -= BULLET_SPEED;
        }
        else if (direction.equals("down") && !getHitBox().colliding()) {
            y += BULLET_SPEED;
        }
        else {
            System.out.println("Bullet colliding");
        }
    }

    public void moveBullet() {
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
