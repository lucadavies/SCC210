public class Runner extends Alien {

    public static final int RUNNER_X = 700;
    public static final int RUNNER_Y = 700;

    public CollisionBox runnerHitBox = new CollisionBox(RUNNER_X, RUNNER_Y, 60, 60);

    public Runner(){
      super(RUNNER_X,RUNNER_Y,"art/enemy/Runner.png");
      super.moveLeft();
      super.moveUp();
      super.moveRight();
      super.moveDown();
      super.setSpriteWithinSheet(1, 1);
      runnerHitBox.setPosition(RUNNER_X,RUNNER_Y);
      //super.moveEnemy();


    }
  }
