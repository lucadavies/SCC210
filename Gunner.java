public class Gunner extends Alien {

    private static final int GUNNER_SPEED = 3;

    public Gunner(int x, int y) {
        super(x, y, GUNNER_SPEED, "art/enemy/Gunner.png", 2);
        super.setSpriteWithinSheet(0, 0);
    }

    /*public void shootBulletLeft() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "left");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletRight() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "right");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletUp() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "up");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    public void shootBulletDown() {
        if (chamber >= 1) {
            Bullet bullet = new Bullet((int) x, (int) y, 5, 5, "art/bullet.jpg", 10, "down");
            firedBullets.add(bullet);
            //bullet.moveBulletLeft();
            chamber--;
        }
    }

    //loads the chamber
    public void loadChamber() {
        if (chamber < chamberLimit) {
            chamber++;
        }
    }*/

}
