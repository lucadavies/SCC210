public class Walker extends Alien {

    private static final double WALKER_SPEED = 0.2;

    public Walker(int x, int y) {
        super(x, y, WALKER_SPEED, "art/enemy/Walker.png");
        setSpriteWithinSheet(1, 1);
    }
}
