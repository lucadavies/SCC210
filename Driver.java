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


//public static ArrayList<Bullet> bullets = new ArrayList<>();

    static int SCREEN_WIDTH = 1020;
    static int SCREEN_HEIGHT = 1020;
    private static String Title = "Test Arena";
    private static String Message = "testing";
    private BackgroundSprite background = new BackgroundSprite("art/background_big.jpg");
    private Map level = new Map(Map.mapType.FARM);

    private Alien enemy = new Alien();

    public boolean[][] tileCollisions = new boolean[17][17];
    public String[][] tileCollisionDirection = new String[17][17];

    RenderWindow window = new RenderWindow();

    private Pickup Bomb = new Pickup(300, 300, 40, 40, Pickup.pickUpType.bomb);
    private Pickup Boots = new Pickup(300,500,40,40,Pickup.pickUpType.boots);
    private Pickup superLaserGun = new Pickup(300,400,40,40,Pickup.pickUpType.superLaserGun);

    public void run() {

        window.create(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT),
                Title,
                WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

        //entities.add(background);
        entities.add(Player.getPlayerInstance());
        pickups.add(Bomb);
        pickups.add(Boots);
        pickups.add(superLaserGun);


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


            //Loops through the tile array, first checks whether player is colliding with any non-walkthroughables,
            //then allows the player to move in all other directions apart from the collision direction.
            //Then checks whether player is still colliding with the previous tile, if not the player can now move in that direction.
            //
            for(int i=0;i<17;i++){
              for(int j=0;j<17;j++){

                boolean t = true;
                boolean f = false;
                if(tiles[i][j].getHitbox().entityCollisionCheck(tiles[i][j].getHitbox().getRectBox(),
                  Player.getPlayerInstance().getHitBox().getRectBox()) && !tiles[i][j].getWalkThrough()){
                    Player.getPlayerInstance().setCollided(t);

                    tileCollisions[i][j] = true;

                    System.out.println("tile: " + i + "/" + j + "  collided:" + tileCollisions[i][j]);

                    if(Player.getPlayerInstance().getLastMove().equals("up")){
                      tileCollisionDirection[i][j] = "up";
                      Player.getPlayerInstance().canMoveUp = false;

                      Player.getPlayerInstance().canMoveDown = true;
                      Player.getPlayerInstance().canMoveLeft = true;
                      Player.getPlayerInstance().canMoveRight = true;
                    }
                    if(Player.getPlayerInstance().getLastMove().equals("down")){
                      tileCollisionDirection[i][j] = "down";
                      Player.getPlayerInstance().canMoveDown = false;

                      Player.getPlayerInstance().canMoveUp = true;
                      Player.getPlayerInstance().canMoveLeft = true;
                      Player.getPlayerInstance().canMoveRight = true;
                    }
                    if(Player.getPlayerInstance().getLastMove().equals("left")){
                      tileCollisionDirection[i][j] = "left";
                      Player.getPlayerInstance().canMoveLeft = false;

                      Player.getPlayerInstance().canMoveUp = true;
                      Player.getPlayerInstance().canMoveDown = true;
                      Player.getPlayerInstance().canMoveRight = true;
                    }
                    if(Player.getPlayerInstance().getLastMove().equals("right")){
                      tileCollisionDirection[i][j] = "right";
                      Player.getPlayerInstance().canMoveRight = false;

                      Player.getPlayerInstance().canMoveUp = true;
                      Player.getPlayerInstance().canMoveDown = true;
                      Player.getPlayerInstance().canMoveLeft = true;
                    }

                  }else if(!tiles[i][j].getHitbox().entityCollisionCheck(tiles[i][j].getHitbox().getRectBox(),
                    Player.getPlayerInstance().getHitBox().getRectBox()) && !tiles[i][j].getWalkThrough()){
                    Player.getPlayerInstance().setCollided(f);
                    //tileCollisions[i][j] = false;
                    //System.out.println("tile: " + i + "/" + j + "  collided:" + tileCollisions[i][j]);
                  }
              }
            }


            //this is the loop that re-checks the tiles that were previously collided with,
            //if the player is no longer colliding with the tile that movement direction is allowed.
            //
            for(int i=0;i<17;i++){
              for(int j=0;j<17;j++){
                if(tileCollisions[i][j]){
                  if(!tiles[i][j].getHitbox().entityCollisionCheck(tiles[i][j].getHitbox().getRectBox(),
                    Player.getPlayerInstance().getHitBox().getRectBox()) && !tiles[i][j].getWalkThrough()){
                      tileCollisions[i][j] = false;

                      if(tileCollisionDirection[i][j].equals("up")){
                        Player.getPlayerInstance().canMoveUp = true;
                      }
                      if(tileCollisionDirection[i][j].equals("down")){
                        Player.getPlayerInstance().canMoveDown = true;
                      }
                      if(tileCollisionDirection[i][j].equals("left")){
                        Player.getPlayerInstance().canMoveLeft = true;
                      }
                      if(tileCollisionDirection[i][j].equals("right")){
                        Player.getPlayerInstance().canMoveRight = true;
                      }
                    }
                }
              }
            }

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
                if(!pickup.getHitBox().entityCollisionCheck(Player.getPlayerInstance().getHitBox().getRectBox(),
                        pickup.getHitBox().getRectBox()) && !pickup.hasPickedUp()){
                  pickup.draw(window);
                }else{
                  pickup.setPickedUp();
                  pickup.setPosition(-10,-10);
                  //if pickup is boots, boolean true so that speed increases.
                  if(pickup.getPickup()==Pickup.pickUpType.boots){
                      Player.getPlayerInstance().setBootsPickedUp();
                  }
                  if(pickup.getPickup()==Pickup.pickUpType.superLaserGun){
                    Player.getPlayerInstance().setSuperLaserGunPickedUp();

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
