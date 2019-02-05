import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.system.Clock;

import java.util.ArrayList;


public class Driver {

    public static ArrayList<Entity> entities = new ArrayList<>();
    public static ArrayList<Entity> backgrounds = new ArrayList<>();
    public static ArrayList<Alien> enemies = new ArrayList<>();
    public static ArrayList<Pickup> pickups = new ArrayList<>();
    public static ArrayList<Walker> walkers = new ArrayList<>();
    public static ArrayList<Runner> runners = new ArrayList<>();

//public static ArrayList<Bullet> bullets = new ArrayList<>();

    static int SCREEN_WIDTH = 1020;
    static int SCREEN_HEIGHT = 1020;
    private static String Title = "Test Arena";
    private static String Message = "testing";
    private Map level = new Map(Map.mapType.SHIP);
    private float walkerSpeed = 2;
    private float runnerSpeed = 4;

    private Alien enemy = new Alien(350,350,"art/enemy_player.png");
    private Walker walker = new Walker();
    private Runner runner = new Runner();

    public boolean[][] tileCollisions = new boolean[17][17];
    public String[][] tileCollisionDirection = new String[17][17];

    RenderWindow window;

    private Pickup Bomb = new Pickup(300, 300, 40, 40, Pickup.pickUpType.bomb);
    private Pickup superLaserGun = new Pickup(300, 400, 40, 40, Pickup.pickUpType.superLaserGun);
    private Pickup vaccumCleaner = new Pickup(100, 100, 40, 40, Pickup.pickUpType.vaccumCleaner);
    private Pickup alienMess2 = new Pickup(600, 45, 40, 40, Pickup.pickUpType.alienMess);
    private Pickup Boots = new Pickup(200, 600, 40, 40, Pickup.pickUpType.boots);
    private Pickup Boots2 = new Pickup(200, 100, 40, 40, Pickup.pickUpType.boots);
    private Pickup alienMess = new Pickup(600, 450, 40, 40, Pickup.pickUpType.alienMess);
    private Pickup allDirectionShooting = new Pickup(850,100,40,40,Pickup.pickUpType.allDirectionsCapsule);
    private Pickup frozenStone = new Pickup(850,700,40,40,Pickup.pickUpType.frozen);
    private Pickup frozenStone2 = new Pickup(150,150,40,40, Pickup.pickUpType.frozen);

    //Timers for the pickups.
    private Clock superLaserGunClock = new Clock();
    private Clock speedClock = new Clock();
    private Clock allDirectionsCapsuleClock = new Clock();
    private Clock frozenStoneClock = new Clock();

    public Driver(RenderWindow w) {
        window = w;
    }

