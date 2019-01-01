import org.jsfml.audio.Music;
import org.jsfml.audio.Sound;
import org.jsfml.audio.SoundBuffer;
import org.jsfml.audio.SoundSource;
import org.jsfml.graphics.*;
import org.jsfml.graphics.Image;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Mouse;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.awt.Dimension;


public class Driver {

public static ArrayList<Actor> actors = new ArrayList<>();
public static ArrayList<Actor> backgrounds = new ArrayList<>();
//public static ArrayList<Bullet> bullets = new ArrayList<>();

static int SCREEN_WIDTH = 512;
static int SCREEN_HEIGHT = 480;
private static String Title = "Test Arena";
private static String Message = "testing";
private BackgroundSprite background = new BackgroundSprite("art/background.jpg");
RenderWindow window = new RenderWindow();


public void run() {

  window.create(new VideoMode(SCREEN_WIDTH,SCREEN_HEIGHT),
                  Title,
                  WindowStyle.DEFAULT);
  window.setFramerateLimit(30);

  Driver.actors.add(background);
  Driver.actors.add(Player.getPlayerInstance());

  window.display();
  window.clear();

  while(window.isOpen()){

     //update display with any changes
    handleMovementInput();

    handleCombatInput();

    //if no combat keys are pressed, load the chamber (currently allows for semi auto fire only)
    if(!combatKeysPressed()){
      Player.getPlayerInstance().loadChamber();
    }

    //if no movement keys pressed, player standing still
    if(!movementKeysPressed()){
      Player.getPlayerInstance().standingStill();
    }

    //draw actors, will need to be in own method as more actors are added
    for(Actor actor : new ArrayList<>(actors)){
      actor.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, actor.x, actor.y);
      actor.performMove();
      actor.draw(window);
    }


    //get all fired bullet instances, loop through and draw them
    for(Bullet bullet : Player.getPlayerInstance().getFiredBullets()){
      bullet.calcMove(0,0,SCREEN_WIDTH,SCREEN_HEIGHT, bullet.getBulletx(), bullet.getBullety());
      bullet.performMove();
      bullet.moveBulletLeft();
      bullet.draw(window);
    }

    for(Event event : window.pollEvents()){
      if(event.type == Event.Type.CLOSED){
        //user pressed close button
        window.close();
      }
    }

    window.display();
    window.clear();
  }
}

public void handleMovementInput() {
        if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
            Player.getPlayerInstance().moveRight();
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
            Player.getPlayerInstance().moveLeft();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
            Player.getPlayerInstance().moveAwayFromCamera();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            Player.getPlayerInstance().moveTowardCamera();
        }
    }

//handle the combat input keys
public void handleCombatInput(){
  if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
      Player.getPlayerInstance().shootBullet();

  }
  if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
      Player.getPlayerInstance().shootBullet();

  }
  if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
      Player.getPlayerInstance().shootBullet();

  }
  if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
      Player.getPlayerInstance().shootBullet();

  }
}


//check to see if any movement keys are pressed
public boolean movementKeysPressed() {
  return (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.UP)
              || Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.DOWN));

    }

//check if any combat keys are being pressed
public boolean combatKeysPressed(){
  return (Keyboard.isKeyPressed(Keyboard.Key.A) || Keyboard.isKeyPressed(Keyboard.Key.W)
              || Keyboard.isKeyPressed(Keyboard.Key.S) || Keyboard.isKeyPressed(Keyboard.Key.D));
}


public static void main (String []args) {
        Driver d = new Driver();
        d.run();
    }


}
