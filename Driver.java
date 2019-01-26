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

    public static ArrayList<Entity> entitys = new ArrayList<>();
    public static ArrayList<Entity> backgrounds = new ArrayList<>();
    public static ArrayList<EnemyPlayer> enemies = new ArrayList<>();
    public static ArrayList<Pickup> pickups = new ArrayList<>();

//public static ArrayList<Bullet> bullets = new ArrayList<>();

    static int SCREEN_WIDTH = 1024;
    static int SCREEN_HEIGHT = 960;
    private static String Title = "Test Arena";
    private static String Message = "testing";
    private BackgroundSprite background = new BackgroundSprite("art/background_big.jpg");

    public EnemyPlayer enemy = new EnemyPlayer();

    RenderWindow window = new RenderWindow();

    public Pickup.pickUpType bomb = Pickup.pickUpType.bomb;
    public Pickup pick1 = new Pickup(300, 300, 10, 10, bomb, "art/Bomb.png");


    public void run() {

        window.create(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT),
                Title,
                WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

        Driver.entitys.add(background);
        Driver.entitys.add(Player.getPlayerInstance());
        Driver.pickups.add(pick1);


        Driver.enemies.add(enemy);


        window.display();
        window.clear();

        while (window.isOpen()) {

            //update display with any changes


            handleMovementInput();


            handleCombatInput();

            //if no combat keys are pressed, load the chamber (currently allows for semi auto fire only)
            if (!combatKeysPressed()) {
                Player.getPlayerInstance().loadChamber();
            }

            //if no movement keys pressed, player standing still
            if (!movementKeysPressed()) {
                Player.getPlayerInstance().standingStill();
            }

            //draw entities, will need to be in own method as more entities are added
            for (Entity entity : new ArrayList<>(entitys)) {
                entity.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, entity.x, entity.y);
                entity.performMove();
                entity.draw(window);
            }

            //draw enemies
            for (EnemyPlayer enemy : new ArrayList<>(enemies)) {
                enemy.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, enemy.x, enemy.y);
                enemy.performMove();
                enemy.draw(window);
                enemy.moveEnemy(Player.getPlayerInstance().x, Player.getPlayerInstance().y);
            }

            for (Pickup pickup : new ArrayList<>(pickups)) {
                pickup.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, pickup.getPickupx(), pickup.getPickupy());
                pickup.performMove();
                pickup.performMove();
                pickup.draw(window);
            }

            //get all fired bullet instances, loop through and draw them
            for (Bullet bullet : Player.getPlayerInstance().getFiredBullets()) {
                bullet.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, bullet.getBulletx(), bullet.getBullety());
                bullet.performMove();
                bullet.moveBullet();
                bullet.draw(window);
            }

            for (Event event : window.pollEvents()) {
                if (event.type == Event.Type.CLOSED) {
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
    public void handleCombatInput() {
        if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
            Player.getPlayerInstance().shootBulletLeft();

        }
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            Player.getPlayerInstance().shootBulletUp();

        }
        if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            Player.getPlayerInstance().shootBulletDown();

        }
        if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
            Player.getPlayerInstance().shootBulletRight();

        }
    }


    //check to see if any movement keys are pressed
    public boolean movementKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.UP)
                || Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.DOWN));

    }

    //check if any combat keys are being pressed
    public boolean combatKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.A) || Keyboard.isKeyPressed(Keyboard.Key.W)
                || Keyboard.isKeyPressed(Keyboard.Key.S) || Keyboard.isKeyPressed(Keyboard.Key.D));
    }


    public static void main(String[] args) {
        Driver d = new Driver();
        d.run();
    }


}
