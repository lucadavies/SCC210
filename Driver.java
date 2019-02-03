import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

import java.util.ArrayList;


public class Driver {

    public static ArrayList<Entity> entities = new ArrayList<>();
    public static ArrayList<Entity> backgrounds = new ArrayList<>();
    public static ArrayList<Alien> enemies = new ArrayList<>();
    public static ArrayList<Pickup> pickups = new ArrayList<>();

    public OutOfBounds outOfBounds = new OutOfBounds();


//public static ArrayList<Bullet> bullets = new ArrayList<>();

    static int SCREEN_WIDTH = 1020;
    static int SCREEN_HEIGHT = 1020;
    private static String Title = "Test Arena";
    private static String Message = "testing";
    private Map level = new Map(Map.mapType.PLANET);

    private Alien enemy = new Alien();

    public boolean[][] tileCollisions = new boolean[17][17];
    public String[][] tileCollisionDirection = new String[17][17];

    RenderWindow window = new RenderWindow();

    private Pickup Bomb = new Pickup(300, 300, 40, 40, Pickup.pickUpType.bomb);
    private Pickup superLaserGun = new Pickup(300, 400, 40, 40, Pickup.pickUpType.superLaserGun);
    private Pickup vaccumCleaner = new Pickup(100, 100, 40, 40, Pickup.pickUpType.vaccumCleaner);
    private Pickup alienMess2 = new Pickup(600, 45, 40, 40, Pickup.pickUpType.alienMess);
    private Pickup Boots = new Pickup(200, 600, 40, 40, Pickup.pickUpType.boots);
    private Pickup Boots2 = new Pickup(200, 100, 40, 40, Pickup.pickUpType.boots);
    private Pickup alienMess = new Pickup(600, 450, 40, 40, Pickup.pickUpType.alienMess);
    private Pickup allDirectionShooting = new Pickup(850,100,40,40,Pickup.pickUpType.allDirectionsCapsule);

    public Driver(RenderWindow w) {
        window = w;
    }

    public void run() {

        /*window.create(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT),
                Title,
                WindowStyle.DEFAULT);
        window.setFramerateLimit(30);*/

        //entities.add(background);
        entities.add(Player.getPlayerInstance());
        pickups.add(Bomb);
        pickups.add(superLaserGun);
        pickups.add(alienMess2);
        pickups.add(vaccumCleaner);
        pickups.add(Boots2);
        pickups.add(allDirectionShooting);


        enemies.add(enemy);


        window.display();
        window.clear();

        while (window.isOpen()) {

            //redraw Map
            level.draw(window);

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
            for (Entity entity : new ArrayList<>(entities)) {
                entity.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, entity.x, entity.y);
                entity.performMove();
                entity.draw(window);
            }

            //holds the array of tiles
            Tile[][] tiles = level.getTiles();

            //initialise the out of bounds instance
            for(int i=0;i<17;i++){
              for(int j=0;j<17;j++){
                if(!tiles[i][j].getWalkThrough()){
                  outOfBounds.accept((int)tiles[i][j].getX(),(int)tiles[i][j].getY(), (int)tiles[i][j].getWidth(),(int)tiles[i][j].getHeight());
                }
              }
            }

            //check ahead to see whether the player will be out of bounds
            //If player will be out of bounds then movement in that direction will be blocked
            outOfBounds.isOutOfBounds((int)Player.getPlayerInstance().x,(int)Player.getPlayerInstance().y,
                (int)Player.getPlayerInstance().PLAYER_WIDTH,(int)Player.getPlayerInstance().PLAYER_HEIGHT,6);





            System.out.println("collided? - " + Player.getPlayerInstance().getCollided());

            //draw enemies
            for (Alien enemy : new ArrayList<>(enemies)) {
                enemy.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, enemy.x, enemy.y);
                enemy.performMove();
                enemy.draw(window);
                enemy.moveEnemy(Player.getPlayerInstance().x, Player.getPlayerInstance().y);
            }


            //loops through every pickup
            //
            //you can check if a pickup has been already been picked up by 'pickup.hasPickedUp()'
            for (Pickup pickup : new ArrayList<>(pickups)) {
                pickup.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, pickup.getPickupx(), pickup.getPickupy());
                pickup.performMove();
                pickup.performMove();

                //if there is no collision it draws the pickup, if theres collision it doesn't
                if (!pickup.getHitBox().entityCollisionCheck(Player.getPlayerInstance().getHitBox().getRectBox(),
                        pickup.getHitBox().getRectBox()) && !pickup.hasPickedUp()) {
                    pickup.draw(window);
                } else {
                    pickup.setPickedUp();
                    pickup.setPosition(-10, -10);

                    switch(pickup.getPickup()){
                                      case alienMess:
                                            Player.getPlayerInstance().setSpeedUpTrue();
                                            Player.getPlayerInstance().speedChange = 2;
                                            System.out.println(""+Player.getPlayerInstance().getSpeedChange());
                                            break;
                                      case boots:
                                            Player.getPlayerInstance().setSpeedUpTrue();
                                            Player.getPlayerInstance().speedChange = 10;
                                            System.out.println(""+Player.getPlayerInstance().getSpeedChange());
                                            break;
                                      case superLaserGun:
                                            Player.getPlayerInstance().setSuperLaserGunPickedUp();
                                            break;
                                      case allDirectionsCapsule:
                                            Player.getPlayerInstance().setChamberTo4();
                                            break;
                    }
                }
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


        if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) && Player.getPlayerInstance().canMoveRight) {
            Player.getPlayerInstance().moveRight();
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.LEFT) && Player.getPlayerInstance().canMoveLeft) {
            Player.getPlayerInstance().moveLeft();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.UP) && Player.getPlayerInstance().canMoveUp) {
            Player.getPlayerInstance().moveAwayFromCamera();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.DOWN) && Player.getPlayerInstance().canMoveDown) {
            Player.getPlayerInstance().moveTowardCamera();
        }
    }

    //handle the combat input keys
    public void handleCombatInput() {
      if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
            Player.getPlayerInstance().shootBulletLeft();
            Player.getPlayerInstance().shootBulletUp();
            Player.getPlayerInstance().shootBulletDown();
            Player.getPlayerInstance().shootBulletRight();
      }
      if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
          Player.getPlayerInstance().shootBulletUp();
          Player.getPlayerInstance().shootBulletDown();
          Player.getPlayerInstance().shootBulletRight();
          Player.getPlayerInstance().shootBulletLeft();
      }
      if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
          Player.getPlayerInstance().shootBulletDown();
          Player.getPlayerInstance().shootBulletLeft();
          Player.getPlayerInstance().shootBulletRight();
          Player.getPlayerInstance().shootBulletUp();
      }
      if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
          Player.getPlayerInstance().shootBulletRight();
          Player.getPlayerInstance().shootBulletDown();
          Player.getPlayerInstance().shootBulletLeft();
          Player.getPlayerInstance().shootBulletUp();
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

}
