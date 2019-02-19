import java.util.*;

import org.jsfml.graphics.RectangleShape;

/*
 *Class for the enemy character
 */
public abstract class  Alien extends Character {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;


    private static ArrayList<Bullet> firedBullets = new ArrayList<>();


    //some bools which could be useful after introducing pickups/abilities
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
    public Alien(int x, int y, int speed, String characterTexture, int health) {
        super(x, y, WIDTH, HEIGHT, characterTexture, health);
        setSpriteWithinSheet(0, 1);
        this.speed = speed;
    }

    public Alien(int x, int y, int width, int height, int speed, String characterTexture, int health) {
        super(x, y, width, height, characterTexture, health);
        setSpriteWithinSheet(0, 1);
        this.speed = speed;
    }

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
            Player.getPlayerInstance().hit();
        }
        getHitBox().setPosition(x, y);
    }

    public void standingStill() {
        super.setSpriteWithinSheet(0, 1);
    }

    public ArrayList<Bullet> getFiredBullets() {
        return firedBullets;
    }

    public RectangleShape getRectBox() {
        return getHitBox().getRectBox();
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
