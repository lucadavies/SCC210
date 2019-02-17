public class Gunner extends Alien {

    private static final double GUNNER_SPEED = 0.2;

    public Gunner(int x, int y) {
        super(x, y, GUNNER_SPEED, "art/enemy/Gunner.png");
        super.setSpriteWithinSheet(1, 1);
    }
}
