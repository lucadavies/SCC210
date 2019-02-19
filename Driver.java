import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.system.Clock;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Random;

public class Driver {

    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Pickup> pickups = new ArrayList<>();

    private Map.mapType[] lvls = {Map.mapType.FARM, Map.mapType.FOREST, Map.mapType.RIVER, Map.mapType.CAVE, Map.mapType.SHIP, Map.mapType.PLANET, Map.mapType.BOSS};
    private Map level;
    private int lvlNum = 0;
    private Player player = Player.getPlayerInstance();
    private HUDObject heart1 = new HUDObject(10, 10, 44, 44, "art/player/heart.png");
    private HUDObject heart2 = new HUDObject(60, 10, 44, 44, "art/player/heart.png");
    private HUDObject heart3 = new HUDObject(110, 10, 44, 44, "art/player/heart.png");
    private RenderWindow window;
    private SplashScreen interLvlLoad;

    private Clock superLaserGunClock = new Clock();
    private Clock speedClock = new Clock();
    private Clock allDirectionsCapsuleClock = new Clock();
    private Clock frozenStoneClock = new Clock();
    private Clock spawnTimer = new Clock();
    private int dead = 0;
    private boolean spawned = false;
    private boolean gameWon = false;

    private Random rnd = new Random();

    public Driver(RenderWindow w) {
        window = w;
        interLvlLoad = new SplashScreen(window, "art/ui/load.png");
    }

    public boolean run() {

        nextLevel();
        entities.add(player);
        for (Entity ent : entities) {
            ent.setMap(level);
        }
        window.display();
        window.clear();

        spawnTimer.restart();
        while (window.isOpen() && !gameWon) {
            handleEvents();
            spawnAliens();
            level.draw(window);
            handleMovementInput();
            handleCombatInput();

            //if no combat keys are pressed, load the chamber (currently allows for semi auto fire only)
            if (!combatKeysPressed()) {
               player.loadChamber();
            }
            if (!movementKeysPressed()) {
                player.standingStill();
            }
            drawEnts();
            filterActiveEnts();

            //get all fired bullet instances, loop through and draw them
            entities.addAll(pickups);
            pickups.clear();
            player.removeUsedBullets();

            //If statements which how long pickups have been activated.
            if (superLaserGunClock.getElapsedTime().asSeconds() > 7) {
                player.setSuperLaserGunFalse();
            }
            if (speedClock.getElapsedTime().asSeconds() > 7) {
                player.setSpeedUpFalse();
            }
            if (allDirectionsCapsuleClock.getElapsedTime().asSeconds() > 7) {
                //player.setChamber(1);
            }
            if (frozenStoneClock.getElapsedTime().asSeconds() > 7) {
                player.setFrozenStoneFalse();
            }

            if (dead >= level.getTotalAliens()) {
                if (lvlNum == lvls.length) {
                    return true;
                } else if (lvlNum < lvls.length - 1) {
                    interLvlLoad.run();
                }
                entities.clear();
                player.reset();
                entities.add(player);
                nextLevel();
                spawnTimer.restart();
                dead = 0;
            }
            if (!player.isAlive()) {
                if (player.getLives() != 0) {
                    entities.clear();
                    lvlNum--; //this line and line below resets current level
                    nextLevel();
                    entities.add(player);
                    player.reset();
                    player.setAlive(true);
                    spawnTimer.restart();
                    dead = 0;
                } else
                    return false;
            }

            window.display();
            window.clear();
        }
        return false;
    }

    private void spawnAliens() {
        if (!spawned && lvlNum == lvls.length && level.needsBoss()) {
            Boss b = new Boss(510, 0);
            level.decrementAlien(Boss.class);
            b.setMap(level);
            entities.add(b);
            spawned = true;
        } else if (!spawned && (int) spawnTimer.getElapsedTime().asSeconds() == 0) {
            Alien a = new Walker(0, 0);
            if (level.getNumRemainingAliens() > 0) {
                do {
                    int type = rnd.nextInt(4);
                    spawned = true;
                    if (type == 0 && level.needsWalker()) {
                        a = new Walker(0, 0);
                        level.decrementAlien(Walker.class);
                    } else if (type == 1 && level.needsRunner()) {
                        a = new Runner(0, 0);
                        level.decrementAlien(Runner.class);
                    } else if (type == 2 && level.needsGunner()) {
                        a = new Gunner(0, 0);
                        level.decrementAlien(Gunner.class);
                    } else if (type == 3 && level.needsTank()) {
                        a = new Tank(0, 0);
                        level.decrementAlien(Tank.class);
                    } else {
                        spawned = false;
                    }
                } while (!spawned);
                int dir = rnd.nextInt(4);
                a.setMap(level);
                if (dir == 0) {
                    a.setPosition(510, 0);
                } else if (dir == 2) {
                    a.setPosition(0, 510);
                } else if (dir == 1) {
                    a.setPosition(950, 510);
                } else if (dir == 3) {
                    a.setPosition(510, 950);
                }
                entities.add(a);
            }
        } else if ((int) spawnTimer.getElapsedTime().asSeconds() == 1) {
            spawnTimer.restart();
            spawned = false;
        }
    }

