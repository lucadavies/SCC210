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
    float x = 0; //current x-coordinate
    float y = 0; //current y-coordinate

    private Sprite img;
    private float width;
    private float height;
    private float maxx;
    private float max;
    private int current;
    private int lineNumber;
    private int xAcross = 0;
    private CollisionBox colBox;

    protected Map level;

    public Entity(int x, int y, int r, String textureFile, float width, float height, int lineNumber) {
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
        this.maxx = imgTexture.getSize().x / width;
        this.max = maxx * imgTexture.getSize().y / height;
        this.current = -1;
        this.lineNumber = lineNumber - 1;
        colBox = new CollisionBox(x, y, width, height);

        //
        //Store references to object and key methods
        //
        obj = img;

        //performMove();
    }

    //this method loops across the spritesheet row, animating the sprite
    public void next() {
        float x;
        if (++current > max) {
            current = 0;
        }
        if (xAcross == 0) {
            x = current % maxx;
        } else {
            x = current % xAcross;

            float y = lineNumber;

            img.setTextureRect(new IntRect((int) x * (int) width, (int) y * (int) height, (int) width, (int) height));
        }
    }

    public Sprite getSprite() {
        return img;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
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
    public void setSpriteWithinSheet(int y, int x) {
        lineNumber = y;
        xAcross = x;
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
