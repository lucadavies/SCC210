public class Tank extends Alien {

    private static final double TANK_SPEED = 0.1;

    public Tank(int x, int y) {
        super(x, y, TANK_SPEED, "art/enemy/Tank.png");
        super.setSpriteWithinSheet(1, 1);
    }
}