    private void handleEvents() {
        for (Event event : window.pollEvents()) {
            if (event.type == Event.Type.CLOSED) {
                window.close();
            }
        }
    }

    private void nextLevel() {
        if (lvlNum < lvls.length) {
            setMap(lvls[lvlNum]);
            lvlNum++;
        } else {
            gameWon = true;
        }
    }

    private void drawEnts() {
        for (Entity ent : entities) {
            if (ent instanceof Alien) {
                if (!player.getFrozenStone())
                    ((Alien) ent).move();
            }
            if (ent instanceof MovingEntity) {
                ((MovingEntity) ent).performMove();
            } else if (ent instanceof Pickup) {
                Pickup p = (Pickup) ent;
                //if there is no collision it draws the pickup, if there's collision it doesn't
                if (p.getHitBox().entityColliding(player.getHitBox().getRectBox())) {
                    p.setPickedUp();
                    switch (p.getType()) {
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
            ent.draw(window);
        }
        for (Bullet b : player.getFiredBullets()) {
            b.move();
            for (Entity ent : entities) {
                if (ent instanceof Alien) {
                    if (b.isColliding(ent)) {
                        b.setUsed(true);
                        if (((Alien) ent).hit()) {
                            dead++;
                            if (rnd.nextInt(4) == 2) {
                                Pickup p = new Pickup(((Alien) ent).x, ((Alien) ent).y, setPickupTypes(rnd.nextInt(7)));
                                pickups.add(p);
                            }
                        }
                    }
                }
            }
            b.performMove();
            b.draw(window);
        }
        if (player.getLives() > 0) {
            heart1.draw(window);
        }
        if (player.getLives() > 1) {
            heart2.draw(window);
        }
        if (player.getLives() > 2) {
            heart3.draw(window);
        }
    }

    private void filterActiveEnts() {
        entities = (ArrayList<Entity>) entities.stream().filter(b ->
                (b instanceof Character && ((Character) b).isAlive()) || (b instanceof Pickup && !((Pickup) b).hasPickedUp())
        ).collect(Collectors.toList());
    }

    private void handleMovementInput() {

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

    private void setMap(Map.mapType m) {
        level = new Map(m);
        for (Entity ent : entities) {
            ent.setMap(level);
        }
    }

    //handle the combat input keys
    private void handleCombatInput() {
      if(player.getChamber() >= 1){


        if (Keyboard.isKeyPressed(Keyboard.Key.A)) {

            player.shootBulletLeft();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
            player.shootBulletUp();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.S)) {
            player.shootBulletDown();
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.D)) {
            player.shootBulletRight();
        }

      }
    }

    //check to see if any movement keys are pressed
    private boolean movementKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.UP)
                || Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.DOWN));

    }

    //check if any combat keys are being pressed
    private boolean combatKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.A) || Keyboard.isKeyPressed(Keyboard.Key.W)
                || Keyboard.isKeyPressed(Keyboard.Key.S) || Keyboard.isKeyPressed(Keyboard.Key.D));
    }

    private Pickup.pickUpType setPickupTypes(int random) {
        switch (random) {
            case 0:
                return Pickup.pickUpType.BOOTS;

            case 1:
                return Pickup.pickUpType.SUPER_LASER_GUN;

            case 2:
                return Pickup.pickUpType.BOOTS;

            case 3:
                return Pickup.pickUpType.FREEZE;

            case 4:
                return Pickup.pickUpType.ALIEN_MESS;

            case 5:
                return Pickup.pickUpType.SUPER_LASER_GUN;

            case 6:
                return Pickup.pickUpType.FREEZE;

        }
        return Pickup.pickUpType.BOOTS;
    }

}
