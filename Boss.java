public class Boss extends Alien {

    private static final int SPEED = 2;
    private static final int WIDTH = 90;
    private static final int HEIGHT = 90;

    public Boss(int x, int y) {
        super(x, y, WIDTH, HEIGHT, SPEED, "art/enemy/boss.png", 20);
        setSpriteWithinSheet(0, 0);
    }
}
