public class Walker extends Alien {

    private static final int WALKER_SPEED = 2;

    public Walker(int x, int y) {
        super(x, y, WALKER_SPEED, "art/enemy/Walker.png");
        setSpriteWithinSheet(0, 0);
    }
}
