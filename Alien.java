import java.util.*;

import org.jsfml.graphics.RectangleShape;

/*
 *Class for the enemy character
 */
public abstract class  Alien extends Character {

    private static final float PLAYER_WIDTH = 60;
    private static final float PLAYER_HEIGHT = 60;


    private static ArrayList<Bullet> firedBullets = new ArrayList<>();


    //some bools which could be useful after introducing pickups/abilities
    private boolean isSolid;
    private boolean isInvincible;
    private boolean isDead;
    private boolean isMoving = false;

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
    public Alien(int x, int y, int speed, String characterTexture) {
        super(x, y, characterTexture);
        setSpriteWithinSheet(0, 1);
        this.speed = speed;
    }

    /*@Override
    public void moveLeft() {
        super.setSpriteWithinSheet(0, 2);
        x -= speed;
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveUp() {
        super.setSpriteWithinSheet(0, 2);
        y -= speed;
        getHitBox().setPosition(x, y);
    }

    @Override
    public void moveRight() {
        super.setSpriteWithinSheet(0, 2);
        x += speed;
        getHitBox().setPosition(x, y);
    }


    @Override
    public void moveDown() {
        super.setSpriteWithinSheet(0, 2);
        y += speed;
        getHitBox().setPosition(x, y);
    }*/

    //this method makes the enemy slowly chase the player.
    //in statement "x-+3" the number is the variable that changes the speed of the enemy
    @Override
    public void move() {
        int pX = Player.getPlayerInstance().getX();
        int pY = Player.getPlayerInstance().getY();
        if (pX > x && canMoveRight()) { //move right
            x += speed;
        }
        if (pX < x && canMoveLeft()) { //move left
            x -= speed;
        }
        if (pY < y && canMoveUp()) { //move up
            y -= speed;
        }
        if (pY > y && canMoveDown()) { //move down
            y += speed;
        }
        if (Player.getPlayerInstance().getHitBox().entityColliding(getHitBox().getRectBox()))
        {
            Player.getPlayerInstance().kill();
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
        super.setSpriteWithinSheet(0, 1);
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

    public boolean getIsMoving() {
        return isMoving;
    }

    public void isMoving() {
        isMoving = true;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


}
