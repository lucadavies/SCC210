import java.util.*;

import java.lang.Object;

import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;


public abstract class Entity {

    Drawable obj;
    IntConsumer rotate;
    BiConsumer<Float, Float> setPosition;
    private ArrayList<Entity> actors = new ArrayList<>();

    float x = 0; //current x-coordinate
    float y = 0; //current y-coordinate
    float w = 0;
    float h = 0;


    int r = 0;    // Change in rotation per cycle
    float dx = 5;    // Change in X-coordinate per cycle
    int dy = 5;    // Change in Y-coordinate per cycle


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
        // check we've not hit screen bounds
        //
        //if(x <= minx || x >= maxx) { dx *= -1; x += dx;}
        //if(x <= miny || x >= maxy) { dy *= -1; y += dy;}

        //
        // check we've not collided with any other actor
        //
        for (Entity a : actors) {
            if (a.obj != obj && a.within(x, y)) {
                dx *= -1;
                x += dx;
                dy *= -1;
                y += dy;
            }
        }
    }


    boolean within(float x, float y) {
        if (this.x == x && this.y == y) {
            return true;
        }
        return false;
    }

    void performMove() {
        rotate.accept(r);
        setPosition.accept(x, y);
    }

    //
    //Render the object at its new position
    //
    void draw(RenderWindow w) {
        w.draw(obj);
    }


}
