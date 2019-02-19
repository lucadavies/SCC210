import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import java.lang.Object;

import org.jsfml.graphics.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;


public abstract class Entity {

    private Drawable obj;
    int x = 0; //current x-coordinate
    int y = 0; //current y-coordinate
    private int width;
    private int height;
    private Sprite img;
    private CollisionBox colBox;
    private int ssCols;
    private int ssRows;
    private int ssX = 0; //sprite sheet column
    private int ssY = 0; //sprite sheet row

    Map level;

    public Entity(int x, int y, int width, int height, String textureFile) {
        //
        // Load image/texture
        //
        Texture imgTexture = new Texture();
        try {
            imgTexture.loadFromFile(Paths.get(textureFile));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imgTexture.setSmooth(false);
        img = new Sprite(imgTexture);
        img.setOrigin(0, 0);
        img.setPosition(x, y);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        colBox = new CollisionBox(x, y, width, height);
        ssCols = imgTexture.getSize().x / width;
        ssRows = imgTexture.getSize().y / height;
        obj = img;
    }

    public void next() {
        if (ssX >= ssCols - 1) {
            ssX = 0;
        }
        else {
            ssX++;
        }
        img.setTextureRect(new IntRect(ssX * width, (this instanceof Tank ? ssY : 0) * height, width, height));
        if (this instanceof Tank) {
        }
    }

    public Sprite getSprite() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getssX() {
        return ssX;
    }

    public int getssY() {
        return ssY;
    }

    public RectangleShape getRectBox() {
        return colBox.getRectBox();
    }

    public CollisionBox getHitBox() {
        return colBox;
    }

    //
    //This method sets the num of lines down, and how many rows across from the left
    //
    public void setSpriteWithinSheet(int x, int y) {
        ssX = x;
        ssY = y;
        img.setTextureRect(new IntRect(ssX * width, 0, width, height));
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

    boolean within(float x, float y) {
        if (this.x == x && this.y == y) {
            return true;
        }
        return false;
    }

    public void setMap(Map level) {
        this.level = level;
    }

    //
    //Render the object at its new position
    //
    void draw(RenderWindow w) {
        w.draw(obj);
    }


}
