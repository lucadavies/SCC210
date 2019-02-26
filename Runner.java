public class Runner extends Alien {

    private static final int RUNNER_SPEED = 5;

    public CollisionBox runnerHitBox;

    public Runner(int x, int y) {
        super(x, y, RUNNER_SPEED, "res/art/enemy/Runner.png", 1);
        super.setSpriteWithinSheet(0, 0);
    }
}