    public void run() {

        entities.add(Player.getPlayerInstance());
        //pickups.add(Bomb);
        pickups.add(superLaserGun);
        pickups.add(alienMess2);
        //pickups.add(vaccumCleaner);
        pickups.add(Boots2);
        pickups.add(allDirectionShooting);
        pickups.add(Boots);
        //pickups.add(alienMess);
        pickups.add(frozenStone);
        pickups.add(frozenStone2);

        //walkers.add(walker);
        //runners.add(runner);


        window.display();
        window.clear();

        while (window.isOpen()) {

            //redraw Map
            level.draw(window);
            //update display with any changes
            handleMovementInput();
            //System.out.println(""+clock.getElapsedTime().asSeconds());
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
            /*
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    if (tiles[i][j].getHitbox().entityCollisionCheck(tiles[i][j].getHitbox().getRectBox(),
                            Player.getPlayerInstance().getHitBox().getRectBox()) && !tiles[i][j].getWalkThrough()) {
                        Player.getPlayerInstance().setCollided(true);

                        tileCollisions[i][j] = true;

                        System.out.println("tile: [" + i + "][" + j + "]  collided:" + tileCollisions[i][j]);

                        if (Player.getPlayerInstance().getLastMove().equals("up")) {
                            tileCollisionDirection[i][j] = "up";
                            Player.getPlayerInstance().canMoveUp = false;

                            Player.getPlayerInstance().canMoveDown = true;
                            Player.getPlayerInstance().canMoveLeft = true;
                            Player.getPlayerInstance().canMoveRight = true;
                        }
                        if (Player.getPlayerInstance().getLastMove().equals("down")) {
                            tileCollisionDirection[i][j] = "down";
                            Player.getPlayerInstance().canMoveDown = false;

                            Player.getPlayerInstance().canMoveUp = true;
                            Player.getPlayerInstance().canMoveLeft = true;
                            Player.getPlayerInstance().canMoveRight = true;
                        }
                        if (Player.getPlayerInstance().getLastMove().equals("left")) {
                            tileCollisionDirection[i][j] = "left";
                            Player.getPlayerInstance().canMoveLeft = false;

                            Player.getPlayerInstance().canMoveUp = true;
                            Player.getPlayerInstance().canMoveDown = true;
                            Player.getPlayerInstance().canMoveRight = true;
                        }
                        if (Player.getPlayerInstance().getLastMove().equals("right")) {
                            tileCollisionDirection[i][j] = "right";
                            Player.getPlayerInstance().canMoveRight = false;

                            Player.getPlayerInstance().canMoveUp = true;
                            Player.getPlayerInstance().canMoveDown = true;
                            Player.getPlayerInstance().canMoveLeft = true;
                        }

                    } else if (!tiles[i][j].getHitbox().entityCollisionCheck(tiles[i][j].getHitbox().getRectBox(),
                            Player.getPlayerInstance().getHitBox().getRectBox()) && !tiles[i][j].getWalkThrough()) {
                        Player.getPlayerInstance().setCollided(false);
                        //tileCollisions[i][j] = false;
                        //System.out.println("tile: " + i + "/" + j + "  collided:" + tileCollisions[i][j]);
                    }
                }
            }


            //this is the loop that re-checks the tiles that were previously collided with,
            //if the player is no longer colliding with the tile that movement direction is allowed.
            //
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    if (tileCollisions[i][j]) {
                        if (!tiles[i][j].getHitbox().entityCollisionCheck(tiles[i][j].getHitbox().getRectBox(),
                                Player.getPlayerInstance().getHitBox().getRectBox()) && !tiles[i][j].getWalkThrough()) {
                            tileCollisions[i][j] = false;

                            if (tileCollisionDirection[i][j].equals("up")) {
                                Player.getPlayerInstance().canMoveUp = true;
                            }
                            if (tileCollisionDirection[i][j].equals("down")) {
                                Player.getPlayerInstance().canMoveDown = true;
                            }
                            if (tileCollisionDirection[i][j].equals("left")) {
                                Player.getPlayerInstance().canMoveLeft = true;
                            }
                            if (tileCollisionDirection[i][j].equals("right")) {
                                Player.getPlayerInstance().canMoveRight = true;
                            }
                        }
                    }
                }
            }*/

            //draw enemies
            for (Alien enemy : new ArrayList<>(enemies)) {
                enemy.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, enemy.x, enemy.y);
                enemy.performMove();
                enemy.draw(window);
              if(!Player.getPlayerInstance().getFrozenStone()){
                enemy.moveEnemy(Player.getPlayerInstance().x, Player.getPlayerInstance().y,walkerSpeed,walkerSpeed+1);
              }
            }

            for(Walker walker : new ArrayList<>(walkers)){
                walker.calcMove(0,0,SCREEN_WIDTH, SCREEN_HEIGHT,walker.x,walker.y);
                walker.performMove();
                walker.draw(window);
              if(!Player.getPlayerInstance().getFrozenStone()){
                walker.moveEnemy(Player.getPlayerInstance().x,Player.getPlayerInstance().y,walkerSpeed,walkerSpeed+1);
              }
            }

            for(Runner runner : new ArrayList<>(runners)){
                runner.calcMove(0,0,SCREEN_WIDTH, SCREEN_HEIGHT,runner.x,runner.y);
                runner.performMove();
                runner.draw(window);
              if(!Player.getPlayerInstance().getFrozenStone()){
                runner.moveEnemy(Player.getPlayerInstance().x,Player.getPlayerInstance().y,runnerSpeed,runnerSpeed+1);
              }
            }

            //loops through every pickup
            //
            //you can check if a pickup has been already been picked up by 'pickup.hasPickedUp()'
            for (Pickup pickup : new ArrayList<>(pickups)) {
                pickup.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, pickup.getPickupx(), pickup.getPickupy());
                pickup.performMove();
                pickup.performMove();

                //if there is no collision it draws the pickup, if theres collision it doesn't
                if (!pickup.getHitBox().entityColliding(Player.getPlayerInstance().getHitBox().getRectBox()) && !pickup.hasPickedUp()) {
                    pickup.draw(window);
                }
                if(pickup.getHitBox().entityColliding(Player.getPlayerInstance().getHitBox().getRectBox())) {
                    pickup.setPickedUp();
                    pickup.setPosition(-10, -10);
                    switch(pickup.getPickup()){
                                      case alienMess:
                                            speedClock.restart();
                                            Player.getPlayerInstance().setSpeedUpTrue();
                                            Player.getPlayerInstance().speedChange = 2;
                                            break;
                                      case boots:
                                            speedClock.restart();
                                            Player.getPlayerInstance().setSpeedUpTrue();
                                            Player.getPlayerInstance().speedChange = 13;
                                            break;
                                      case superLaserGun:
                                            superLaserGunClock.restart();
                                            Player.getPlayerInstance().setSuperLaserGunPickedUp();
                                            break;
                                      case allDirectionsCapsule:
                                            allDirectionsCapsuleClock.restart();
                                            Player.getPlayerInstance().setChamber(4);
                                            break;
                                      case frozen:
                                            frozenStoneClock.restart();
                                            Player.getPlayerInstance().setFrozenStone();
                                            break;
                }
              }
            }

