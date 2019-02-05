public class Walker extends Alien{

    public static final int WALKER_X = 500;
    public static final int WALKER_Y = 500;

    public CollisionBox walkerHitBox = new CollisionBox(WALKER_X, WALKER_Y, 60, 60);

    public Walker(){
      super(WALKER_X,WALKER_Y,"art/enemy/Walker.png");
      super.moveLeft();
      super.moveUp();
      super.moveRight();
      super.moveDown();
      super.setSpriteWithinSheet(1, 1);
      walkerHitBox.setPosition(WALKER_X,WALKER_Y);
      //super.moveEnemy();


    }
  }
