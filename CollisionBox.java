import java.util.*;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.*;
import org.jsfml.graphics.*;

/*
 *Class that represents a hit box that is attached to all actors which require collision
 */

public class CollisionBox {

    private float HITBOX_HEIGHT = 0;
    private float HITBOX_WIDTH = 0;
    private float HITBOX_X = 0;
    private float HITBOX_Y = 0;
    private RectangleShape hitbox = new RectangleShape(new Vector2f(HITBOX_WIDTH, HITBOX_HEIGHT));

    public CollisionBox(float x, float y, float w, float h) {
        HITBOX_WIDTH = w;
        HITBOX_HEIGHT = h;
        HITBOX_X = x;
        HITBOX_Y = y;

        hitbox.setOutlineColor(Color.RED);
        //hitbox.setOutlineThickness(2);
        hitbox.setSize(new Vector2f(w, h));

        setPosition(HITBOX_X, HITBOX_Y);
    }

    public void setPosition(float x, float y) {
        HITBOX_X = x;
        HITBOX_Y = y;
        hitbox.setPosition(HITBOX_X, HITBOX_Y);
        //System.out.println("hitbox x,y: " + HITBOX_X + " - " + HITBOX_Y);
    }


    //will be primarily used for checking if a bullet entity collides with an enemy
    public boolean collisionCheck(RectangleShape hitBox) {

        for (Alien enemy : new ArrayList<>(Driver.enemies)) {

            if (new FloatRect(hitbox.getPosition().x, hitbox.getPosition().y,
                    hitbox.getSize().x, hitbox.getSize().y).intersection(new FloatRect(enemy.getRectBox().getPosition().x,
                    enemy.getRectBox().getPosition().y, enemy.getRectBox().getSize().x, enemy.getRectBox().getSize().y)) != null) {
                return true;
            }
        }
        return false;
    }

//general entity->entity collision checker
//every entity that requires collision will have a hitbox, the hitbox's are whats passed to this method
    public boolean entityCollisionCheck(RectangleShape hitbox1,RectangleShape hitbox2){

      if(new FloatRect(hitbox1.getPosition().x,hitbox1.getPosition().y,
          hitbox1.getSize().x,hitbox1.getSize().y).intersection(new FloatRect(hitbox2.getPosition().x,
              hitbox2.getPosition().y,hitbox2.getSize().x,hitbox2.getSize().y)) != null){
                return true;
          }
          return false;
        }

    public RectangleShape getRectBox() {
        return hitbox;
    }

    public float getHitBoxX() {
        return HITBOX_X;
    }

    public float getHitBoxY() {
        return HITBOX_Y;
    }

}
