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

    private Sprite img;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;
    private CollisionBox colBox;

    private int ssCols;
    private int ssRows;
    private int ssX = 0; //sprite sheet column
    private int ssY = 0; //sprite sheet row

    Map level;

    public Entity(int x, int y, int r, String textureFile) {
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
        colBox = new CollisionBox(x, y, WIDTH, HEIGHT);
        ssCols = imgTexture.getSize().x / WIDTH;
        ssRows = imgTexture.getSize().y / HEIGHT;
        obj = img;
    }

    public void next() {
        if (ssX >= ssCols - 1) {
            ssX = 0;
        }
        else {
            ssX++;
        }
        img.setTextureRect(new IntRect(ssX * WIDTH, (this instanceof Tank ? ssY : 0) * HEIGHT, WIDTH, HEIGHT));
        if (this instanceof Tank) {
            System.out.println("ssX: " + ssX + ", ssY: " + ssY);
        }
    }

    public Sprite getSprite() {
        return img;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
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
        img.setTextureRect(new IntRect(ssX * WIDTH, 0, WIDTH, HEIGHT));
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
