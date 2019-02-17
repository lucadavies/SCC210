public class Runner extends Alien {

    private static final int RUNNER_SPEED = 5;

    public CollisionBox runnerHitBox;

    public Runner(int x, int y) {
        super(x, y, RUNNER_SPEED, "art/enemy/Runner.png");
        super.setSpriteWithinSheet(0, 0);
    }
}