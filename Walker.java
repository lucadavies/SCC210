import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

public class Walker extends Alien{

    public static final int WALKER_X = 500;
    public static final int WALKER_Y = 500;

    public CollisionBox walkerHitBox = new CollisionBox(WALKER_X, WALKER_Y, 60, 60);

    public Walker(){
      super(WALKER_X,WALKER_Y,"art/enemy/Walker.png");
      super.moveLeft();
      super.moveAwayFromCamera();
      super.moveRight();
      super.moveTowardCamera();
      super.setSpriteWithinSheet(1, 1);
      walkerHitBox.setPosition(WALKER_X,WALKER_Y);
      //super.moveEnemy();


    }
  }
