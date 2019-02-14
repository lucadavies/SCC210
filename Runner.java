public class Runner extends Alien {


    public Runner[] runner = new Runner[5];

    public CollisionBox runnerHitBox;

    public Runner(int x, int y) {
        super(x, y, "art/enemy/Runner.png");
        //super.moveLeft();
        //super.moveUp();
        //super.moveRight();
        //super.moveDown();
        super.setSpriteWithinSheet(1, 1);
        runnerHitBox = new CollisionBox(x, y, 60, 60);
        runnerHitBox.setPosition(x, y);
        //super.moveEnemy();

    }


}