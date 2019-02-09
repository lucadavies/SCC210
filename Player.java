import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

/*
 *Class for the user controlled player
 */
public class Player extends Character {

    public static final float PLAYER_WIDTH = 60;
    public static final float PLAYER_HEIGHT = 60;
    public static final float PLAYER_SPEED = 5;
    public static float speedChange;


    public static ArrayList<Bullet> firedBullets = new ArrayList<>();


    //some bools which could be useful after introducing pickups/abilities
    public boolean isSolid;
    public boolean isInvincible;
    public boolean isDead;

    //booleans for pickUps.
    public boolean sLaserGunPickedUp = false;
    public boolean speedUp = false;
    public boolean frozenStone = false;

    public String lastMoveDirection = "null";

    //how many bullets are int the chamber
    public int chamber = 1;

    //how many bullets the player can shoot before it needs resetting
    public int chamberLimit = 1;


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
        y -= (speedUp ? speedChange:PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveRight() {
        super.setSpriteWithinSheet(0, 2);
        x += (speedUp? speedChange :PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }


    @Override
    public void moveDown() {
        super.setSpriteWithinSheet(0, 2);
        y += (speedUp ? speedChange: PLAYER_SPEED);
        getHitBox().setPosition(x, y);
    }


    public void shootBulletLeft() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBullet.png" : "art/bullet.jpg"), 10, "left");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletRight() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBullet.png" : "art/bullet.jpg"), 10, "right");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletUp() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBulletUpsideDown.png" : "art/bullet.jpg"), 10, "up");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletDown() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, (sLaserGunPickedUp ? "art/pickups/superBulletUpsideDown.png" : "art/bullet.jpg"), 10, "down");
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
    }

    public boolean getFrozenStone(){
       return frozenStone;
    }

    public void setFrozenStone(){
       frozenStone = true;
    }

    public void setFrozenStoneFalse(){
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

    public void setSuperLaserGunPickedUp(){
      sLaserGunPickedUp = true;
    }

    public void setSuperLaserGunFalse(){
      sLaserGunPickedUp = false;
    }

    public void setSpeedUpTrue(){
      speedUp = true;
    }
    public void setSpeedUpFalse(){
      speedUp = false;
    }
    public boolean getSpeedUp(){
      return speedUp;
    }

    public void setChamber(int x) {
        chamber = x;
        chamberLimit = x;
    }

    public float getSpeedChange(){
      return speedChange;
    }

    public String getLastMove(){return lastMoveDirection;}
}
