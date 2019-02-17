import org.jsfml.graphics.*;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.system.Clock;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Random;

public class Driver {

    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Pickup> pickups = new ArrayList<>();

    static int SCREEN_WIDTH = 1020;
    static int SCREEN_HEIGHT = 1020;
    private static String Title = "Test Arena";
    private static String Message = "testing";
    private Map.mapType[] lvls = {Map.mapType.FARM, Map.mapType.FOREST, Map.mapType.RIVER, Map.mapType.CAVE, Map.mapType.PLANET, Map.mapType.SHIP};
    private Map level;
    private int lvlNum = 0;
    private float walkerSpeed = 0.3f;
    private float runnerSpeed = 0.4f;
    private float gunnerSpeed = 0.3f;
    private float tankSpeed = 0.1f;

    private int walkerN = 10;
    private int runnerN = 5;
    private Player player = Player.getPlayerInstance();
    private Walker[] walker = new Walker[walkerN];
    private Runner[] runner = new Runner[runnerN];

    private RenderWindow window;


    private Clock superLaserGunClock = new Clock();
    private Clock speedClock = new Clock();
    private Clock allDirectionsCapsuleClock = new Clock();
    private Clock frozenStoneClock = new Clock();
    private Clock spawnTimer = new Clock();
    private int dead = 0;
    private boolean spawned = false;
    private int aliensSpawned = 0;

    private Random rnd = new Random();

    public Driver(RenderWindow w) {
        window = w;
    }

    public void run() {

        nextLevel();
        entities.add(player);
        for (Entity ent : entities) {
            ent.setMap(level);
        }
        window.display();
        window.clear();

        spawnTimer.restart();
        while (window.isOpen()) {
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
            for (Bullet b : player.getFiredBullets()) {
                b.move();
                for (Entity ent : entities) {
                    if (ent instanceof Alien) {
                        if (b.isColliding(ent)) {
                            b.setUsed(true);
                            ((Alien) ent).kill();
                            if (rnd.nextInt(4) == 2) {
                                Pickup p = new Pickup(((Alien) ent).x, ((Alien) ent).y, setPickupTypes(rnd.nextInt(7)));
                                pickups.add(p);
                            }
                            dead++;
                            System.out.println("Dead: " + dead);
                        }
                    }
                }
                b.performMove();
                b.draw(window);
            }
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
                player.setChamber(1);
            }
            if (frozenStoneClock.getElapsedTime().asSeconds() > 7) {
                player.setFrozenStoneFalse();
            }

            if (dead >= level.getNumAliens()) {
                entities.clear();
                entities.add(player);
                nextLevel();
                player.setCoordnts(500, 500);
                spawnTimer.restart();
                dead = 0;
                aliensSpawned = 0;
            }

            window.display();
            window.clear();
        }
    }

    private void spawnAliens() {
        if (!spawned && level.getNumAliens() > aliensSpawned && (int) spawnTimer.getElapsedTime().asSeconds() == 0) {
            spawned = true;
            aliensSpawned++;
            System.out.println("Enemy " + aliensSpawned + " spawned");
            int dir = rnd.nextInt(4);
            Walker w = new Walker(0, 0);
            w.setMap(level);
            if (dir == 0) {
                w.setPosition(510, 0);
            } else if (dir == 2) {
                w.setPosition(0, 510);
            } else if (dir == 1) {
                w.setPosition(950, 510);
            } else if (dir == 3) {
                w.setPosition(510, 950);
            }
            entities.add(w);

        } else if ((int) spawnTimer.getElapsedTime().asSeconds() == 1) {
            spawnTimer.restart();
            spawned = false;
        }
    }

    private void handleEvents() {
        if (debugKeysPressed()) {
            if (level.getType() == Map.mapType.TEST) {
                setMap(lvls[0]);
            } else {
                setMap(Map.mapType.TEST);
            }
        }
        if (mapKeyPressed()) {
            if (level.getType() == Map.mapType.FARM) {
                setMap(lvls[1]);
            } else if (level.getType() == Map.mapType.FOREST) {
                setMap(lvls[2]);
            } else if (level.getType() == Map.mapType.RIVER) {
                setMap(lvls[3]);
            } else if (level.getType() == Map.mapType.CAVE) {
                setMap(lvls[4]);
            } else if (level.getType() == Map.mapType.SHIP) {
                setMap(lvls[5]);
            } else if (level.getType() == Map.mapType.PLANET) {
                setMap(lvls[0]);
            }

        }
        for (Event event : window.pollEvents()) {
            if (event.type == Event.Type.CLOSED) {
                window.close();
            }
        }
    }

    private void nextLevel() {
        if (lvlNum < 6) {
            setMap(lvls[lvlNum]);
            lvlNum++;
        }
    }

    private void drawEnts() {
        for (Entity ent : entities) {
            if (ent instanceof Alien) {
                for (int i = 0; i < walkerN; i++) {
                    if (!player.getFrozenStone())
                        ((Alien) ent).move();
                }
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
    private boolean movementKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.RIGHT) || Keyboard.isKeyPressed(Keyboard.Key.UP)
                || Keyboard.isKeyPressed(Keyboard.Key.LEFT) || Keyboard.isKeyPressed(Keyboard.Key.DOWN));

    }

    private boolean debugKeysPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.X));
    }

    private boolean mapKeyPressed() {
        return (Keyboard.isKeyPressed(Keyboard.Key.Z));
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
