public class Gunner extends Alien {

    private static final int GUNNER_SPEED = 3;

    public Gunner(int x, int y) {
        super(x, y, GUNNER_SPEED, "art/enemy/Gunner.png");
        super.setSpriteWithinSheet(0, 0);
    }
}
