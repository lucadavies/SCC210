public class Runner extends Alien {

    private static final double RUNNER_SPEED = 0.5;

    public CollisionBox runnerHitBox;

    public Runner(int x, int y) {
        super(x, y, RUNNER_SPEED, "art/enemy/Runner.png");
        super.setSpriteWithinSheet(1, 1);
    }
}