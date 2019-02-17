public class Tank extends Alien {

    private static final int TANK_SPEED = 2;

    public Tank(int x, int y) {
        super(x, y, TANK_SPEED, "art/enemy/Tank.png");
        super.setSpriteWithinSheet(0, 0);
    }

    public void move() {
        int pX = Player.getPlayerInstance().getX();
        int pY = Player.getPlayerInstance().getY();
        if (pX > x && canMoveRight()) { //move right
            x += speed;
        }
        if (pX < x && canMoveLeft()) { //move left
            x -= speed;
        }
        if (pY < y && canMoveUp()) { //move up
            y -= speed;
        }
        if (pY > y && canMoveDown()) { //move down
            y += speed;
        }
        int xDiff = x - Player.getPlayerInstance().getX();
        int yDiff = y - Player.getPlayerInstance().getY();
        if (Math.abs(xDiff) > Math.abs(yDiff)) { //if player further away horiz...
            if (xDiff <= 0) { //player to the right
                setSpriteWithinSheet(getssX(), 0);
            }
            else { //player to the left
                setSpriteWithinSheet(getssX(), 1);
            }
        }
        else { //player further away verti...
            if (yDiff >= 0) { //player above
                setSpriteWithinSheet(getssX(), 2);
            }
            else { //player below
                setSpriteWithinSheet(getssX(), 3);
            }
        }
        getHitBox().setPosition(x, y);
    }

}