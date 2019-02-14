public class Tank extends Alien {


    public Tank[] tank = new Tank[10];

    public CollisionBox tankHitBox;

    public Tank(int x, int y) {
        super(x, y, "art/enemy/Tank.png");
        //super.moveLeft();
        //super.moveUp();
        //super.moveRight();
        //super.moveDown();
        super.setSpriteWithinSheet(1, 1);
        tankHitBox = new CollisionBox(x, y, 60, 60);
        tankHitBox.setPosition(x, y);
        //super.moveEnemy();

    }


}
