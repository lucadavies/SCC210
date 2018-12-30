import java.util.*;
import javafx.geometry.BoundingBox;
import java.lang.Object;
import javafx.geometry.Bounds;
import org.jsfml.audio.Sound;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.*;
import org.jsfml.graphics.RectangleShape;

/*
*Class for the user controlled player
*/
public class Player extends Character {

  public static final float PLAYER_WIDTH = 80;
  public static final float PLAYER_HEIGHT = 80;
  public static final float PLAYER_SPEED = 5;


  //some bools which could be useful after introducing pickups/abilities
  public boolean isSolid;
  public boolean isInvincible;
  public boolean isDead;

  private static Player player = new Player(); //creating the singleton instance


  /*
  *Class for the user controlled player.
  *
  *This is a singleton class (private constructor)
  *to ensure that only one instance is created.
  */
  private Player() {
        super(0, 0, "art/player_spriteSheet.png", PLAYER_WIDTH, PLAYER_HEIGHT);
        super.setSpriteWithinSheet(1, 1);

    }


  //method to get the single instance of the player
  public static Player getPlayerInstance(){
    return player;
  }

  @Override
  public void moveLeft(){
    super.setSpriteWithinSheet(0, 2);
    x-=PLAYER_SPEED;
  }

  @Override
  public void moveAwayFromCamera(){
    super.setSpriteWithinSheet(0, 2);
    y-=PLAYER_SPEED;
    x=x;
  }
  @Override
  public void moveRight(){
    super.setSpriteWithinSheet(0, 2);
    x+=PLAYER_SPEED;
  }


  @Override
  public void moveTowardCamera(){
    super.setSpriteWithinSheet(0, 2);
    y+=PLAYER_SPEED;
    x=x;
  }

  public void standingStill(){
    super.setSpriteWithinSheet(1,1);
  }

  public boolean isSolid(){
    return isSolid;
  }

  public boolean isInvincible(){
    return isInvincible;
  }

  public boolean isDead(){
    return isDead;
  }


}
