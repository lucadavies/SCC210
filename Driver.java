import com.sun.prism.PhongMaterial;
import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;
import org.jsfml.system.Clock;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Driver {

    public static ArrayList<Entity> entities = new ArrayList<>();
    public static ArrayList<Alien> enemies = new ArrayList<>();
    public static ArrayList<Pickup> pickups = new ArrayList<>();

    static int SCREEN_WIDTH = 1020;
    static int SCREEN_HEIGHT = 1020;
    private static String Title = "Test Arena";
    private static String Message = "testing";
    private Map.mapType lvl1 = Map.mapType.FARM;
    private Map.mapType lvl2 = Map.mapType.FOREST;
    private Map.mapType lvl3 = Map.mapType.RIVER;
    private Map.mapType lvl4 = Map.mapType.CAVE;
    private Map.mapType lvl5 = Map.mapType.SHIP;
    private Map.mapType lvl6 = Map.mapType.PLANET;
    private Map level = new Map(lvl1);
    private float walkerSpeed = 0.1;
    private float runnerSpeed = 4;

    private Player player = Player.getPlayerInstance();
    private Walker[] walker = new Walker[10];
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

    private Clock clockForEnemies = new Clock();
    private int index = 2;
    private int forEnemies = 0;
    private int random1;
    private int random2;
    private int i;

    public Driver(RenderWindow w) {
        window = w;
    }

    public void run() {

        entities.add(player);
        for(int i =0;i<walker.length;i++){

          random1 =(int)(Math.random() * 4);

          if(random1==0){
            walker[i] = new Walker(510,0);
            entities.add(walker[i]);
          }

          if(random1==2){
            walker[i] = new Walker(0,510);
            entities.add(walker[i]);
          }

          if(random1==1){
            walker[i] = new Walker(950,510);
            entities.add(walker[i]);
          }

          if(random1==3){
            walker[i] = new Walker(510,750);
            entities.add(walker[i]);
          }

        }
        //pickups.add(Bomb);
        pickups.add(superLaserGun);
        pickups.add(alienMess2);
        pickups.add(Boots2);
        pickups.add(allDirectionShooting);
        pickups.add(Boots);
        //pickups.add(alienMess);
        pickups.add(frozenStone);
        pickups.add(frozenStone2);

        //walkers.add(walker);
        //runners.add(runner);

        for (Entity ent : entities) {
            ent.setMap(level);
        }

        window.display();
        window.clear();

        while (window.isOpen()) {

            if(i==1){
              clockForEnemies.restart();
              i++;
            }

            //redraw Map
            level.draw(window);
            //update display with any changes
            handleMovementInput();
            //System.out.println(""+clock.getElapsedTime().asSeconds());
            handleCombatInput();

            if (debugKeysPressed()) {
                if (level.getType() == Map.mapType.TEST) {
                    setMap(lvl1);
                }
                else {
                    setMap(Map.mapType.TEST);
                }
            }

            if (mapKeyPressed()) {
                if (level.getType() == Map.mapType.FARM) {
                    level = new Map(lvl2);
                }
                if (level.getType() == Map.mapType.FOREST) {
                    level = new Map(lvl3);
                }
                if (level.getType() == Map.mapType.RIVER) {
                    level = new Map(lvl4);
                }
                if (level.getType() == Map.mapType.CAVE) {
                    level = new Map(lvl5);
                }
                if (level.getType() == Map.mapType.SHIP) {
                    level = new Map(lvl6);
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
            for (Entity ent : entities) {
                if (ent instanceof Alien) {
                  if((int)clockForEnemies.getElapsedTime().asSeconds()>index && forEnemies <10){
                    walker[forEnemies].isMoving();
                    forEnemies++;
                    index = index + 2;

                  }
                 for(int i=0; i<walker.length; i++){
                   if(((Alien)ent).getIsMoving())
                   ((Alien)ent).moveEnemy(player.getX(), player.getY(), walkerSpeed, walkerSpeed );
                 }
                }
                if (ent instanceof MovingEntity) {
                    ((MovingEntity)ent).performMove();
                }
                ent.draw(window);
            }
            removeChars();

            //get all fired bullet instances, loop through and draw them
            for (Bullet b : player.getFiredBullets()) {
                b.move();
                b.performMove();
                b.draw(window);
            }
            player.removeBullets();


            //loops through every pickup
            //
            //you can check if a pickup has been already been picked up by 'pickup.hasPickedUp()'
            for (Pickup pickup : pickups) {

                //if there is no collision it draws the pickup, if theres collision it doesn't
                if (!pickup.getHitBox().entityColliding(player.getHitBox().getRectBox()) && !pickup.hasPickedUp()) {
                    pickup.draw(window);
                }
                if (pickup.getHitBox().entityColliding(player.getHitBox().getRectBox())) {
                    pickup.setPickedUp();
                    pickup.setPosition(-10, -10);
                    switch (pickup.getType()) {
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

    private void removeChars() {
        entities = (ArrayList<Entity>) entities.stream().filter(b -> b instanceof Character && ((Character)b).isAlive()).collect(Collectors.toList());
    }

    public void handleMovementInput() {

        //Once player-occupied tiles are identified, all operations are performed based upon the bottom right-most tile occupied

        if (Keyboard.isKeyPressed(Keyboard.Key.RIGHT)) {
            player.moveRight();  //make move to simulate NEXT position
            if (!(player.canMoveRight())) {
                player.moveLeft();
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.LEFT)) {
            player.moveLeft();   //make move to simulate NEXT position
            if (!(player.canMoveLeft())) {
                player.moveRight();
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.UP)) {
            player.moveUp();    //make move to simulate NEXT position
            if (!(player.canMoveUp())) { //if not blocked or at edge of map
                player.moveDown();
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.DOWN)) {
            player.moveDown();  //make move to simulate NEXT position
            if (!(player.canMoveDown())) {
                player.moveUp();
            }
        }
    }

    public void setMap(Map.mapType m) {
        level = new Map(m);
        for (Entity ent : entities) {
            ent.setMap(level);
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

    public boolean debugKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.X));
    }

    public boolean mapKeyPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.Z));
    }

    //check if any combat keys are being pressed
    public boolean combatKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.A) || Keyboard.isKeyPressed(Keyboard.Key.W)
                || Keyboard.isKeyPressed(Keyboard.Key.S) || Keyboard.isKeyPressed(Keyboard.Key.D));
    }

    public ArrayList<Entity> getEnts() {
        return entities;
    }

}
