public class Walker extends Alien {


    public Walker[] walker = new Walker[10];

    public CollisionBox walkerHitBox;

    public Walker(int x, int y) {
        super(x, y, "art/enemy/Walker.png");
        //super.moveLeft();
        //super.moveUp();
        //super.moveRight();
        //super.moveDown();
        super.setSpriteWithinSheet(1, 1);
        walkerHitBox = new CollisionBox(x, y, 60, 60);
        walkerHitBox.setPosition(x, y);
        //super.moveEnemy();

    }


}
