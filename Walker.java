import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

public class Walker extends Alien{

    public Walker(){
      super(500,500,"art/enemy/Walker.png");
      super.moveLeft();
      super.moveAwayFromCamera();
      super.moveRight();
      super.moveTowardCamera();
      super.setSpriteWithinSheet(1, 1);
      //super.moveEnemy();


    }
  }
