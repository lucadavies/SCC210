public class Walker extends Alien {


    public Walker[] walker = new Walker[10];

    public CollisionBox walkerHitBox;

    public Walker(int WALKER_X, int WALKER_Y) {
        super(WALKER_X, WALKER_Y, "art/enemy/Walker.png");
        super.moveLeft();
        super.moveUp();
        super.moveRight();
        super.moveDown();
        super.setSpriteWithinSheet(1, 1);
         walkerHitBox = new CollisionBox(WALKER_X, WALKER_Y, 60, 60);
        walkerHitBox.setPosition(WALKER_X, WALKER_Y);
        //super.moveEnemy();

    }


}
