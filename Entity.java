import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import java.lang.Object;

import org.jsfml.graphics.*;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;


public abstract class Entity {

    Drawable obj;
    BiConsumer<Float, Float> setPosition;
    //private ArrayList<Entity> actors = new ArrayList<>();

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
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxx = imgTexture.getSize().x / width;
        this.max = maxx * imgTexture.getSize().y / height;
        this.current = -1;
        this.lineNumber = lineNumber - 1;

        //
        //Store references to object and key methods
        //
        obj = img;
        setPosition = img::setPosition;
        performMove();
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

    //
    //This method sets the num of lines down, and how many rows across from the left
    //
    public void setSpriteWithinSheet(int y, int x) {
        lineNumber = y;
        xAcross = x;
    }

    //
    // work out where object should be for next frame
    //
    void calcMove(int minx, int miny, int maxx, int maxy, float xPos, float yPos) {
        //
        //add deltas to x and y setPosition
        //
        x = xPos;
        y = yPos;

        //
        // check we've not hit screen bounds
        //
        //if(x <= minx || x >= maxx) { dx *= -1; x += dx;}
        //if(x <= miny || x >= maxy) { dy *= -1; y += dy;}

        //
        // check we've not collided with any other actor
        //
        /*for (Entity a : actors) {
            if (a.obj != obj && a.within(x, y)) {
                dx *= -1;
                x += dx;
                dy *= -1;
                y += dy;
            }
        }*/
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

    void performMove() {
        setPosition.accept(x, y);
    }

    //
    //Render the object at its new position
    //
    void draw(RenderWindow w) {
        w.draw(obj);
    }


}
