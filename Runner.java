import java.util.*;

import javafx.geometry.BoundingBox;

import java.lang.Object;

import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

public class Runner extends Alien{

    public static final int RUNNER_X = 700;
    public static final int RUNNER_Y = 700;

    public CollisionBox runnerHitBox = new CollisionBox(RUNNER_X, RUNNER_Y, 60, 60);

    public Runner(){
      super(RUNNER_X,RUNNER_Y,"art/enemy/Runner.png");
      super.moveLeft();
      super.moveAwayFromCamera();
      super.moveRight();
      super.moveTowardCamera();
      super.setSpriteWithinSheet(1, 1);
      runnerHitBox.setPosition(RUNNER_X,RUNNER_Y);
      //super.moveEnemy();


    }
  }
