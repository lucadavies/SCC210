import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

//this will be the class that reads
//the sprite sheets for the animated sprites
//---
//Its loosely based on the example image class

public class MovingEntity extends Entity {

    BiConsumer<Float, Float> setPosition;
    private float speed = 5;

    public MovingEntity(int x, int y, int r, String textureFile, float width, float height, int lineNumber) {
        super(x, y, r, textureFile, width, height, lineNumber);
        setPosition = getSprite()::setPosition;
    }

    //
    // work out where object should be for next frame
    //
    void calcMove(int minx, int miny, int maxx, int maxy, float xPos, float yPos) {
        //
        //add deltas to x and y setPosition
        //
        x = xPos;
        y = yPos;

        //
        // check we've not collided with any other actor
        //
        /*for (Entity a : actors) {
            if (a.obj != obj && a.within(x, y)) {
                dx *= -1;
                x += dx;
                dy *= -1;
                y += dy;
            }
        }*/
    }

    public void moveLeft() {
        x -= speed;
        getHitBox().setPosition(x, y);
    }

    public void moveRight() {
        x += speed;
        getHitBox().setPosition(x, y);
    }

    public void moveUp() {
        y -= speed;
        getHitBox().setPosition(x, y);
    }

    public void moveDown() {
        y += speed;
        getHitBox().setPosition(x, y);
    }

    public void performMove() {
        setPosition.accept(x, y);
        this.next();
    }

}
