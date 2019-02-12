import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

/*
 *Class for the enemy character
 */
public class Alien extends Character {

    public float ENEMY_X = 350;
    public float ENEMY_Y = 350;
    public static final float PLAYER_WIDTH = 60;
    public static final float PLAYER_HEIGHT = 60;
    public static final float PLAYER_SPEED = 5;


    public static ArrayList<Bullet> firedBullets = new ArrayList<>();


    //some bools which could be useful after introducing pickups/abilities
    public boolean isSolid;
    public boolean isInvincible;
    public boolean isDead;
    public boolean isMoving = false;

    //how many bullets are int the chamber
    public int chamber = 1;
    //how many bullets the player can shoot before it needs resetting
    public int chamberLimit = 1;

    /*
     *Class for the user controlled player.
     *
     *This is a singleton class (private constructor)
     *to ensure that only one instance is created.
     */
    public Alien(int x, int y, String characterTexture) {
        super(x, y, characterTexture, PLAYER_WIDTH, PLAYER_HEIGHT);
        super.setSpriteWithinSheet(1, 1);
    }

    @Override
    public void moveLeft() {
        super.setSpriteWithinSheet(0, 2);
        x -= PLAYER_SPEED;
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveUp() {
        super.setSpriteWithinSheet(0, 2);
        y -= PLAYER_SPEED;
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveRight() {
        super.setSpriteWithinSheet(0, 2);
        x += PLAYER_SPEED;
        getHitBox().setPosition(x, y);
    }


    @Override
    public void moveDown() {
        super.setSpriteWithinSheet(0, 2);
        y += PLAYER_SPEED;
        getHitBox().setPosition(x, y);
    }

    //this method makes the enemy slowly chase the player.
    //in statement "x-+3" the number is the variable that changes the speed of the enemy
    public void moveEnemy(float playerX, float playerY, float speed1, float speed2) {
        super.setSpriteWithinSheet(0, 2);
        if (playerX > x && canMoveRight()) { //move right
            float diff = playerX - x;
            if (diff > 100) {
                x += speed1;
            }
            if (diff < 100 && diff > 50) {
                x += speed2;
            }
            if (diff < 50 && diff > 25) {
                x += speed2;
            }
        }
        if (playerX < x && canMoveLeft()) { //move left
            float diff = x - playerX;
            if (diff > 100) {
                x -= speed1;
            }
            if (diff < 100 && diff > 50) {
                x -= speed2;
            }
            if (diff < 50 && diff > 25) {
                x -= speed2;
            }
        }
        if (playerY < y && canMoveUp()) {  //move up
            float diff = y - playerY;
            if (diff > 100) {
                y -= speed1;
            }
            if (diff < 100 && diff > 50) {
                y -= speed1;
            }
            if (diff < 50 && diff > 25) {
                y -= speed2;
            }
        }
        if (playerY > y && canMoveDown()) {  //move down
            float diff = playerY - y;
            if (diff > 100) {
                y += speed1;
            }
            if (diff < 100 && diff > 50) {
                y += speed1;
            }
            if (diff < 50 && diff > 25) {
                y += speed2;
            }
        }

        getHitBox().setPosition(x, y);
    }


    /*public void shootBulletLeft() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "left");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletRight() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "right");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletUp() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "up");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletDown() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "down");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    //loads the chamber
    public void loadChamber() {
        if (chamber < chamberLimit) {
            chamber++;
        }
    }*/

    public void standingStill() {
        super.setSpriteWithinSheet(1, 1);
    }

    public ArrayList<Bullet> getFiredBullets() {
        return firedBullets;
    }

    public RectangleShape getRectBox() {
        return getHitBox().getRectBox();
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

    public boolean getIsMoving(){
        return isMoving;
    }

    public void isMoving(){
       isMoving = true;
    }

    public void setPosition(int x,int y){
        ENEMY_X =x;
        ENEMY_Y =y;
    }


}
