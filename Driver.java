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
    private Map.mapType lvl1 = Map.mapType.FARM;
    private Map level = new Map(lvl1);
    private float walkerSpeed = 2;
    private float runnerSpeed = 4;

    private Player player = Player.getPlayerInstance();
    private Alien enemy = new Alien(350, 350, "art/enemy_player.png");
    private Walker walker = new Walker();
    private Runner runner = new Runner();

    private RenderWindow window;

    private Pickup Bomb = new Pickup(300, 300, 40, 40, Pickup.pickUpType.BOMB);
    private Pickup superLaserGun = new Pickup(300, 400, 40, 40, Pickup.pickUpType.SUPER_LASER_GUN);
    private Pickup vaccumCleaner = new Pickup(100, 100, 40, 40, Pickup.pickUpType.NUKE);
    private Pickup alienMess2 = new Pickup(600, 45, 40, 40, Pickup.pickUpType.ALIEN_MESS);
    private Pickup Boots = new Pickup(200, 600, 40, 40, Pickup.pickUpType.BOOTS);
    private Pickup Boots2 = new Pickup(200, 100, 40, 40, Pickup.pickUpType.BOOTS);
    private Pickup alienMess = new Pickup(600, 450, 40, 40, Pickup.pickUpType.ALIEN_MESS);
    private Pickup allDirectionShooting = new Pickup(850, 100, 40, 40, Pickup.pickUpType.OMNI_SHOT);
    private Pickup frozenStone = new Pickup(850, 700, 40, 40, Pickup.pickUpType.FREEZE);
    private Pickup frozenStone2 = new Pickup(150, 150, 40, 40, Pickup.pickUpType.FREEZE);

    //Timers for the pickups.
    private Clock superLaserGunClock = new Clock();
    private Clock speedClock = new Clock();
    private Clock allDirectionsCapsuleClock = new Clock();
    private Clock frozenStoneClock = new Clock();

    public Driver(RenderWindow w) {
        window = w;
    }

    public void run() {

        entities.add(player);
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

            if (debugKeysPress()) {
                if (level.getType() == Map.mapType.TEST) {
                    level = new Map(lvl1);
                }
                else {
                    level = new Map(Map.mapType.TEST);
                }
            }
            //if no combat keys are pressed, load the chamber (currently allows for semi auto fire only)
            if (!combatKeysPressed()) {
                player.loadChamber();
            }

            //if no movement keys pressed, player standing still
            if (!movementKeysPressed()) {
                player.standingStill();
            }

            //draw entities, will need to be in own method as more entities are added
            for (Entity entity : entities) {
                if (entity instanceof MovingEntity) {
                    ((MovingEntity)entity).calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, entity.x, entity.y);
                    ((MovingEntity)entity).performMove();
                }
                entity.draw(window);
            }

            //draw enemies
            for (Alien enemy : enemies) {
                enemy.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, enemy.x, enemy.y);
                enemy.performMove();
                enemy.draw(window);
                if (!player.getFrozenStone()) {
                    enemy.moveEnemy(player.x, player.y, walkerSpeed, walkerSpeed + 1);
                }
            }

            for (Walker walker : walkers) {
                walker.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, walker.x, walker.y);
                walker.performMove();
                walker.draw(window);
                if (!player.getFrozenStone()) {
                    walker.moveEnemy(player.x, player.y, walkerSpeed, walkerSpeed + 1);
                }
            }

            for (Runner runner : runners) {
                runner.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, runner.x, runner.y);
                runner.performMove();
                runner.draw(window);
                if (!player.getFrozenStone()) {
                    runner.moveEnemy(player.x, player.y, runnerSpeed, runnerSpeed + 1);
                }
            }

            //loops through every pickup
            //
            //you can check if a pickup has been already been picked up by 'pickup.hasPickedUp()'
            for (Pickup pickup : pickups) {
                //pickup.calcMove(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, pickup.getPickupx(), pickup.getPickupy());
                //pickup.performMove();
                //pickup.performMove();

                //if there is no collision it draws the pickup, if theres collision it doesn't
                if (!pickup.getHitBox().entityColliding(player.getHitBox().getRectBox()) && !pickup.hasPickedUp()) {
                    pickup.draw(window);
                }
                if (pickup.getHitBox().entityColliding(player.getHitBox().getRectBox())) {
                    pickup.setPickedUp();
                    pickup.setPosition(-10, -10);
                    switch (pickup.getPickup()) {
                        case ALIEN_MESS:
                            speedClock.restart();
                            player.setSpeedUpTrue();
                            player.speedChange = 2;
                            break;
                        case BOOTS:
                            speedClock.restart();
                            player.setSpeedUpTrue();
                            player.speedChange = 13;
                            break;
                        case SUPER_LASER_GUN:
                            superLaserGunClock.restart();
                            player.setSuperLaserGunPickedUp();
                            break;
                        case OMNI_SHOT:
                            allDirectionsCapsuleClock.restart();
                            player.setChamber(4);
                            break;
                        case FREEZE:
                            frozenStoneClock.restart();
                            player.setFrozenStone();
                            break;
                    }
                }
            }

            //If statements which how long pickups have been activated.
            if (superLaserGunClock.getElapsedTime().asSeconds() > 7) {
                player.setSuperLaserGunFalse();
            }

            if (speedClock.getElapsedTime().asSeconds() > 7) {
                player.setSpeedUpFalse();
            }

            if (allDirectionsCapsuleClock.getElapsedTime().asSeconds() > 7) {
                player.setChamber(1);
            }

            if (frozenStoneClock.getElapsedTime().asSeconds() > 7) {
                player.setFrozenStoneFalse();
            }


            //get all fired bullet instances, loop through and draw them
            for (Bullet bullet : player.getFiredBullets()) {
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

        //Once player-occupied tiles are identified, all operations are performed based upon the bottom right-most tile occupied

        if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
            player.moveRight();  //make move to simulate NEXT position
            boolean blocked = false;
            int x = -1, y = -1;
            int n = 0;  //counts how many tiles player currently occupies (logically can be 1, 2 or 4)
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(player.getHitBox().getRectBox())) { //if player is in tile[i][j]
                        System.out.println("Player in: [" + i + "," + j + "]");
                        x = i;  //store right-most tile player occupies
                        y = j;  //store bottom-most tile player occupies
                        n++;
                    }
                }
            }
            System.out.println("n = " + n + "\nBottom right tile is [" + x + "," + y + "]" );
            if (!level.grid[x][y].canWalkThrough()) {  //bottom-right tile
                blocked = true;
                System.out.println("Right move blocked by [" + x +"][" + y + "]");
            }
            else if (!level.grid[x][y - (y > 0 ? 1 : 0)].canWalkThrough()) {  //top-right tile
                blocked = true;
                System.out.println("Right move blocked by [" + x +"][" + (y - 1) + "]");
            }
            else if (n == 2 && x == 16) {
                blocked = true;
            }
            if (blocked) { //if blocked or at edge of map, move not allowed, return to original position
                player.moveLeft();
            }
            System.out.println();
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
            player.moveLeft();   //make move to simulate NEXT position
            boolean blocked = false;
            int x = -1, y = -1;
            int n = 0;  //counts how many tiles player currently occupies (logically can be 1, 2 or 4)
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(player.getHitBox().getRectBox())) { //if player is in tile[i][j]
                        System.out.println("Player in: [" + i + "," + j + "]");
                        x = i;  //store right-most tile player occupies
                        y = j;  //store bottom-most tile player occupies
                        n++;
                    }
                }
            }
            System.out.println("Bottom right tile is [" + x + "," + y + "]" );
            if (!level.grid[x - (x > 0 ? 1 : 0)][y].canWalkThrough()) {  //bottom-left tile
                blocked = true;
                System.out.println("Left move blocked by [" + (x - 1) +"][" + y + "]");
            }
            else if (!level.grid[x - (x > 0 ? 1 : 0)][y - (y > 0 ? 1 : 0)].canWalkThrough()) {  //top-right tile
                blocked = true;
                System.out.println("Left move blocked by [" + (x - 1) +"][" + (y - 1) + "]");
            }
            else if (n == 2 && x == 0) {
                blocked = true;
            }
            if (blocked) { //if blocked or at edge of map, move not allowed, return to original position
                player.moveRight();
            }
            System.out.println();
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
            player.moveUp();    //make move to simulate NEXT position
            boolean blocked = false;
            int x = -1, y = -1;
            int n = 0;  //counts how many tiles player currently occupies (logically can be 1, 2 or 4)
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(player.getHitBox().getRectBox())) { //if player is in tile[i][j]
                        System.out.println("Player in: [" + i + "," + j + "]");
                        x = i;  //store right-most tile player occupies
                        y = j;  //store bottom-most tile player occupies
                        n++;
                    }
                }
            }
            System.out.println("Bottom right tile is [" + x + "," + y + "]" );
            if (!level.grid[x - (x > 0 ? 1 : 0)][y - (y > 0 ? 1 : 0)].canWalkThrough()) {  //top-right tile
                blocked = true;
                System.out.println("Up move blocked by [" + (x - 1) +"][" + (y - 1) + "]");
            }
            else if (!level.grid[x][y - (y > 0 ? 1 : 0)].canWalkThrough()) {  //top-left tile
                blocked = true;
                System.out.println("Up move blocked by [" + (x) +"][" + (y - 1) + "]");
            }
            else if (n == 2 && y == 0) {
                blocked = true;
            }
            if (blocked) { //if not blocked or at edge of map
                player.moveDown();
            }
            System.out.println();
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            player.moveDown();  //make move to simulate NEXT position
            boolean blocked = false;
            int x = -1, y = -1;
            int n = 0;  //counts how many tiles player currently occupies (logically can be 1, 2 or 4)
            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    if (level.grid[i][j].getHitbox().entityColliding(player.getHitBox().getRectBox())) { //if player is in tile[i][j]
                        System.out.println("Player in: [" + i + "," + j + "]");
                        x = i;  //store right-most tile player occupies
                        y = j;  //store bottom-most tile player occupies
                        n++;
                    }
                }
            }
            System.out.println("Bottom right tile is [" + x + "," + y + "]" );
            if (!level.grid[x][y].canWalkThrough()) {  //bottom-right tile
                blocked = true;
                System.out.println("Down move blocked by [" + x +"][" + y + "]");
            }
            else if (!level.grid[x - (x > 0 ? 1 : 0)][y].canWalkThrough()) {  //bottom-left tile
                blocked = true;
                System.out.println("Down move blocked by [" + (x - 1) +"][" + y + "]");
            }
            else if (n == 2 && y == 16) {
                blocked = true;
            }
            if (blocked) { //if not blocked or at edge of map
                player.moveUp();
            }
            System.out.println();
        }
    }

    //handle the combat input keys
    public void handleCombatInput() {
        if (Keyboard.isKeyPressed(Keyboard.Key.A)) {
            player.shootBulletLeft();
            player.shootBulletUp();
            player.shootBulletDown();
            player.shootBulletRight();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            player.shootBulletUp();
            player.shootBulletDown();
            player.shootBulletRight();
            player.shootBulletLeft();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            player.shootBulletDown();
            player.shootBulletLeft();
            player.shootBulletRight();
            player.shootBulletUp();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
            player.shootBulletRight();
            player.shootBulletDown();
            player.shootBulletLeft();
            player.shootBulletUp();
        }
    }


    //check to see if any movement keys are pressed
    public boolean movementKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.UP)
                || Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.DOWN));

    }

    public boolean debugKeysPress() {
        return (Keyboard.isKeyPressed(Keyboard.Key.X));
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
