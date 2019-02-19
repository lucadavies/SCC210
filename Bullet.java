import java.lang.*;

import org.jsfml.graphics.*;
import org.jsfml.system.*;
/*
 *Class for all characters in the game to be based on.
 *Both enemies and the player (+ allies)
 */

public class Bullet extends MovingEntity {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private String BULLET_COLOUR;
    private float BULLET_SPEED;
    private String direction;
    private boolean used = false;

    private RectangleShape bullet = new RectangleShape(new Vector2f(WIDTH, HEIGHT));


    public Bullet(int x, int y, String c, float s, String d) {
        super(x, y, WIDTH, HEIGHT, c);
        this.x = x;
        this.y = y;
        BULLET_COLOUR = c;
        BULLET_SPEED = s;
        direction = d;
        bullet.setPosition(x, y);
    }

    @Override
    public void move() {
        if (canMove()) {
            if (direction.equals("left")) {
                x -= BULLET_SPEED;
            } else if (direction.equals("right")) {
                x += BULLET_SPEED;
            } else if (direction.equals("up")) {
                y -= BULLET_SPEED;
            } else if (direction.equals("down")) {
                y += BULLET_SPEED;
            }
        } else {
            used = true;
        }
        getHitBox().setPosition(x, y);
    }

    private boolean canMove() {
        boolean blocked = false;
        int[] t = getOccupiedTiles();
        int tileX = t[0];
        int tileY = t[1];
        if (tileX == -1 || tileY == -1 || !level.getTiles()[tileX][tileY].canShootThrough()) {  //bottom-right tile
            blocked = true;
            //System.out.println("Down move blocked by [" + tileX + "][" + tileY + "]");
        }
        return !blocked;
    }

    public void next() {
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
