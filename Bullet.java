import java.lang.*;
import org.jsfml.graphics.*;
import org.jsfml.system.*;
/*
*Class for all characters in the game to be based on.
*Both enemies and the player (+ allies)
*/

public class Bullet extends AnimatedSprite {

private float BULLET_WIDTH = 5;
private float BULLET_HEIGHT = 5;
private String BULLET_COLOUR;
private float BULLET_SPEED;
private float xLoc = 0;
private float yLoc = 0;

private int SCREEN_WIDTH = 500;
private int SCREEN_HEIGHT = 500;


private RectangleShape bullet = new RectangleShape(new Vector2f(BULLET_WIDTH,BULLET_HEIGHT));



public Bullet(float x, float y, float w, float h, String c, float s){
  super((int)x,(int)y,0,"art/bullet.jpg",w,w,0);
  xLoc = x;
  yLoc = y;
  BULLET_WIDTH = w;
  BULLET_HEIGHT = h;
  BULLET_COLOUR = c;
  BULLET_SPEED = s;
  bullet.setPosition(x,y);

}


public void moveBulletLeft(){
    //System.out.println("bulletx:"+xLoc);
    xLoc = xLoc - BULLET_SPEED;
    drawBullet();
}

public void moveBulletRight(){

    xLoc = xLoc + BULLET_SPEED;
    drawBullet();
}

public void moveBulletUp(){
    yLoc = yLoc - BULLET_SPEED;
    drawBullet();
}

public void moveBulletDown(){
    yLoc = yLoc + BULLET_SPEED;
    drawBullet();
}

public void drawBullet(){
  bullet.setFillColor(Color.WHITE);
  bullet.setOrigin(xLoc,yLoc);
}

public RectangleShape getBullet(){
  return bullet;
}

public float getBulletx(){
  return xLoc;
}

public float getBullety(){
  return yLoc;
}



}
