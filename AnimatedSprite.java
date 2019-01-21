import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import java.io.IOException;
import java.nio.file.Paths;

//this will be the class that reads
//the sprite sheets for the animated sprites
//---
//Its loosely based on the example image class

public class AnimatedSprite extends Actor {

  private Sprite img;
  private float width;
  private float height;
  private float maxx;
  private float max;
  private int current;
  private int lineNumber;
  private int xAcross = 0;

  public AnimatedSprite(int x, int y, int r, String textureFile, float width, float height, int lineNumber) {
      //
      // Load image/texture
      //
      Texture imgTexture = new Texture();
      try {
        imgTexture.loadFromFile(Paths.get(textureFile));
      } catch(IOException ex) {
              ex.printStackTrace();
      }
      imgTexture.setSmooth(false);
      img = new Sprite(imgTexture);

      img.setOrigin(0, 0);
      this.x = x;
      this.y = y;
      this.r = r;
      this.width = width;
      this.height = height;
      this.maxx = imgTexture.getSize().x / width;
      this.max = maxx*imgTexture.getSize().y / height;
      this.current = -1;
      this.lineNumber = lineNumber - 1;

      //
      //Store references to object and key methods
      //
      obj = img;
      rotate = img::rotate;
      setPosition = img::setPosition;

  }

  //
  //This method sets the num of lines down, and how many rows across from the left
  //
  public void setSpriteWithinSheet(int y, int x) {
        lineNumber = y;
        xAcross = x;
  }


  //this method loops across the spritesheet row, animating the sprite
  public void next() {
    float x;
    if(++current > max) {
      current = 0;
    }
    if(xAcross == 0) {
      x = current % maxx;
    } else {
      x = current % xAcross;

      float y = lineNumber;

      img.setTextureRect(new IntRect((int)x * (int)width, (int)y * (int)height, (int)width, (int)height));
    }
  }

  public void performMove() {
    super.performMove();
    this.next();
  }

  public void setImgTexture(String texture) {
    Texture imgTexture = new Texture();
    try {
      imgTexture.loadFromFile(Paths.get(texture));
        } catch (IOException ex) {
          System.out.println(ex.getMessage());
        }
        img = new Sprite(imgTexture);
    }










}
