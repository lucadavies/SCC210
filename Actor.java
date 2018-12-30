import java.util.*;
import javafx.geometry.BoundingBox;
import java.lang.Object;
import javafx.geometry.Bounds;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;


public abstract class Actor {

  Drawable obj;
  IntConsumer rotate;
  BiConsumer<Float, Float> setPosition;
  private ArrayList<Actor> actors = new ArrayList<Actor>( );

  float x = 0; //current x-coordinate
  float y = 0; //current y-coordinate
  float w = 0;
  float h = 0;

  int r  = 0;	// Change in rotation per cycle
  float dx = 5;	// Change in X-coordinate per cycle
  int dy = 5;	// Change in Y-coordinate per cycle



  boolean collisionWithin(BoundingBox box1, BoundingBox box2){
    //where some form of simple
    //bounding box collision could take place.

    if(box2.intersects(box1)) {
      return true;
    }
    return false;
  }

  //
  // work out where object should be for next frame
  //
  void calcMove(int minx, int miny, int maxx, int maxy, float xPos, float yPos){
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
    for(Actor a : actors) {
      if(a.obj != obj && a.within(x,y)) {
            dx *= -1; x += dx;
            dy *= -1; y += dy;
      }
    }
  }


  //simple bounding box collision method
  boolean within (BoundingBox bb1, BoundingBox bb2) {
        if(bb2.intersects(bb1)) {
            return true;
        }
        return false;
    }

    boolean within (float x, float y) {
        if(this.x == x && this.y == y) {
            return true;
        }
        return false;
    }

    void performMove( ) {
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