            //If statements which how long pickups have been activated.
            if(superLaserGunClock.getElapsedTime().asSeconds()>7){
              Player.getPlayerInstance().setSuperLaserGunFalse();
            }

            if(speedClock.getElapsedTime().asSeconds()>7){
              Player.getPlayerInstance().setSpeedUpFalse();
            }

            if(allDirectionsCapsuleClock.getElapsedTime().asSeconds()>7){
              Player.getPlayerInstance().setChamber(1);
            }

            if(frozenStoneClock.getElapsedTime().asSeconds()>7){
              Player.getPlayerInstance().setFrozenStoneFalse();
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
            boolean moved = false;
            for (int i = 0; i < 17 && !moved; i++) {
                for (int j = 0; j < 17 && !moved; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(Player.getPlayerInstance().getHitBox().getRectBox())) { //if player is in this tile
                        System.out.println("Player in: [" + i + "," + j + "]");
                        if (level.grid[i + (i == 16 ? 0 : 1)][j].getWalkThrough()) {
                            Player.getPlayerInstance().moveRight();
                            moved = true;
                        }
                        else {
                            System.out.println("Impassable tile to right");
                        }
                    }
                }
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.LEFT) && Player.getPlayerInstance().canMoveLeft) {
            boolean moved = false;
            for (int i = 0; i < 17 && !moved; i++) {
                for (int j = 0; j < 17 && !moved; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(Player.getPlayerInstance().getHitBox().getRectBox())) { //if player is in this tile
                        System.out.println("Player in: [" + i + "," + j + "]");
                        if (level.grid[i + (i == 0 ? 0 : -1)][j].getWalkThrough()) {
                            Player.getPlayerInstance().moveLeft();
                            moved = true;
                        }
                        else {
                            System.out.println("Impassable tile to left");
                        }
                    }
                }
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.UP) && Player.getPlayerInstance().canMoveUp) {
            boolean moved = false;
            for (int i = 0; i < 17 && !moved; i++) {
                for (int j = 0; j < 17 && !moved; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(Player.getPlayerInstance().getHitBox().getRectBox())) { //if player is in this tile
                        System.out.println("Player in: [" + i + "," + j + "]");
                        if (level.grid[i][j + (j == 0 ? 0 : -1)].getWalkThrough()) {
                            Player.getPlayerInstance().moveUp();
                            moved = true;
                        }
                        else {
                            System.out.println("Impassable tile above");
                        }
                    }
                }
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.DOWN) && Player.getPlayerInstance().canMoveDown) {
            boolean moved = false;
            for (int i = 0; i < 17 && !moved; i++) {
                for (int j = 0; j < 17 && !moved; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(Player.getPlayerInstance().getHitBox().getRectBox())) { //if player is in this tile
                        System.out.println("Player in: [" + i + "," + j + "]");
                        if (level.grid[i][j + (j == 16 ? 0 : 1)].getWalkThrough()) {
                            Player.getPlayerInstance().moveDown();
                            moved = true;
                        }
                        else {
                            System.out.println("Impassable tile to right");
                        }
                    }
                }
            }
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


    public static void main(String[] args) {
        RenderWindow win = new RenderWindow(new VideoMode(SCREEN_WIDTH, SCREEN_HEIGHT), "test", WindowStyle.DEFAULT);
        win.setFramerateLimit(30);
        Driver d = new Driver(win);
        d.run();
    }


}
