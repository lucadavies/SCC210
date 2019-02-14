public class Gunner extends Alien {


    public Gunner[] gunner = new Gunner[10];

    public CollisionBox gunnerHitBox;

    public Gunner(int x, int y) {
        super(x, y, "art/enemy/Gunner.png");
        //super.moveLeft();
        //super.moveUp();
        //super.moveRight();
        //super.moveDown();
        super.setSpriteWithinSheet(1, 1);
        gunnerHitBox = new CollisionBox(x, y, 60, 60);
        gunnerHitBox.setPosition(x, y);
        //super.moveEnemy();

    }


}
